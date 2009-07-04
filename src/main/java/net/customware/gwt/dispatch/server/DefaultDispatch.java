package net.customware.gwt.dispatch.server;

import java.util.List;

import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.ActionException;
import net.customware.gwt.dispatch.shared.Result;
import net.customware.gwt.dispatch.shared.UnsupportedActionException;

import com.google.inject.Inject;

public class DefaultDispatch implements Dispatch {

    private static class DefaultExecutionContext implements ExecutionContext {
        private final DefaultDispatch dispatch;

        private final List<ActionResult<?, ?>> actionResults;

        private DefaultExecutionContext( DefaultDispatch dispatch ) {
            this.dispatch = dispatch;
            this.actionResults = new java.util.ArrayList<ActionResult<?, ?>>();
        }

        public <A extends Action<R>, R extends Result> R execute( A action ) throws ActionException {
            return execute( action, true );
        }

        public <A extends Action<R>, R extends Result> R execute( A action, boolean allowRollback )
                throws ActionException {
            R result = dispatch.doExecute( action, this );
            if ( allowRollback )
                actionResults.add( new ActionResult<A, R>( action, result ) );
            return result;
        }

        /**
         * Rolls back all logged action/results.
         * 
         * @throws ActionException
         *             If there is an action exception while rolling back.
         * @throws ServiceException
         *             If there is a low level problem while rolling back.
         */
        private void rollback() throws ActionException {
            for ( int i = actionResults.size() - 1; i >= 0; i-- ) {
                ActionResult<?, ?> actionResult = actionResults.get( i );
                rollback( actionResult );
            }
        }

        private <A extends Action<R>, R extends Result> void rollback( ActionResult<A, R> actionResult )
                throws ActionException {
            dispatch.doRollback( actionResult.getAction(), actionResult.getResult(), this );
        }

    };

    private final ActionHandlerRegistry handlerRegistry;

    @Inject
    public DefaultDispatch( ActionHandlerRegistry handlerRegistry ) {
        this.handlerRegistry = handlerRegistry;
    }

    public <A extends Action<R>, R extends Result> R execute( A action ) throws ActionException {
        DefaultExecutionContext ctx = new DefaultExecutionContext( this );
        try {
            return doExecute( action, ctx );
        } catch ( ActionException e ) {
            ctx.rollback();
            throw e;
        }
    }

    private <A extends Action<R>, R extends Result> R doExecute( A action, ExecutionContext ctx )
            throws ActionException {
        ActionHandler<A, R> handler = findHandler( action );
        return handler.execute( action, ctx );
    }

    private <A extends Action<R>, R extends Result> ActionHandler<A, R> findHandler( A action )
            throws UnsupportedActionException {
        ActionHandler<A, R> handler = handlerRegistry.findHandler( action );
        if ( handler == null )
            throw new UnsupportedActionException( action );

        return handler;
    }

    private <A extends Action<R>, R extends Result> void doRollback( A action, R result, ExecutionContext ctx )
            throws ActionException {
        ActionHandler<A, R> handler = findHandler( action );
        handler.rollback( action, result, ctx );
    }
}

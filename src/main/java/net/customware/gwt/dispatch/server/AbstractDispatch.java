package net.customware.gwt.dispatch.server;

import java.util.List;

import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.ActionException;
import net.customware.gwt.dispatch.shared.DispatchException;
import net.customware.gwt.dispatch.shared.Result;
import net.customware.gwt.dispatch.shared.UnsupportedActionException;

public abstract class AbstractDispatch implements Dispatch {

    private static class DefaultExecutionContext implements ExecutionContext {
        private final AbstractDispatch dispatch;

        private final List<ActionResult<?, ?>> actionResults;

        private DefaultExecutionContext( AbstractDispatch dispatch ) {
            this.dispatch = dispatch;
            this.actionResults = new java.util.ArrayList<ActionResult<?, ?>>();
        }

        public <A extends Action<R>, R extends Result> R execute( A action ) throws DispatchException {
            return execute( action, true );
        }

        public <A extends Action<R>, R extends Result> R execute( A action, boolean allowRollback )
                throws DispatchException {
            R result = dispatch.doExecute( action, this );
            if ( allowRollback )
                actionResults.add( new ActionResult<A, R>( action, result ) );
            return result;
        }

        /**
         * Rolls back all logged action/results.
         * 
         * @throws DispatchException
         */
        private void rollback() throws DispatchException {
            for ( int i = actionResults.size() - 1; i >= 0; i-- ) {
                ActionResult<?, ?> actionResult = actionResults.get( i );
                rollback( actionResult );
            }
        }

        private <A extends Action<R>, R extends Result> void rollback( ActionResult<A, R> actionResult )
                throws DispatchException {
            dispatch.doRollback( actionResult.getAction(), actionResult.getResult(), this );
        }

    };

    public <A extends Action<R>, R extends Result> R execute( A action ) throws DispatchException {
        DefaultExecutionContext ctx = new DefaultExecutionContext( this );
        try {
            return doExecute( action, ctx );
        } catch ( ActionException e ) {
            ctx.rollback();
            throw e;
        }
    }

    private <A extends Action<R>, R extends Result> R doExecute( A action, ExecutionContext ctx )
            throws DispatchException {
        ActionHandler<A, R> handler = findHandler( action );
        
        executing(action, handler, ctx);
        
        try {
            R result  = handler.execute( action, ctx );
            executed(action, result, handler, ctx);
            return result;
        } catch ( DispatchException e) {
            failed(action, e, handler, ctx);
            throw e;
        } catch ( RuntimeException e) {
            failed(action, e, handler, ctx);
            throw e;
        } catch ( Error e) {
            failed(action, e, handler, ctx);
            throw e;
        }
        
    }

    private <A extends Action<R>, R extends Result> ActionHandler<A, R> findHandler( A action )
            throws UnsupportedActionException {
        ActionHandler<A, R> handler = getHandlerRegistry().findHandler( action );
        if ( handler == null )
            throw new UnsupportedActionException( action );

        return handler;
    }

    protected abstract ActionHandlerRegistry getHandlerRegistry();
    
    /**
     * Method invoked before executing the specified action with the specified handler.
     * 
     * <p>This method must not throw any exceptions.</p>
     * 
     * @param <A> the action type
     * @param <R> the result type
     * @param action the action to execute
     * @param handler the handler to execute it with
     * @param ctx the execution context
     */
    protected <A extends Action<R>, R extends Result> void executing(A action, ActionHandler<A,R > handler, ExecutionContext ctx) {
        
    }

    /**
     * Method invoked after the specified action has been succesfully executed with the specified handler.
     * 
     * <p>This method must not throw any exceptions.</p>
     * 
     * @param <A> the action type
     * @param <R> the result type
     * @param action the action to execute
     * @param result the execution result
     * @param handler the handler to execute it with
     * @param ctx the execution context
     */
    protected <A extends Action<R>, R extends Result> void executed(A action, R result, ActionHandler<A,R> handler, ExecutionContext ctx) {
        
    }
    
    /**
     * Method invoked after the specified action has been unsuccesfully executed with the specified handler.
     * 
     * <p>This method must not throw any exceptions.</p>
     * 
     * @param <A> the action type
     * @param <R> the result type
     * @param action the action to execute
     * @param e the exception thrown by the handler
     * @param handler the handler to execute it with
     * @param ctx the execution context
     */    
    protected <A extends Action<R>, R extends Result> void failed(A action, Throwable e, ActionHandler<A,R> handler, ExecutionContext ctx) {
        
    }

    private <A extends Action<R>, R extends Result> void doRollback( A action, R result, ExecutionContext ctx )
            throws DispatchException {
        ActionHandler<A, R> handler = findHandler( action );
        handler.rollback( action, result, ctx );
    }
}

package net.customware.gwt.dispatch.server;

import java.lang.reflect.AnnotatedElement;
import java.util.List;

import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.ActionException;
import net.customware.gwt.dispatch.shared.Result;
import net.customware.my.shared.DispatchText;
import net.customware.my.shared.ServiceException;
import net.customware.my.shared.security.CurrentUser;
import net.customware.my.shared.security.RequireRole;
import net.customware.my.shared.security.UnauthorizedAccessException;
import net.customware.my.shared.security.User;

import org.apache.log4j.Logger;

import com.google.inject.Inject;

public class DefaultDispatch implements Dispatch {

    private static class DefaultExecutionContext implements ExecutionContext {
        private final DefaultDispatch dispatch;

        private final List<ActionResult<?, ?>> actionResults;

        private DefaultExecutionContext( DefaultDispatch dispatch ) {
            this.dispatch = dispatch;
            this.actionResults = new java.util.ArrayList<ActionResult<?, ?>>();
        }

        public <A extends Action<R>, R extends Result> R execute( A action ) throws ActionException,
                ServiceException {
            return execute( action, true );
        }

        public <A extends Action<R>, R extends Result> R execute( A action, boolean allowRollback )
                throws ActionException, ServiceException {
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
        private void rollback() throws ActionException, ServiceException {
            for ( int i = actionResults.size() - 1; i >= 0; i-- ) {
                ActionResult<?, ?> actionResult = actionResults.get( i );
                rollback( actionResult );
            }
        }

        private <A extends Action<R>, R extends Result> void rollback( ActionResult<A, R> actionResult )
                throws ServiceException, ActionException {
            dispatch.doRollback( actionResult.getAction(), actionResult.getResult(), this );
        }

    };

    private final static Logger LOG = Logger.getLogger( DefaultDispatch.class );

    private final DispatchText text;

    private final ActionHandlerRegistry handlerRegistry;

    private final CurrentUser currentUser;

    @Inject
    public DefaultDispatch( ActionHandlerRegistry handlerRegistry, DispatchText text, CurrentUser currentUser ) {
        this.text = text;
        this.handlerRegistry = handlerRegistry;
        this.currentUser = currentUser;
    }

    public <A extends Action<R>, R extends Result> R execute( A action ) throws ActionException, ServiceException {
        DefaultExecutionContext ctx = new DefaultExecutionContext( this );
        try {
            return doExecute( action, ctx );
        } catch ( ActionException e ) {
            ctx.rollback();
            throw e;
        } catch ( ServiceException e ) {
            ctx.rollback();
            throw e;
        }
    }

    private <A extends Action<R>, R extends Result> R doExecute( A action, ExecutionContext ctx )
            throws ActionException, ServiceException {
        checkRole( action );

        ActionHandler<A, R> handler = findHandler( action );

        return handler.execute( action, ctx );
    }

    private <A extends Action<R>, R extends Result> ActionHandler<A, R> findHandler( A action )
            throws ServiceException {
        ActionHandler<A, R> handler = handlerRegistry.findHandler( action );
        if ( handler == null ) {
            LOG.error( text.unsupportedAction() + " (" + action.getClass().getName() + ")" );
            throw new ServiceException( text.unsupportedAction() );
        }
        return handler;
    }

    private void checkRole( Object object ) throws UnauthorizedAccessException {
        checkRole( object.getClass() );
    }

    private void checkRole( AnnotatedElement annotated ) throws UnauthorizedAccessException {
        RequireRole reqRole = annotated.getAnnotation( RequireRole.class );

        // If no annotation is present, no restrictions.
        if ( reqRole == null )
            return;

        User user = currentUser.get();
        if ( user != null ) {
            boolean allowed = false;
            for ( int i = 0; !allowed && i < reqRole.value().length; i++ ) {
                if ( user.hasRole( reqRole.value()[i] ) )
                    allowed = true;
            }

            if ( !allowed )
                throw new UnauthorizedAccessException( "You are not authorized to access this resource." );
        } else {
            throw new UnauthorizedAccessException( "You must be logged in to access this resource." );
        }
    }

    private <A extends Action<R>, R extends Result> void doRollback( A action, R result, ExecutionContext ctx )
            throws ServiceException, ActionException {
        ActionHandler<A, R> handler = findHandler( action );
        handler.rollback( action, result, ctx );
    }
}

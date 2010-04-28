package net.customware.gwt.dispatch.server;

import java.util.List;

import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.ActionException;
import net.customware.gwt.dispatch.shared.BatchAction;
import net.customware.gwt.dispatch.shared.BatchResult;
import net.customware.gwt.dispatch.shared.Result;
import net.customware.gwt.dispatch.shared.BatchAction.OnException;

/**
 * This handles {@link BatchAction} requests, which are a set of multiple
 * actions that need to all be executed successfully in sequence for the whole
 * action to succeed.
 * 
 * @author David Peterson
 */
public class BatchActionHandler extends AbstractActionHandler<BatchAction, BatchResult> {

    public BatchActionHandler() {
        super( BatchAction.class );
    }

    public BatchResult execute( BatchAction action, ExecutionContext context ) throws ActionException {
        OnException onException = action.getOnException();
        List<Result> results = new java.util.ArrayList<Result>();
        List<Throwable> exceptions = new java.util.ArrayList<Throwable>();
        for ( Action<?> a : action.getActions() ) {
            Result result = null;
            try {
                result = context.execute( a );
            } catch ( Exception e ) {
                if ( onException == OnException.ROLLBACK ) {
                    if ( e instanceof ActionException )
                        throw ( ActionException ) e;
                    if ( e instanceof RuntimeException )
                        throw ( RuntimeException ) e;
                    else
                        throw new ActionException( e );
                } else {
                    exceptions.set( results.size(), e );
                }
            }
            results.add( result );
        }

        return new BatchResult( results, exceptions );
    }

    public void rollback( BatchAction action, BatchResult result, ExecutionContext context )
            throws ActionException {
        // No action necessary - the sub actions should automatically rollback
    }

}

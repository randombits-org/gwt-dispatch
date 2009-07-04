package net.customware.gwt.dispatch.server;

import java.util.List;

import org.apache.log4j.Logger;

import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.ActionException;
import net.customware.gwt.dispatch.shared.BatchAction;
import net.customware.gwt.dispatch.shared.BatchResult;
import net.customware.gwt.dispatch.shared.Result;
import net.customware.gwt.dispatch.shared.BatchAction.OnException;
import net.customware.my.shared.ServiceException;

/**
 * This handles {@link BatchAction} requests, which are a set of multiple
 * actions that need to all be executed successfully in sequence for the whole
 * action to succeed.
 * 
 * @author David Peterson
 */
public class BatchActionHandler extends AbstractActionHandler<BatchAction, BatchResult> {

    private static final Logger LOG = Logger.getLogger( BatchActionHandler.class );

    public BatchActionHandler() {
        super( BatchAction.class );
    }

    public BatchResult execute( BatchAction action, ExecutionContext context ) throws ActionException,
            ServiceException {
        OnException onException = action.getOnException();
        List<Result> results = new java.util.ArrayList<Result>();
        for ( Action<?> a : action.getActions() ) {
            Result result = null;
            try {
                result = context.execute( a );
            } catch ( Exception e ) {
                if ( onException == OnException.ROLLBACK ) {
                    if ( e instanceof ActionException )
                        throw ( ActionException ) e;
                    if ( e instanceof ServiceException )
                        throw ( ServiceException ) e;
                    if ( e instanceof RuntimeException )
                        throw ( RuntimeException ) e;
                    else
                        throw new ServiceException( e );
                } else {
                    LOG.warn( "Exception in a BatchAction: " + e.getMessage(), e );
                }
            }
            results.add( result );
        }

        return new BatchResult( results );
    }

    public void rollback( BatchAction action, BatchResult result, ExecutionContext context )
            throws ServiceException, ActionException {
        // No action necessary - the sub actions should automatically rollback
    }

}

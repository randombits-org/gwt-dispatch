package net.customware.gwt.dispatch.server.standard;

import net.customware.gwt.dispatch.client.standard.StandardDispatchService;
import net.customware.gwt.dispatch.server.Dispatch;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.DispatchException;
import net.customware.gwt.dispatch.shared.Result;
import net.customware.gwt.dispatch.shared.ServiceException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public abstract class AbstractStandardDispatchServlet extends RemoteServiceServlet implements
        StandardDispatchService {

    public Result execute( Action<?> action ) throws DispatchException {
        try {
            return getDispatch().execute( action );
        } catch ( RuntimeException e ) {
            log( "Exception while executing " + action.getClass().getName() + ": " + e.getMessage(), e );
            throw new ServiceException( e );
        }
    }

    /**
     * 
     * @return The Dispatch instance.
     */
    protected abstract Dispatch getDispatch();

}

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

    public <R extends Result> R execute( Action<R> action ) throws DispatchException {
        try {
            Dispatch dispatch = getDispatch();
            
            if ( dispatch == null )
                throw new ServiceException("No dispatch found for servlet '" + getServletName() + "' . Please verify your server-side configuration.");
            
            return dispatch.execute( action );
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

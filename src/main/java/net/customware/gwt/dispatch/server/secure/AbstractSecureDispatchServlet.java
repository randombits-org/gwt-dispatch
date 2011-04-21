package net.customware.gwt.dispatch.server.secure;

import net.customware.gwt.dispatch.client.secure.SecureDispatchService;
import net.customware.gwt.dispatch.server.Dispatch;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.DispatchException;
import net.customware.gwt.dispatch.shared.Result;
import net.customware.gwt.dispatch.shared.ServiceException;
import net.customware.gwt.dispatch.shared.secure.InvalidSessionException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public abstract class AbstractSecureDispatchServlet extends RemoteServiceServlet implements SecureDispatchService {

    public Result execute( String sessionId, Action<?> action ) throws DispatchException {
        try {
            
            SecureSessionValidator sessionValidator = getSessionValidator();
            if ( sessionValidator == null )
                throw new ServiceException("No session validator found for servlet '" + getServletName() + "' . Please verify your server-side configuration.");
            
            Dispatch dispatch = getDispatch();
            if ( dispatch == null )
                throw new ServiceException("No dispatch found for servlet '" + getServletName() + "' . Please verify your server-side configuration.");
            
            if ( sessionValidator.isValid( sessionId, getThreadLocalRequest() ) ) {
                return dispatch.execute( action );
            } else {
                throw new InvalidSessionException();
            }
        } catch ( RuntimeException e ) {
            log( "Exception while executing " + action.getClass().getName() + ": " + e.getMessage(), e );
            throw new ServiceException( e.getMessage() );
        }
    }

    protected abstract SecureSessionValidator getSessionValidator();

    protected abstract Dispatch getDispatch();
}
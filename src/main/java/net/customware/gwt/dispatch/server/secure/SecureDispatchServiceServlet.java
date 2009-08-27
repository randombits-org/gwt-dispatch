package net.customware.gwt.dispatch.server.secure;

import net.customware.gwt.dispatch.client.secure.SecureDispatchService;
import net.customware.gwt.dispatch.server.Dispatch;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.Result;
import net.customware.gwt.dispatch.shared.secure.InvalidSessionException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * A servlet implementation of the {@link SecureDispatchService}. This will
 * call the provided {@link SecureSessionValidator} to confirm that the provided
 * session ID is still valid before executing any actions. If not, an
 * {@link InvalidSessionException} is thrown back to the client.
 * 
 * @author David Peterson
 */
@Singleton
public class SecureDispatchServiceServlet extends RemoteServiceServlet implements SecureDispatchService {
    private final Dispatch dispatch;

    private SecureSessionValidator sessionValidator;

    @Inject
    public SecureDispatchServiceServlet( Dispatch dispatch, SecureSessionValidator sessionValidator ) {
        this.dispatch = dispatch;
        this.sessionValidator = sessionValidator;
    }

    public Result execute( String sessionId, Action<?> action ) throws Exception {
        try {
            if ( sessionValidator.getSessionDetails( sessionId ) != null ) {
                return dispatch.execute( action );
            } else {
                throw new InvalidSessionException();
            }
        } catch ( RuntimeException e ) {
            log( "Exception while executing " + action.getClass().getName() + ": " + e.getMessage(), e );
            throw e;
        }
    }
}

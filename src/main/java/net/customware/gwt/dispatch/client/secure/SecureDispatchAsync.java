package net.customware.gwt.dispatch.client.secure;

import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.dispatch.server.Dispatch;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.Result;
import net.customware.gwt.dispatch.shared.secure.InvalidSessionException;
import net.customware.gwt.dispatch.shared.secure.SecureSessionResult;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * This class is the default implementation of {@link DispatchAsync}, which is
 * essentially the client-side access to the {@link Dispatch} class on the
 * server-side.
 * 
 * @author David Peterson
 */
public class SecureDispatchAsync implements DispatchAsync {

    private static final SecureDispatchServiceAsync realService = GWT.create( SecureDispatchService.class );

    public SecureDispatchAsync() {
    }

    public <A extends Action<R>, R extends Result> void execute( final A action, final AsyncCallback<R> callback ) {

        String sessionId = SecureSessionUtil.getSessionId();

        realService.execute( sessionId, action, new AsyncCallback<Result>() {
            public void onFailure( Throwable caught ) {
                if ( caught instanceof InvalidSessionException ) {
                    SecureSessionUtil.clearSessionId();
                }
                callback.onFailure( caught );
            }

            public void onSuccess( Result result ) {
                if ( result instanceof SecureSessionResult ) {
                    SecureSessionUtil.setSessionId( ( ( SecureSessionResult ) result ).getSessionId() );
                }
                callback.onSuccess( ( R ) result );
            }
        } );
    }

}

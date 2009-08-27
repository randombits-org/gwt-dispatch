package net.customware.gwt.dispatch.client.standard;

import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.dispatch.server.Dispatch;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.Result;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * This class is the default implementation of {@link DispatchAsync}, which is
 * essentially the client-side access to the {@link Dispatch} class on the
 * server-side.
 * 
 * @author David Peterson
 */
public class StandardDispatchAsync implements DispatchAsync {

    private static final StandardDispatchServiceAsync realService = GWT.create( StandardDispatchService.class );

    public StandardDispatchAsync() {
    }

    public <A extends Action<R>, R extends Result> void execute( final A action, final AsyncCallback<R> callback ) {
        realService.execute( action, new AsyncCallback<Result>() {
            public void onFailure( Throwable caught ) {
                callback.onFailure( caught );
            }

            public void onSuccess( Result result ) {
                callback.onSuccess( ( R ) result );
            }
        } );
    }

}

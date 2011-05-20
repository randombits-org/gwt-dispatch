package net.customware.gwt.dispatch.client.secure;

import com.google.gwt.user.client.rpc.AsyncCallback;

import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.Result;

public interface SecureDispatchServiceAsync {
    <R extends Result> void execute( String sessionId, Action<R> action, AsyncCallback<R> callback );
}

package net.customware.gwt.dispatch.client.standard;

import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.Result;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface StandardDispatchServiceAsync {

    /**
     * Executes the specified action.
     * 
     * @param action The action to execute.
     * @param callback The callback to execute once the action completes.
     * 
     * @see net.customware.gwt.dispatch.server.Dispatch
     */
    <R extends Result> void execute( Action<R> action, AsyncCallback<R> callback );
}

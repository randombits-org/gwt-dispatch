package net.customware.gwt.dispatch.client.service;

import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.Result;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DispatchServiceAsync {

    /**
     * GWT-RPC service asynchronous (client-side) interface
     * 
     * @see net.customware.gwt.dispatch.server.Dispatch
     */
    void execute( Action<?> action, AsyncCallback<Result> callback );
}

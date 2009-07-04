package net.customware.gwt.dispatch.server.service;

import net.customware.gwt.dispatch.client.service.DispatchService;
import net.customware.gwt.dispatch.server.Dispatch;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.Result;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class DispatchServiceServlet extends RemoteServiceServlet implements DispatchService {
    private final Dispatch dispatch;

    @Inject
    public DispatchServiceServlet( Dispatch dispatch ) {
        this.dispatch = dispatch;
    }

    public Result execute( Action<?> action ) throws Exception {
        return dispatch.execute( action );
    }

}

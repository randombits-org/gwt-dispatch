package net.customware.gwt.dispatch.server.standard;

import net.customware.gwt.dispatch.client.standard.StandardDispatchService;
import net.customware.gwt.dispatch.server.Dispatch;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.ActionException;
import net.customware.gwt.dispatch.shared.Result;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class StandardDispatchServiceServlet extends RemoteServiceServlet implements StandardDispatchService {
    private final Dispatch dispatch;

    @Inject
    public StandardDispatchServiceServlet( Dispatch dispatch ) {
        this.dispatch = dispatch;
    }

    public Result execute( Action<?> action ) throws ActionException {
        try {
            return dispatch.execute( action );
        } catch ( RuntimeException e ) {
            log( "Exception while executing " + action.getClass().getName() + ": " + e.getMessage(), e );
            throw e;
        }
    }
}

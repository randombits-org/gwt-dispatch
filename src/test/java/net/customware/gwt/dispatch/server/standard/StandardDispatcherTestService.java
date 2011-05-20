package net.customware.gwt.dispatch.server.standard;

import net.customware.gwt.dispatch.client.standard.GwtTestStandardDispatcher;
import net.customware.gwt.dispatch.client.standard.StandardDispatchService;
import net.customware.gwt.dispatch.server.BatchActionHandler;
import net.customware.gwt.dispatch.server.DefaultActionHandlerRegistry;
import net.customware.gwt.dispatch.server.Dispatch;
import net.customware.gwt.dispatch.server.InstanceActionHandlerRegistry;
import net.customware.gwt.dispatch.server.SimpleDispatch;
import net.customware.gwt.dispatch.server.counter.CauseErrorHandler;
import net.customware.gwt.dispatch.server.counter.Counter;
import net.customware.gwt.dispatch.server.counter.IncrementCounterHandler;
import net.customware.gwt.dispatch.server.counter.ResetCounterHandler;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.DispatchException;
import net.customware.gwt.dispatch.shared.Result;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * Server side setup for {@link GwtTestStandardDispatcher}.
 */
public class StandardDispatcherTestService extends RemoteServiceServlet implements StandardDispatchService {

    private Dispatch dispatch;

    public StandardDispatcherTestService() {
        // Setup for test case
        Counter counter = new Counter();
        
        InstanceActionHandlerRegistry registry = new DefaultActionHandlerRegistry();
        registry.addHandler( new IncrementCounterHandler( counter ) );
        registry.addHandler( new ResetCounterHandler( counter ) );
        registry.addHandler( new CauseErrorHandler() );
        registry.addHandler( new BatchActionHandler() );
        dispatch = new SimpleDispatch( registry );
    }

    public <R extends Result> R execute( Action<R> action ) throws DispatchException {
        try {
            return dispatch.execute( action );
        } catch ( RuntimeException e ) {
            log( "Exception while executing " + action.getClass().getName() + ": " + e.getMessage(), e );
            throw e;
        }
    }
}

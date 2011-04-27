package net.customware.gwt.dispatch.server;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.stub;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.customware.gwt.dispatch.server.counter.Counter;
import net.customware.gwt.dispatch.server.counter.IncrementCounterHandler;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.ActionException;
import net.customware.gwt.dispatch.shared.DispatchException;
import net.customware.gwt.dispatch.shared.Result;
import net.customware.gwt.dispatch.shared.counter.IncrementCounter;
import net.customware.gwt.dispatch.shared.counter.IncrementCounterResult;

import org.junit.Test;
import org.mockito.Mockito;

/**
 * @author Robert Munteanu
 */
public class SimpleDispatchTest {

    @Test
    public void beforeAndAfterExecuteInvoked() throws DispatchException {

        final IncrementCounterHandler topHandler = new IncrementCounterHandler(new Counter());
        final IncrementCounter topAction = new IncrementCounter(1);

        DefaultActionHandlerRegistry registry = new DefaultActionHandlerRegistry();
        registry.addHandler(topHandler);

        final boolean[] called = new boolean[2];
        final IncrementCounterResult[] foundResult = new IncrementCounterResult[1];

        SimpleDispatch dispatch = new SimpleDispatch(registry) {

            protected <A extends Action<R>, R extends Result> void executing(A action, ActionHandler<A, R> handler,
                    ExecutionContext ctx) {

                called[0] = true;
                
                assertThat(action).isEqualTo(topAction);
                assertThat(handler).isEqualTo(topHandler);
                assertThat(ctx).isNotNull();

            };
            
            protected <A extends Action<R>, R extends Result> void executed(A action, R result, ActionHandler<A,R> handler, ExecutionContext ctx) {
                
                called[1] = true;

                assertThat(action).isEqualTo(topAction);
                assertThat(handler).isEqualTo(topHandler);
                assertThat(ctx).isNotNull();
                
                foundResult[0] = (IncrementCounterResult) result;

            };
        };

        final IncrementCounterResult result = dispatch.execute(topAction);

        assertThat(called).containsOnly(true, true);
        assertThat(foundResult).containsOnly(result);
    }
    
    @Test
    public void failedInvoked() throws DispatchException {

        final IncrementCounterHandler topHandler = Mockito.mock(IncrementCounterHandler.class);
        final IncrementCounter topAction = new IncrementCounter(1);
        
        Throwable firstThrowable = new IllegalArgumentException();
        Throwable secondThrowable = new ActionException("Test");
        Throwable thirdThrowable = new LinkageError();
        final List<Throwable> throwables = Arrays.asList(firstThrowable, secondThrowable, thirdThrowable);
        
        stub(topHandler.execute(Mockito.eq(topAction), (ExecutionContext) Mockito.anyObject()))
            .toThrow(firstThrowable).toThrow(secondThrowable).toThrow(thirdThrowable);
        stub(topHandler.getActionType()).toReturn(IncrementCounter.class);

        DefaultActionHandlerRegistry registry = new DefaultActionHandlerRegistry();
        registry.addHandler(topHandler);

        final List<Throwable> reportedThrowables = new ArrayList<Throwable>();
        SimpleDispatch dispatch = new SimpleDispatch(registry) {

            protected <A extends Action<R>, R extends Result> void failed(A action, Throwable e, ActionHandler<A,R> handler, ExecutionContext ctx) {
                
                assertThat(action).isEqualTo(topAction);
                assertThat(handler).isEqualTo(topHandler);
                assertThat(ctx).isNotNull();
                
                reportedThrowables.add(e);
            };
        };

        final List<Throwable> caughtThrowables = new ArrayList<Throwable>();
        
        for ( int i = 0 ; i < throwables.size(); i++ )
            try {
                dispatch.execute(topAction);
            } catch (Throwable e) {
                caughtThrowables.add(e);
            }

        assertThat(reportedThrowables).isEqualTo(throwables)
            .isEqualTo(caughtThrowables);
    }
}

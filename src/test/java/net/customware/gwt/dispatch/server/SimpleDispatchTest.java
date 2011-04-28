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
import net.customware.gwt.dispatch.shared.ServiceException;
import net.customware.gwt.dispatch.shared.counter.IncrementCounter;
import net.customware.gwt.dispatch.shared.counter.IncrementCounterResult;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * @author Robert Munteanu
 */
public class SimpleDispatchTest {

    private CallCounter counter;
    private final IncrementCounter topAction = new IncrementCounter(1);
    
    @Before
    public void init() {
        
        counter = new CallCounter();
    }

    @Test
    public void executingAndExecuted() throws DispatchException {

        final IncrementCounterHandler topHandler = new IncrementCounterHandler(new Counter());
        final IncrementCounterResult[] foundResult = new IncrementCounterResult[1];

        SimpleDispatch dispatch = new SimpleDispatch(registryWithSingleHandler(topHandler)) {

            protected <A extends Action<R>, R extends Result> void executing(A action, ActionHandler<A, R> handler,
                    ExecutionContext ctx) {

                counter.executingCount++;

                assertThat(action).isEqualTo(topAction);
                assertThat(handler).isEqualTo(topHandler);
                assertThat(ctx).isNotNull();

            }

            protected <A extends Action<R>, R extends Result> void executed(A action, R result, ActionHandler<A, R> handler, ExecutionContext ctx) {

                counter.executedCount++;

                assertThat(action).isEqualTo(topAction);
                assertThat(handler).isEqualTo(topHandler);
                assertThat(ctx).isNotNull();

                foundResult[0] = (IncrementCounterResult) result;
            }

            protected <A extends Action<R>, R extends Result> void failed(A action, Throwable e, ActionHandler<A, R> handler, ExecutionContext ctx) {

                counter.failedCount++;
            }
        };

        final IncrementCounterResult result = dispatch.execute(topAction);

        counter.assertCallCounts(1, 1, 0);

        assertThat(foundResult).containsOnly(result);
    }

    @Test
    public void executingAndFailed() throws DispatchException {

        final IncrementCounterHandler topHandler = Mockito.mock(IncrementCounterHandler.class);

        Throwable firstThrowable = new IllegalArgumentException();
        Throwable secondThrowable = new ActionException("Test");
        Throwable thirdThrowable = new LinkageError();
        final List<Throwable> throwables = Arrays.asList(firstThrowable, secondThrowable, thirdThrowable);

        stub(topHandler.execute(Mockito.eq(topAction), (ExecutionContext) Mockito.anyObject()))
                .toThrow(firstThrowable).toThrow(secondThrowable).toThrow(thirdThrowable);

        final List<Throwable> reportedThrowables = new ArrayList<Throwable>();
        SimpleDispatch dispatch = new SimpleDispatch(registryWithSingleHandler(topHandler)) {

            protected <A extends Action<R>, R extends Result> void executing(A action, ActionHandler<A, R> handler, ExecutionContext ctx) throws DispatchException {

                counter.executingCount++;
            }

            protected <A extends Action<R>, R extends Result> void executed(A action, R result, ActionHandler<A, R> handler, ExecutionContext ctx) {

                counter.executedCount++;
            }

            protected <A extends Action<R>, R extends Result> void failed(A action, Throwable e, ActionHandler<A, R> handler, ExecutionContext ctx) {

                counter.failedCount++;

                assertThat(action).isEqualTo(topAction);
                assertThat(handler).isEqualTo(topHandler);
                assertThat(ctx).isNotNull();

                reportedThrowables.add(e);
            };
        };

        final List<Throwable> caughtThrowables = new ArrayList<Throwable>();

        for (int i = 0; i < throwables.size(); i++)
            try {
                dispatch.execute(topAction);
            } catch (Throwable e) {
                caughtThrowables.add(e);
            }

        assertThat(reportedThrowables).isEqualTo(throwables)
                .isEqualTo(caughtThrowables);
        counter.assertCallCounts(3, 0, 3);
    }

    @Test
    public void exceptionInExecuted() throws DispatchException {

        final IncrementCounterHandler topHandler = new IncrementCounterHandler(new Counter());
        final ServiceException thrown = new ServiceException("Disallowed");

        SimpleDispatch dispatch = new SimpleDispatch(registryWithSingleHandler(topHandler)) {

            protected <A extends Action<R>, R extends Result> void executing(A action, ActionHandler<A, R> handler,
                    ExecutionContext ctx) throws ServiceException {

                counter.executingCount++;

                throw thrown;

            };

            protected <A extends Action<R>, R extends Result> void executed(A action, R result, ActionHandler<A, R> handler, ExecutionContext ctx) {

                counter.executedCount++;
            };

            protected <A extends Action<R>, R extends Result> void failed(A action, Throwable e, ActionHandler<A, R> handler, ExecutionContext ctx) {

                counter.failedCount++;
            };

        };

        try {
            dispatch.execute(topAction);
        } catch (ServiceException e) {
            assertThat(e).isEqualTo(thrown);
        }

        counter.assertCallCounts(1, 0, 1);

    }
    
    private ActionHandlerRegistry registryWithSingleHandler(ActionHandler<IncrementCounter, IncrementCounterResult> handler ) {
        
        ActionHandlerRegistry registry = Mockito.mock(ActionHandlerRegistry.class);
        stub(registry.findHandler(topAction)).toReturn(handler);
        
        return registry;
    }

    private static class CallCounter {

        private int executingCount;
        private int executedCount;
        private int failedCount;

        public void assertCallCounts(int executing, int executed, int failed) {

            assertThat(executingCount).as("executing").isEqualTo(executing);
            assertThat(executedCount).as("executed").isEqualTo(executed);
            assertThat(failedCount).as("failed").isEqualTo(failed);
        }
    }
}

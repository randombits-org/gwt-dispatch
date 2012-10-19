package net.customware.gwt.dispatch.server.guice;

import com.google.inject.AbstractModule;
import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ActionHandlerRegistry;
import net.customware.gwt.dispatch.server.Dispatch;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.Result;

/**
 * This is an abstract base class that configures Guice to inject
 * {@link Dispatch} and {@link ActionHandler} instances. If no other prior
 * instance of {@link ServerDispatchModule} has been installed, the standard
 * {@link Dispatch} and {@link ActionHandlerRegistry} classes will be
 * configured.
 * <p/>
 * <p/>
 * Implement the {@link #configureHandlers()} method and call
 * {@link #bindHandler(Class, Class)} to register handler implementations. For example:
 * <p/>
 * <pre>
 * public class MyDispatchModule extends ClientDispatchModule {
 *      \@Override
 *      protected void configureHandlers() {
 *          bindHandler( MyHandler.class );
 *      }
 * }
 * </pre>
 */
public abstract class ActionHandlerModule extends AbstractModule {

    private static class ActionHandlerMapImpl<A extends Action<R>, R extends Result> implements
        ActionHandlerMap<A, R> {

        private final Class<A> actionClass;

        private final Class<? extends ActionHandler<A, R>> handlerClass;

        public ActionHandlerMapImpl( Class<A> actionClass, Class<? extends ActionHandler<A, R>> handlerClass ) {
            this.actionClass = actionClass;
            this.handlerClass = handlerClass;
        }

        public Class<A> getActionClass() {
            return actionClass;
        }

        public Class<? extends ActionHandler<A, R>> getActionHandlerClass() {
            return handlerClass;
        }

    }

    @Override
    protected final void configure() {
        // This will only get installed once due to equals/hashCode override.
        install( new ServerDispatchModule() );

        configureHandlers();
    }

    /**
     * Override this method to configure handlers.
     */
    protected abstract void configureHandlers();

    /**
     * Call this method to binds the specified {@link ActionHandler} instance
     * class.
     *
     * @param actionClass  The action class.
     * @param handlerClass The handler class.
     */
    protected <A extends Action<R>, R extends Result> void bindHandler( Class<A> actionClass,
                                                                        Class<? extends ActionHandler<A, R>> handlerClass ) {
        bind( ActionHandlerMap.class ).annotatedWith( UniqueAnnotations.create() ).toInstance(
            new ActionHandlerMapImpl<A, R>( actionClass, handlerClass ) );
    }

}

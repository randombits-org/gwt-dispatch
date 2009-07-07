package net.customware.gwt.dispatch.server.guice;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ActionHandlerRegistry;
import net.customware.gwt.dispatch.server.Dispatch;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.internal.UniqueAnnotations;

/**
 * This is an abstract base class that configures Guice to inject
 * {@link Dispatch} and {@link ActionHandler} instances. If no other prior
 * instance of {@link ServerDispatchModule} has been installed, the standard
 * {@link Dispatch} and {@link ActionHandlerRegistry} classes will be
 * configured.
 * 
 * <p>
 * Implement the {@link #configureHandlers()} method and call
 * {@link #bindHandler(Class)} to register handler implementations. For example:
 * 
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
     * Binds the specified {@link ActionHandler} instance class.
     * 
     * @param handlerClass
     */
    protected void bindHandler( Class<? extends ActionHandler<?, ?>> handlerClass ) {
        bind( ActionHandler.class ).annotatedWith( UniqueAnnotations.create() ).to( handlerClass ).in(
                Singleton.class );

    }

}

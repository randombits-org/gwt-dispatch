package net.customware.gwt.dispatch.server.guice;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ActionHandlerRegistry;
import net.customware.gwt.dispatch.server.DefaultActionHandlerRegistry;
import net.customware.gwt.dispatch.server.DefaultDispatch;
import net.customware.gwt.dispatch.server.Dispatch;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.name.Names;

/**
 * This is an abstract base class that configures Guice to inject
 * {@link Dispatch} and {@link ActionHandler} instances.
 * 
 * <p>
 * Subclass this module to initialise the {@link Dispatch}, implement the
 * {@link #configureHandlers()} method and call {@link #bindHandler(Class)} to
 * register handler implementations. For example:
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
public abstract class ServerDispatchModule extends AbstractModule {

    private static boolean configured = false;

    @Override
    protected void configure() {
        // Note: This assumes that all modules will only be configured in one
        // injector tree, and that it all happens in a single thread.
        if ( !configured ) {
            bind( ActionHandlerRegistry.class ).to( getActionHandlerRegistryClass() ).in( Singleton.class );
            bind( Dispatch.class ).to( getDispatchClass() );
            // This will bind registered handlers to the registry.
            requestStaticInjection( ActionHandlerLinker.class );
            configured = true;
        }

        configureHandlers();
    }

    /**
     * The class returned by this method is bound to the {@link Dispatch}
     * service. Subclasses may override this method to provide custom
     * implementations. Defaults to {@link DefaultDispatch}.
     * 
     * @return The {@link Dispatch} implementation class.
     */
    protected Class<? extends Dispatch> getDispatchClass() {
        return DefaultDispatch.class;
    }

    /**
     * The class returned by this method is bound to the
     * {@link ActionHandlerRegistry}. Subclasses may override this method to
     * provide custom implementations. Defaults to
     * {@link DefaultActionHandlerRegistry}.
     * 
     * @return The {@link ActionHandlerRegistry} implementation class.
     */
    protected Class<? extends ActionHandlerRegistry> getActionHandlerRegistryClass() {
        return DefaultActionHandlerRegistry.class;
    }

    protected abstract void configureHandlers();

    protected void bindHandler( Class<? extends ActionHandler<?, ?>> handlerClass ) {
        bind( ActionHandler.class ).annotatedWith( Names.named( handlerClass.getName() ) ).to( handlerClass ).in(
                Singleton.class );

    }

}

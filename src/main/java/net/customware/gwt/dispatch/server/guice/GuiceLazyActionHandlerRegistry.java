package net.customware.gwt.dispatch.server.guice;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.LazyActionHandlerRegistry;

/**
 * This will use Guice to create instances of registered {@link ActionHandler}s
 * on in a lazy manner. That is, they are only created upon the first request of
 * a handler for the {@link Action} it is registered with, rather than requiring
 * the class to be constructed when the registry is initialised.
 * 
 * @author David Peterson
 */
@Singleton
public class GuiceLazyActionHandlerRegistry extends LazyActionHandlerRegistry {
    private final Injector injector;

    @Inject
    public GuiceLazyActionHandlerRegistry( Injector injector ) {
        this.injector = injector;
    }

    @Override
    protected ActionHandler<?, ?> createInstance( Class<? extends ActionHandler<?, ?>> handlerClass ) {
        return injector.getInstance( handlerClass );
    }

}

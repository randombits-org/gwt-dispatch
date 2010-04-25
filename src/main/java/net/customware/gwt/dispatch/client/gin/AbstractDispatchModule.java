package net.customware.gwt.dispatch.client.gin;

import com.google.gwt.inject.client.AbstractGinModule;
import net.customware.gwt.dispatch.client.ExceptionHandler;

/**
 * Abstract base class that binds an instance of {@link ExceptionHandler} for use
 * by {@link net.customware.gwt.dispatch.client.AbstractDispatchAsync} implementations.
 *
 * @author David Peterson
 */
public class AbstractDispatchModule extends AbstractGinModule {
    protected final Class<? extends ExceptionHandler> exceptionHandlerType;

    public AbstractDispatchModule( Class<? extends ExceptionHandler> exceptionHandlerType ) {
        this.exceptionHandlerType = exceptionHandlerType;
    }

    @Override
    protected void configure() {
        bind( ExceptionHandler.class ).to( exceptionHandlerType );
    }
}

package net.customware.gwt.dispatch.server.guice;

import net.customware.gwt.dispatch.server.ActionHandlerRegistry;
import net.customware.gwt.dispatch.server.DefaultActionHandlerRegistry;
import net.customware.gwt.dispatch.server.DefaultDispatch;
import net.customware.gwt.dispatch.server.Dispatch;
import net.customware.gwt.dispatch.server.InstanceActionHandlerRegistry;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Singleton;

/**
 * This module will configure the implementation for the {@link Dispatch} and
 * {@link ActionHandlerRegistry} interfaces. If you want to override the
 * defaults ({@link DefaultDispatch} and {@link DefaultActionHandlerRegistry},
 * respectively), pass the override values into the constructor for this module
 * and ensure it is installed <b>before</b> any {@link ActionHandlerModule}
 * instances.
 * 
 * @author David Peterson
 * 
 */
public class ServerDispatchModule extends AbstractModule {

    private Class<? extends Dispatch> dispatchClass;

    private Class<? extends ActionHandlerRegistry> actionHandlerRegistryClass;

    public ServerDispatchModule() {
        this( DefaultDispatch.class, DefaultActionHandlerRegistry.class );
    }

    public ServerDispatchModule( Class<? extends Dispatch> dispatchClass ) {
        this( dispatchClass, DefaultActionHandlerRegistry.class );
    }

    public ServerDispatchModule( Class<? extends Dispatch> dispatchClass,
            Class<? extends ActionHandlerRegistry> actionHandlerRegistryClass ) {
        this.dispatchClass = dispatchClass;
        this.actionHandlerRegistryClass = actionHandlerRegistryClass;
    }

    @Override
    protected final void configure() {
        bind( ActionHandlerRegistry.class ).to( getActionHandlerRegistryClass() ).in( Singleton.class );
        bind( Dispatch.class ).to( getDispatchClass() );
        // This will bind registered handlers to the registry.
        if ( InstanceActionHandlerRegistry.class.isAssignableFrom( getActionHandlerRegistryClass() ) )
            requestStaticInjection( ActionHandlerLinker.class );
    }

    /**
     * The class returned by this method is bound to the {@link Dispatch}
     * service. Subclasses may override this method to provide custom
     * implementations. Defaults to {@link DefaultDispatch}.
     * 
     * @return The {@link Dispatch} implementation class.
     */
    protected Class<? extends Dispatch> getDispatchClass() {
        return dispatchClass;
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
        return actionHandlerRegistryClass;
    }

    /**
     * Override so that only one instance of this class will ever be installed
     * in an {@link Injector}.
     */
    @Override
    public boolean equals( Object obj ) {
        return obj instanceof ServerDispatchModule;
    }

    /**
     * Override so that only one instance of this class will ever be installed
     * in an {@link Injector}.
     */
    @Override
    public int hashCode() {
        return ServerDispatchModule.class.hashCode();
    }

}

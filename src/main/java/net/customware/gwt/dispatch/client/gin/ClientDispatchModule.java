package net.customware.gwt.dispatch.client.gin;

import net.customware.gwt.dispatch.client.DefaultDispatchAsync;
import net.customware.gwt.dispatch.client.DispatchAsync;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.Ginjector;
import com.google.inject.Singleton;

/**
 * This module binds the {@link DispatchAsync} to {@link DefaultDispatchAsync}.
 * Add this as a \@GinModule in your {@link Ginjector} instance.
 * 
 * @author David Peterson
 */
public class ClientDispatchModule extends AbstractGinModule {

    @Override
    protected void configure() {
        bind( DispatchAsync.class ).to( DefaultDispatchAsync.class ).in( Singleton.class );
    }

}

package net.customware.gwt.dispatch.client.gin;

import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.dispatch.client.appengine.AppEngineSecureSessionAccessor;
import net.customware.gwt.dispatch.client.secure.CookieSecureSessionAccessor;
import net.customware.gwt.dispatch.client.secure.SecureDispatchAsync;
import net.customware.gwt.dispatch.client.secure.SecureSessionAccessor;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.Ginjector;
import com.google.inject.Provides;
import com.google.inject.Singleton;

/**
 * This module binds the {@link DispatchAsync} to {@link SecureDispatchAsync}.
 * Add this as a \@GinModule in your {@link Ginjector} instance.
 * 
 * <p>
 * You must also provide another module which binds an implementation of
 * {@link SecureSessionAccessor}, such as {@link CookieSecureSessionAccessor}
 * or {@link AppEngineSecureSessionAccessor}.
 * 
 * @author David Peterson
 */
public class SecureDispatchModule extends AbstractGinModule {

    @Override
    protected void configure() {
    }

    @Provides
    @Singleton
    protected DispatchAsync provideDispatchAsync( SecureSessionAccessor secureSessionAccessor ) {
        return new SecureDispatchAsync( secureSessionAccessor );
    }

}

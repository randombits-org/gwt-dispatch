package net.customware.gwt.dispatch.client.appengine;

import net.customware.gwt.dispatch.client.secure.CookieSecureSessionAccessor;
import net.customware.gwt.dispatch.client.secure.SecureSessionAccessor;

/**
 * An implementation of {@link SecureSessionAccessor} for Google App Engine
 * authentication.
 * 
 * @author David Peterson
 * @author David Chandler
 */
public class AppEngineSecureSessionAccessor extends CookieSecureSessionAccessor {

    private final static String COOKIE_NAME = "ACSID";

    public AppEngineSecureSessionAccessor() {
        super( COOKIE_NAME );
    }
}

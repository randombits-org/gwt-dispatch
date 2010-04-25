package net.customware.gwt.dispatch.shared.secure;

import net.customware.gwt.dispatch.shared.Action;

/**
 * A simple username/password authentication request. If successful, a session
 * has been created and a {@link SecureSessionResult} is returned.
 * 
 * @author David Peterson
 */
public class SimpleAuthenticationAction implements Action<SecureSessionResult> {
    private String username;

    private String password;

    SimpleAuthenticationAction() {
    }

    public SimpleAuthenticationAction( String username, String password ) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

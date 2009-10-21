package net.customware.gwt.dispatch.server.secure;

import net.customware.gwt.dispatch.server.guice.GuiceSecureDispatchServlet;

/**
 * Implementors must provide an implementation of this interface and provide it
 * to {@link GuiceSecureDispatchServlet} so that it can check for valid
 * session ids.
 * 
 * @author David Peterson
 */
public interface SecureSessionValidator {
    /**
     * Returns the session details if the session is still valid. If not,
     * <code>null</code> is returned.
     * 
     * @param sessionId
     *            The session id.
     * @return <code>true</code> if valid.
     */
    Object getSessionDetails( String sessionId );

    /**
     * Creates a new session with the specified details and returns the session
     * id.
     * 
     * @param details
     *            The details object.
     * @return The session id.
     */
    String createSession( Object details );

    /**
     * Clears the specified session, if it is still relevant.
     * 
     * @param sessionId
     *            The session ID.
     * @return <code>true</code> if the session existed and has now been
     *         cleared, or false if it could not be found.
     */
    boolean clearSession( String sessionId );
}

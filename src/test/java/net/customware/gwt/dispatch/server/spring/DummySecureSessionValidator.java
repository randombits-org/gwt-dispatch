package net.customware.gwt.dispatch.server.spring;

import javax.servlet.http.HttpServletRequest;

import net.customware.gwt.dispatch.server.secure.SecureSessionValidator;

/**
 * @author Robert Munteanu
 */
public class DummySecureSessionValidator implements SecureSessionValidator {

    public boolean isValid(String sessionId, HttpServletRequest req) {

        return true;
    }

}

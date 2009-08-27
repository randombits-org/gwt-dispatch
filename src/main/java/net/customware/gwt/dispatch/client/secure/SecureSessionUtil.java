package net.customware.gwt.dispatch.client.secure;

import java.util.Date;

import com.google.gwt.user.client.Cookies;

public class SecureSessionUtil {

    private static final String SESSION_ID = "sid";

    // duration remembering login. 2 weeks currently.
    private static final long DURATION = 1000 * 60 * 60 * 24 * 14;

    public static String getSessionId() {
        return Cookies.getCookie( SESSION_ID );
    }

    public static void setSessionId( String sessionId ) {
        Date expires = new Date( System.currentTimeMillis() + DURATION );
        Cookies.setCookie( SESSION_ID, sessionId, expires, null, "/", false );
    }

    public static void clearSessionId() {
        Cookies.removeCookie( SESSION_ID );
    }

}

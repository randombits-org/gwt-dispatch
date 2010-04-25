package net.customware.gwt.dispatch.shared.secure;

import net.customware.gwt.dispatch.shared.ServiceException;

public class InvalidSessionException extends ServiceException {

    public InvalidSessionException() {
        super();
    }

    public InvalidSessionException( String message ) {
        super( message );
    }
}

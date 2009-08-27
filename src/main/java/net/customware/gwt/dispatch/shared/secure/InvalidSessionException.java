package net.customware.gwt.dispatch.shared.secure;

public class InvalidSessionException extends Exception {

    public InvalidSessionException() {
        super();
    }

    public InvalidSessionException( String message, Throwable cause ) {
        super( message, cause );
    }

    public InvalidSessionException( String message ) {
        super( message );
    }

    public InvalidSessionException( Throwable cause ) {
        super( cause );
    }

}

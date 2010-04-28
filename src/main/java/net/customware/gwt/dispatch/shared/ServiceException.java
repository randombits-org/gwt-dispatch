package net.customware.gwt.dispatch.shared;

import java.io.Serializable;

/**
 * This is thrown by services when there is a low-level problem while processing an action execution.
 *
 * @author David Peterson
 */
public class ServiceException extends Exception implements Serializable {
    public ServiceException() {
    }

    public ServiceException( String message ) {
        super( message );
    }

    public ServiceException( String message, Throwable cause ) {
        super( message + " (" + cause + ")" );
    }

    public ServiceException( Throwable cause ) {
        super( cause.getMessage() );
    }
}

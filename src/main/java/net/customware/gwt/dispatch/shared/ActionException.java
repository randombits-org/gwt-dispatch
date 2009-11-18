package net.customware.gwt.dispatch.shared;

import net.customware.gwt.dispatch.server.Dispatch;

import java.io.Serializable;

/**
 * These are thrown by {@link Dispatch#execute(Action)} if there is a
 * problem executing a particular {@link Action}.
 * 
 * @author David Peterson
 */
public class ActionException extends Exception implements Serializable {

    public ActionException() {
    }

    public ActionException( String message ) {
        super( message );
    }

    public ActionException( Throwable cause ) {
        super( cause.getMessage() );
    }

    public ActionException( String message, Throwable cause ) {
        super( message + " (" + cause.getMessage() + ")" );
    }

}

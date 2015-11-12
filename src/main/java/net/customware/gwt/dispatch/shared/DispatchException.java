package net.customware.gwt.dispatch.shared;

import com.google.gwt.core.shared.SerializableThrowable;
import java.io.Serializable;

/**
 * An abstract superclass for exceptions that can be thrown by the Dispatch
 * system.
 * 
 * @author David Peterson
 */
public abstract class DispatchException extends Exception implements Serializable {

    private String causeClassname;

    protected DispatchException() {
    }

    public DispatchException( String message ) {
        super( message );
    }

    public DispatchException( Throwable cause ) {
        super( cause.getMessage() );
        this.causeClassname = cause.getClass().getName();
    }

    public DispatchException( String message, Throwable cause ) {
        super( message + " (" + cause.getMessage() + ")" );
        this.causeClassname = cause.getClass().getName();
    }

    public DispatchException( SerializableThrowable serializableThrowable ) {
        super(serializableThrowable);
        causeClassname = serializableThrowable.getClass().getName();
    }

    public String getCauseClassname() {
        return causeClassname;
    }

    @Override
    public String toString() {
        return super.toString() + ( causeClassname != null ? " [cause: " + causeClassname + "]" : "" );
    }
}

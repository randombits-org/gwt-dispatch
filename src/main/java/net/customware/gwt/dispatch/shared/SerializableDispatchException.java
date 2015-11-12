package net.customware.gwt.dispatch.shared;

import com.google.gwt.core.shared.SerializableThrowable;
import java.io.PrintStream;

/**
 * Thin Wrapper Around {@link SerializableThrowable}, used to allow full stacktraces to be displayed client side
 *
 * @author Ciaran Liedeman
 */
public class SerializableDispatchException extends DispatchException {

    private SerializableThrowable serializableThrowable;

    public SerializableDispatchException() {
    }

    public SerializableDispatchException( SerializableThrowable serializableThrowable ) {
        this.serializableThrowable = serializableThrowable;
    }

    @Override
    public void printStackTrace() {
        serializableThrowable.printStackTrace();
    }

    @Override
    public void printStackTrace(PrintStream s) {
        serializableThrowable.printStackTrace(s);
    }

    public SerializableThrowable getSerializableThrowable() {
        return serializableThrowable;
    }

    public void setSerializableThrowable( SerializableThrowable serializableThrowable ) {
        this.serializableThrowable = serializableThrowable;
    }

    @Override
    public StackTraceElement[] getStackTrace() {
        return serializableThrowable.getStackTrace();
    }
}
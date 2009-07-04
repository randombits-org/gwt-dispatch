package net.customware.gwt.dispatch.shared;

/**
 * A simple extension of {@link Result} which returns a single value. You can
 * use the {@link SimpleResultCallback} to get the value of the result more
 * directly.
 * 
 * @author David Peterson
 * 
 * @param <T>
 *            The type of the return value.
 */
public interface SimpleResult<T> extends Result {
    /**
     * Gets the value.
     * 
     * @return The value returned by the result.
     */
    public T get();
}

package net.customware.gwt.dispatch.shared;

import net.customware.gwt.dispatch.shared.BatchAction.OnException;

import java.util.Iterator;
import java.util.List;

/**
 * Contains the list of {@link Result}s from successful actions in the
 * {@link BatchAction}. The order will match the order of the original
 * {@link Action}s listed in the {@link BatchAction}.
 * 
 * <p>
 * If the {@link BatchAction} was specified to have an {@link OnException} value
 * of {@link OnException#CONTINUE}, failed actions will have a <code>null</code>
 * value, and there will be an exception available via the {@link #getExceptions()} or {@link #getException(int)} methods
 * for the same index.
 * 
 * <p>
 * This class also implements {@link Iterable}, so it can be used in 'for'
 * loops.
 * 
 * @author David Peterson
 */
public class BatchResult implements Result, Iterable<Result> {
    private List<Result> results;
    
    private List<Throwable> exceptions;

    /**
     * For serialization.
     */
    BatchResult() {
    }

    /**
     * Creates a new result with the list of results from the batch action, in
     * order.
     * 
     * @param results
     *            The list of results.
     */
    public BatchResult( List<Result> results, List<Throwable> exceptions ) {
        this.results = results;
        this.exceptions = exceptions;
    }

    /**
     * The list of results.
     * 
     * @return
     */
    public List<Result> getResults() {
        return results;
    }
    
    /**
     * The list of exceptions, matched to the result number.
     * @return
     */
    public List<Throwable> getExceptions() {
        return exceptions;
    }

    /**
     * The size of the result set.
     * @return
     */
    public int size() {
        return results.size();
    }

    /**
     * Returns the result for the specified index.
     * 
     * @param i The index.
     * @return The result.
     */
    public Result getResult( int i ) {
        return results.get( i );
    }

    /**
     * Returns the result only if the value at the specified index is of the specified type.
     * It does not support the specified class being a superclass of the result type.
     * 
     * @param <T> The type of result to retrieve.
     * @param i The index.
     * @param type The type value.
     * @return The result, if it matches.
     */
    public <T extends Result> T getResult( int i, Class<T> type ) {
        Object result = results.get( i );
        if ( result != null && type.getName().equals( result.getClass().getName() ) )
            return ( T ) result;
        return null;
    }
    
    public Throwable getException( int index ) {
        return exceptions.get( index );
    }

    public Iterator<Result> iterator() {
        return results.iterator();
    }
}

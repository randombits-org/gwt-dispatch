package net.customware.gwt.dispatch.shared;

import java.util.List;

import net.customware.gwt.dispatch.shared.BatchAction.OnException;

/**
 * Contains the list of {@link Result}s from successful actions in the
 * {@link BatchAction}. The order will match the order of the original
 * {@link Action}s listed in the {@link BatchAction}.
 * 
 * <p>
 * If the {@link BatchAction} was specified to have an {@link OnException} value
 * of {@link OnException#CONTINUE}, failed actions will have a
 * <code>null</code> value.
 * 
 * @author David Peterson
 */
public class BatchResult implements Result {
    private final List<Result> results;

    public BatchResult( List<Result> results ) {
        this.results = results;
    }

    public List<Result> getResults() {
        return results;
    }
}

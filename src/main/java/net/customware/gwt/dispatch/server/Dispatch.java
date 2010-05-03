package net.customware.gwt.dispatch.server;

import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.DispatchException;
import net.customware.gwt.dispatch.shared.Result;

/**
 * Executes actions and returns the results.
 * 
 * @author David Peterson
 */
public interface Dispatch {

    /**
     * Executes the specified action and returns the appropriate result.
     * 
     * @param <T>
     *            The {@link Result} type.
     * @param action
     *            The {@link Action}.
     * @return The action's result.
     * @throws DispatchException
     *             if the action execution failed.
     */
    <A extends Action<R>, R extends Result> R execute( A action ) throws DispatchException;
}

package net.customware.gwt.dispatch.server;

import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.DispatchException;
import net.customware.gwt.dispatch.shared.Result;

/**
 * Instances of this interface will handle specific types of {@link Action}
 * classes.
 * 
 * @author David Peterson
 */
public interface ActionHandler<A extends Action<R>, R extends Result> {

    /**
     * @return The type of {@link Action} supported by this handler.
     */
    Class<A> getActionType();

    /**
     * Handles the specified action.
     * 
     * @param <T>
     *            The Result type.
     * @param action
     *            The action.
     * @return The {@link Result}.
     * @throws DispatchException
     *             if there is a problem performing the specified action.
     */
    R execute( A action, ExecutionContext context ) throws DispatchException;

    /**
     * Attempts to roll back the specified action.
     * 
     * @param action
     *            The action.
     * @param result
     *            The result of the action.
     * @param context
     *            The execution context.
     * @throws DispatchException
     */
    void rollback( A action, R result, ExecutionContext context ) throws DispatchException;
}

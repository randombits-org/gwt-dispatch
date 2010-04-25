package net.customware.gwt.dispatch.server;

import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.ActionException;
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
     * @throws ActionException
     *             if there is a problem performing the specified action.
     * @throws ServiceException
     *             if there is a low-level problem.
     */
    R execute( A action, ExecutionContext context ) throws ActionException;

    /**
     * Attempts to roll back the specified action.
     * 
     * @param action
     *            The action.
     * @param result
     *            The result of the action.
     * @param context
     *            The execution context.
     * @throws ServiceException
     * @throws ActionException
     */
    void rollback( A action, R result, ExecutionContext context ) throws ActionException;
}

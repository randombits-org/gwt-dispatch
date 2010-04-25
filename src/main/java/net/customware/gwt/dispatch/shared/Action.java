package net.customware.gwt.dispatch.shared;

import net.customware.gwt.dispatch.server.Dispatch;

import java.io.Serializable;

/**
 * An action represents a command sent to the {@link Dispatch}. It has a
 * specific result type which is returned if the action is successful.
 *
 * @author David Peterson
 * @param <R>
 * The {@link Result} type.
 */
public interface Action<R extends Result> extends Serializable {
}

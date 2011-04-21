package net.customware.gwt.dispatch.server.counter;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.server.SimpleActionHandler;
import net.customware.gwt.dispatch.shared.ActionException;
import net.customware.gwt.dispatch.shared.DispatchException;
import net.customware.gwt.dispatch.shared.counter.CauseError;
import net.customware.gwt.dispatch.shared.counter.CauseErrorResult;

public class CauseErrorHandler extends SimpleActionHandler<CauseError, CauseErrorResult> {

    public CauseErrorResult execute(CauseError action, ExecutionContext context) throws DispatchException {

        throw new ActionException("Failed as expected.");
    }
}

package net.customware.gwt.dispatch.server.counter;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.ActionException;
import net.customware.gwt.dispatch.shared.DispatchException;
import net.customware.gwt.dispatch.shared.counter.CauseError;
import net.customware.gwt.dispatch.shared.counter.CauseErrorResult;

public class CauseErrorHandler implements ActionHandler<CauseError, CauseErrorResult> {

	public Class<CauseError> getActionType() {
		
		return CauseError.class;
	}

	public CauseErrorResult execute(CauseError action, ExecutionContext context)
			throws DispatchException {
		
		throw new ActionException("Failed as expected.");
	}

	public void rollback(CauseError action, CauseErrorResult result,
			ExecutionContext context) throws DispatchException {
		
		// no rollback
	}

}

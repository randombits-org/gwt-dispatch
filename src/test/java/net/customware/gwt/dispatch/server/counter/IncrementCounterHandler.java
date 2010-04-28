package net.customware.gwt.dispatch.server.counter;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.ActionException;
import net.customware.gwt.dispatch.shared.counter.IncrementCounter;
import net.customware.gwt.dispatch.shared.counter.IncrementCounterResult;

public class IncrementCounterHandler implements
		ActionHandler<IncrementCounter, IncrementCounterResult> {

	private Counter counter;
	
	public IncrementCounterHandler( Counter counter ) {
	    this.counter = counter;
	}

	public Class<IncrementCounter> getActionType() {
		return IncrementCounter.class;
	}

	public IncrementCounterResult execute(IncrementCounter action,
			ExecutionContext context) throws ActionException {
		counter.setValue( counter.getValue() + action.getAmount() );
		return new IncrementCounterResult(action.getAmount(), counter.getValue() );
	}

	public void rollback(IncrementCounter action,
			IncrementCounterResult result, ExecutionContext context)
			throws ActionException {
		// Reset to the previous value.
		counter.setValue( result.getCurrent() - result.getAmount() );
	}
}
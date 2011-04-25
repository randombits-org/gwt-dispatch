package net.customware.gwt.dispatch.server.counter;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.ActionException;
import net.customware.gwt.dispatch.shared.counter.ResetCounter;
import net.customware.gwt.dispatch.shared.counter.ResetCounterResult;

public class ResetCounterHandler implements ActionHandler<ResetCounter, ResetCounterResult> {
    
    private final Counter counter;
    
    public ResetCounterHandler( Counter counter ) {
        this.counter = counter;
    }
    
    public Class<ResetCounter> getActionType() {
        return ResetCounter.class;
    }

    public ResetCounterResult execute( ResetCounter action, ExecutionContext context ) throws ActionException {
        int oldValue = counter.getValue();
        counter.setValue( action.getValue() );
        return new ResetCounterResult( oldValue, counter.getValue() );
    }

    public void rollback( ResetCounter action, ResetCounterResult result, ExecutionContext context )
            throws ActionException {
        counter.setValue( result.getOldValue() );
    }

}

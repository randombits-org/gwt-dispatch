package net.customware.gwt.dispatch.shared.counter;

import net.customware.gwt.dispatch.shared.Result;

public class ResetCounterResult implements Result {
    
    private int oldValue;
    private int newValue;
    
    @SuppressWarnings("unused")
    private ResetCounterResult() {
    }

    public ResetCounterResult( int oldValue, int newValue ) {
        this.newValue = newValue;
        this.oldValue = oldValue;
    }
    
    public int getOldValue() {
        return oldValue;
    }
    
    public int getNewValue() {
        return newValue;
    }
}

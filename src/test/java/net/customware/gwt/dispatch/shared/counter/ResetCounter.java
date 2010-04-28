package net.customware.gwt.dispatch.shared.counter;

import net.customware.gwt.dispatch.shared.Action;

public class ResetCounter implements Action<ResetCounterResult> {
    private int value = 0;
    
    public ResetCounter() {
    }
    
    public ResetCounter( int value ) {
        this.value = value;
    }
    
    public int getValue() {
        return value;
    }
}

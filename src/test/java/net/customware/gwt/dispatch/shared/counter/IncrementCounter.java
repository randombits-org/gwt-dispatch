package net.customware.gwt.dispatch.shared.counter;

import net.customware.gwt.dispatch.shared.Action;

public class IncrementCounter implements Action<IncrementCounterResult> {
    private int amount;

    /** For serialization only. */
    IncrementCounter() {
    }

    public IncrementCounter( int amount ) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }
}
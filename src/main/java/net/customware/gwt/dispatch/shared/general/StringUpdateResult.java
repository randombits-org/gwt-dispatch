package net.customware.gwt.dispatch.shared.general;

import net.customware.gwt.dispatch.shared.AbstractUpdateResult;

/**
 * A simple {@link String} update result. Mostly this is provided to allow GWT
 * to complile when including {@link AbstractUpdateResult} values.
 * 
 * @author David Peterson
 */
public class StringUpdateResult extends AbstractUpdateResult<String> {

    private static final long serialVersionUID = 1L;
    
    protected StringUpdateResult() {
    }

    public StringUpdateResult( String oldValue, String newValue ) {
        super( oldValue, newValue );
    }

}

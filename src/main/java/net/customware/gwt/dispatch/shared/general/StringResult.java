package net.customware.gwt.dispatch.shared.general;

import net.customware.gwt.dispatch.shared.AbstractSimpleResult;

/**
 * A simple {@link String} result. Mostly this is provided to allow GWT to
 * complile when including {@link AbstractSimpleResult} values.
 * 
 * @author David Peterson
 */
public class StringResult extends AbstractSimpleResult<String> {

    private static final long serialVersionUID = 1L;

    protected StringResult() {
    }

    public StringResult( String value ) {
        super( value );
    }
}

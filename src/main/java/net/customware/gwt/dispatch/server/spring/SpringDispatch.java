package net.customware.gwt.dispatch.server.spring;

import net.customware.gwt.dispatch.server.ActionHandlerRegistry;
import net.customware.gwt.dispatch.server.SimpleDispatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * A simple extension of {@link SimpleDispatch} with Spring annotations.
 * 
 * @author David Peterson
 */
@Component
public class SpringDispatch extends SimpleDispatch {

    @Autowired
    public SpringDispatch( ActionHandlerRegistry handlerRegistry ) {
        super( handlerRegistry );
    }
}

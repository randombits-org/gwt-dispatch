package net.customware.gwt.dispatch.server.spring;

import net.customware.gwt.dispatch.server.AbstractDispatch;
import net.customware.gwt.dispatch.server.ActionHandlerRegistry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SpringDispatch extends AbstractDispatch {

    private final ActionHandlerRegistry handlerRegistry;

    @Autowired
    public SpringDispatch( ActionHandlerRegistry handlerRegistry ) {
        this.handlerRegistry = handlerRegistry;
    }

    @Override
    protected ActionHandlerRegistry getHandlerRegistry() {
        return handlerRegistry;
    }

}

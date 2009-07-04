package net.customware.gwt.dispatch.server.guice;

import java.util.List;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ActionHandlerRegistry;

import com.google.inject.Binding;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.TypeLiteral;

/**
 * This class links any registered {@link ActionHandler} instances with the
 * default {@link ActionHandlerRegistry}.
 * 
 * @author David Peterson
 * 
 */
public class ActionHandlerLinker {

    public ActionHandlerLinker() {
    }

    @Inject
    @SuppressWarnings("unchecked")
    public void link( Injector injector, ActionHandlerRegistry actionHandlerRegistry ) {
        List<Binding<ActionHandler>> bindings = injector
                .findBindingsByType( TypeLiteral.get( ActionHandler.class ) );

        for ( Binding<ActionHandler> binding : bindings ) {
            actionHandlerRegistry.addHandler( binding.getProvider().get() );
        }
    }
}

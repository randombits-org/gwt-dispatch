package net.customware.gwt.dispatch.server;

import java.util.List;
import java.util.Map;

import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.Result;

import com.google.inject.Singleton;

@Singleton
public class DefaultActionHandlerRegistry implements InstanceActionHandlerRegistry {

    private final Map<Class<? extends Action<?>>, ActionHandler<?, ?>> handlers;

    public DefaultActionHandlerRegistry() {
        handlers = new java.util.HashMap<Class<? extends Action<?>>, ActionHandler<?, ?>>( 100 );
    }

    public void addHandler( ActionHandler<?, ?> handler ) {
        handlers.put( handler.getActionType(), handler );
    }

    public boolean removeHandler( ActionHandler<?, ?> handler ) {
        return handlers.remove( handler.getActionType() ) != null;
    }

    public <A extends Action<R>, R extends Result> ActionHandler<A, R> findHandler( A action ) {
        return (net.customware.gwt.dispatch.server.ActionHandler<A, R> ) handlers.get( action.getClass() );
    }
    
    /**
     * Sets the specified <tt>actionHandlers</tt> as the only action handler held by this registry instance
     * 
     * @param actionHandlers the list of action handler to set
     */
    public void setActionHandlers(List<ActionHandler<?,?>> actionHandlers) {
        
        clearHandlers();
        
        for ( ActionHandler<?,?> actionHandler : actionHandlers )
            addHandler(actionHandler);
    }

    public void clearHandlers() {
        handlers.clear();
    }

}

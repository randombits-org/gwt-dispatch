package net.customware.gwt.dispatch.server.spring;

import javax.servlet.ServletContext;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextException;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.ContextLoaderServlet;
import org.springframework.web.context.WebApplicationContext;

/**
 * The <tt>PredefinedContextLocationContextLoaderServlet</tt> initialises a Spring context from a predefined location.
 * 
 * <p>This implementation is used to work around the limitations of the GWT test framework , which does not currently
 * allow init parameters to be set.</p>
 * 
 * @author Robert Munteanu
 *
 */
public final class PredefinedContextLocationContextLoaderServlet extends ContextLoaderServlet {
    
    private static final long serialVersionUID = 1L;

    private final String _configLocation;

    public PredefinedContextLocationContextLoaderServlet(String configLocation) {

        _configLocation = configLocation;
    }

    @Override
    protected ContextLoader createContextLoader() {

        return new ContextLoader() {
            
            @Override
            protected WebApplicationContext createWebApplicationContext(ServletContext servletContext,
                    ApplicationContext parent) throws BeansException {

                // method implementation mostly copied from superclass
                
                Class<?> contextClass = determineContextClass(servletContext);
                if (!ConfigurableWebApplicationContext.class.isAssignableFrom(contextClass)) {
                    throw new ApplicationContextException("Custom context class [" + contextClass.getName()
                            + "] is not of type [" + ConfigurableWebApplicationContext.class.getName() + "]");
                }

                ConfigurableWebApplicationContext wac = (ConfigurableWebApplicationContext) BeanUtils
                        .instantiateClass(contextClass);
                wac.setParent(parent);
                wac.setServletContext(servletContext);
                wac.setConfigLocation(_configLocation);
                customizeContext(servletContext, wac);
                wac.refresh();

                return wac;
            }

        };
    }
}
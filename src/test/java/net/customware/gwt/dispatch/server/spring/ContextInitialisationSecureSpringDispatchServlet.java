package net.customware.gwt.dispatch.server.spring;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.springframework.web.context.ContextLoaderServlet;

/**
 * The <tt>ContextInitialisationSpringDispatchServlet</tt> is a <tt>SpringStandardDispatchServlet</tt> which also
 * initialises a Spring ApplicationContext.
 * 
 * <p>This combination is needed as the GWT testing framework seems to instantiate servlets when they are first accessed, 
 * and accessing the servlet just to initialise it from the tests is too much of a burden.</p>
 * 
 * @author Robert Munteanu
 * 
 */
public class ContextInitialisationSecureSpringDispatchServlet extends SpringSecureDispatchServlet {

    private static final long serialVersionUID = 1L;
    
    private final ContextLoaderServlet servlet = new PredefinedContextLocationContextLoaderServlet("classpath:/dispatch-secure-test.xml, classpath:/dispatch-test.xml");

    public void init(ServletConfig config) throws ServletException {

        servlet.init(config);

        super.init(config);

    };

    @Override
    public void destroy() {

        super.destroy();

        servlet.destroy();
    }
}

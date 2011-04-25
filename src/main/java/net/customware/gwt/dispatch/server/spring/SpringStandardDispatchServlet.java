package net.customware.gwt.dispatch.server.spring;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import net.customware.gwt.dispatch.server.Dispatch;
import net.customware.gwt.dispatch.server.standard.AbstractStandardDispatchServlet;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class SpringStandardDispatchServlet extends AbstractStandardDispatchServlet {
    
    @Autowired
    private Dispatch dispatch;
    
    @Override
    public void init(ServletConfig config) throws ServletException {

        super.init(config);
        
        WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
        
        dispatch = (Dispatch) BeanFactoryUtils.beanOfType(ctx, Dispatch.class);
    }
 

    @Override
    protected Dispatch getDispatch() {
        
        return dispatch;
    }
}

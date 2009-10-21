package net.customware.gwt.dispatch.server.spring;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import net.customware.gwt.dispatch.server.Dispatch;
import net.customware.gwt.dispatch.server.standard.AbstractStandardDispatchServlet;

public class SpringStandardDispatchServlet extends AbstractStandardDispatchServlet {
    
    private Dispatch dispatch;
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        WebApplicationContext ctx = WebApplicationContextUtils
                .getRequiredWebApplicationContext(config.getServletContext());
        AutowireCapableBeanFactory beanFactory = ctx
                .getAutowireCapableBeanFactory();
        beanFactory.autowireBean(this);
    }
 

    @Override
    protected Dispatch getDispatch() {
        return dispatch;
    }
    
    @Autowired
    @Required
    public void setDispatch(Dispatch dispatch) {
        this.dispatch = dispatch;
    }

}

package net.customware.gwt.dispatch.server.spring;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import net.customware.gwt.dispatch.server.Dispatch;
import net.customware.gwt.dispatch.server.secure.AbstractSecureDispatchServlet;
import net.customware.gwt.dispatch.server.secure.SecureSessionValidator;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class SpringSecureDispatchServlet extends AbstractSecureDispatchServlet {

    private Dispatch dispatch;

    private SecureSessionValidator sessionValidator;

    @Override
    public void init( ServletConfig config ) throws ServletException {
        
        super.init( config );
        
        WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext( config .getServletContext() );
        
        dispatch = (Dispatch) BeanFactoryUtils.beanOfType(ctx, Dispatch.class);
        sessionValidator = (SecureSessionValidator) BeanFactoryUtils.beanOfType(ctx, SecureSessionValidator.class);
    }

    @Override
    protected Dispatch getDispatch() {
        return dispatch;
    }

    @Override
    protected SecureSessionValidator getSessionValidator() {
        return sessionValidator;
    }

}

package net.customware.gwt.dispatch.server.osgi;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * <p>
 * Filter that swaps the current thread context class loader to the one given.
 * This is required to workaround class loading issues with GWT RPC within an
 * OSGi environment.
 * </p>
 * <p>
 * GWT RPC uses the current thread context class loader to find classes when
 * serialising and deserialising for RPC. Within an OSGi environment not all the
 * classes are likely to be available in this class loader. This filter swaps
 * the class loader for one provided. This class loader should be able to "see"
 * all the classes needed by the application. At a minimum when using
 * gwt-dispatch this class loader should be able to load classes from the
 * gwt-servlet, gwt-dispatch and the web application bundles. This can be
 * achieved by using the <code>MultiClassLoader</code> class.
 * </p>
 * <p>
 * Refs:
 * <ul>
 * <li>
 * See GWT Issue 1888 - Allow to pass in class loader on RPC deserialization.</li>
 * <li>An example of an OSGi gwt-dispatch library is available on clazzes.org
 * (http://svn.clazzes.org/svn/gwt/trunk/gwt-osgi/).</li>
 * </ul>
 * </p>
 * 
 * @author Paul Illingworth
 */
public class ThreadContextClassLoaderSwappingFilter implements Filter {

    final static Logger LOGGER = Logger.getLogger(ThreadContextClassLoaderSwappingFilter.class.getName());

    private final ClassLoader classLoader;

    /**
     * Create the filter using given set of class loaders.
     * 
     * @param classLoaders
     */
    public ThreadContextClassLoaderSwappingFilter(ClassLoader... classLoaders) {

        this(new MultiClassLoader(classLoaders));
    }

    /**
     * Create the filter using the given class loader.
     * 
     * @param classLoaders
     */
    public ThreadContextClassLoaderSwappingFilter(ClassLoader classLoader) {

        this.classLoader = classLoader;
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(classLoader);
        try {
            chain.doFilter(request, response);
        } finally {
            Thread.currentThread().setContextClassLoader(cl);
        }
    }
}
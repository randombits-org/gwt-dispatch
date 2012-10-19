package net.customware.gwt.dispatch.server.osgi;

import java.net.URL;

/**
 * Class loader implementation that tries all the class loaders in a given
 * array, one after the other, in the order given, to find resources and
 * classes.
 * 
 * @author Paul Illingworth
 */
public class MultiClassLoader extends ClassLoader {

    private final ClassLoader[] loaders;

    public MultiClassLoader(ClassLoader... loaders) {

        this.loaders = loaders;
    }

    @Override
    public URL getResource(String name) {

        for (ClassLoader loader : loaders) {
            URL url = loader.getResource(name);
            if (url != null)
                return url;
        }
        return null;
    }

    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {

        for (ClassLoader loader : loaders) {
            try {
                return loader.loadClass(name);
            } catch (ClassNotFoundException e) {
                /* ignore; try next class loader */
            }
        }
        throw new ClassNotFoundException(name);
    }
}
package net.customware.gwt.dispatch.server.standard;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author Robert Munteanu
 */
public abstract class GenericUtils {

    public static Class<?> getFirstTypeParameterDeclaredOnSuperclass(Class<?> clazz) {

        if ( clazz == null )
            throw new IllegalArgumentException("clazz may not be null");
        
        Type classGenType = clazz.getGenericSuperclass();

        // special CGLIB workaround -- get generic superclass of superclass
        if (clazz.getName().contains("$$EnhancerByCGLIB$$")) {
            classGenType = clazz.getSuperclass().getGenericSuperclass();
        }

        if (classGenType instanceof ParameterizedType) {
            Type[] params = ((ParameterizedType) classGenType).getActualTypeArguments();

            if ((params != null) && (params.length >= 1)) {
                return (Class<?>) params[0];
            }
        }

        for (Type ifGenType : clazz.getGenericInterfaces()) {
            if (ifGenType instanceof ParameterizedType) {
                Type[] params = ((ParameterizedType) ifGenType).getActualTypeArguments();

                if ((params != null) && (params.length >= 1)) {
                    return (Class<?>) params[0];
                }
            }
        }

        throw new IllegalArgumentException("No type parameters found on " + clazz.getSimpleName());
    }
    
    private GenericUtils() {
        
    }
}

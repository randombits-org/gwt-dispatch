package net.customware.gwt.dispatch.server.standard;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @author Robert Munteanu
 */
public class GenericUtilsTest {

    @Test(expected = IllegalArgumentException.class)
    public void nullClassThrowsException() {
        
        GenericUtils.getFirstTypeParameterDeclaredOnSuperclass(null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void noParametersClassThrowsException() {
        
       GenericUtils.getFirstTypeParameterDeclaredOnSuperclass(new Base<Object, Object>().getClass());
    }
    
    @Test
    public void firstParameterIsExtracted() {
        
        assertEquals(String.class, GenericUtils.getFirstTypeParameterDeclaredOnSuperclass(new Impl().getClass()));
    }
    
    private static class Base<T1, T2> {
        
    }
    
    private static class Impl extends Base<String, Integer> {
        
    }
}

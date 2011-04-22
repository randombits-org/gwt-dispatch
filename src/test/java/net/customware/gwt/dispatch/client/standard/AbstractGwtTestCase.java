package net.customware.gwt.dispatch.client.standard;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;


/**
 * @author Robert Munteanu
 *
 */
public abstract class AbstractGwtTestCase extends GWTTestCase {
    
    protected static final int TEST_DELAY = 2000;
    
    protected abstract static class TestCallback<T> implements AsyncCallback<T> {
        public void onFailure( Throwable e ) {
            throw new RuntimeException( e );
        }
    }
}

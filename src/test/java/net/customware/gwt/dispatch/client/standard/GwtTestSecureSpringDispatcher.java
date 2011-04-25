package net.customware.gwt.dispatch.client.standard;

import net.customware.gwt.dispatch.client.ExceptionHandler;
import net.customware.gwt.dispatch.client.secure.CookieSecureSessionAccessor;
import net.customware.gwt.dispatch.client.secure.SecureDispatchAsync;
import net.customware.gwt.dispatch.shared.counter.IncrementCounter;
import net.customware.gwt.dispatch.shared.counter.IncrementCounterResult;

public class GwtTestSecureSpringDispatcher extends AbstractGwtTestCase {

    private SecureDispatchAsync dispatch;

    @Override
    public String getModuleName() {
        return "net.customware.gwt.dispatch.SecureSpringDispatchTest";
    }

    @Override
    protected void gwtSetUp() throws Exception {

        dispatch = new SecureDispatchAsync( new ExceptionHandler() {
            public Status onFailure( Throwable e ) {
                throw new RuntimeException( e );
            }
        }, new CookieSecureSessionAccessor("TEST") );

        super.gwtSetUp();
    }

    @Override
    protected void gwtTearDown() throws Exception {
        dispatch = null;

        super.gwtTearDown();
    }
    
    public void testIncrementCounter() {
        
        dispatch.execute( new IncrementCounter( 1 ), new TestCallback<IncrementCounterResult>() {
            public void onSuccess( IncrementCounterResult result ) {
                assertEquals( 1, result.getCurrent() );
                finishTest();
            }
        } );
        
        // Set a delay period significantly longer than the
        // event is expected to take.
        delayTestFinish( TEST_DELAY );
    }
}

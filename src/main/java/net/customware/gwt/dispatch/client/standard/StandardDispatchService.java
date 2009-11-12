package net.customware.gwt.dispatch.client.standard;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.dispatch.server.Dispatch;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.Result;

/**
 * There currently seem to be GWT compilation problems with services that use
 * generic templates in method parameters. As such, they are stripped here, but
 * added back into the {@link Dispatch} and {@link DispatchAsync}, which are
 * the interfaces that should be accessed by regular code.
 * <p/>
 * <p/>
 * Once GWT can compile templatized methods correctly, this should be merged
 * into a single pair of interfaces.
 *
 * @author David Peterson
 */
@RemoteServiceRelativePath("dispatch")
public interface StandardDispatchService extends RemoteService {
    Result execute( Action<?> action ) throws Exception;
}

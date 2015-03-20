You can set the RPCRequestBuilder on you dispatch instance if you downcast it to `ServiceDefTarget` and set a `RpcRequestBuilder`:

```
((ServiceDefTarget) service).setRpcRequestBuilder(this.authenticatingRequestBuilder);
```

Where a RPC Build can look similar to:

```
public class AuthenticatingRequestBuilder extends RpcRequestBuilder {

       private final LoginProvider loginProvider;

       @Inject
       public AuthenticatingRequestBuilder(LoginProvider loginProvider) {
               this.loginProvider = loginProvider;

       }

       @Override
       protected RequestBuilder doCreate(String serviceEntryPoint) {
               RequestBuilder requestBuilder = super.doCreate(serviceEntryPoint);
               UserLogin userLogin = loginProvider.get();
               if (userLogin != null) {
                       requestBuilder.setHeader("X-GWT-CRED", userLogin.getCredentials());
               }
               return requestBuilder;
       }
}
```

(Picked up from [a gwt-dispatch group discussion](https://groups.google.com/d/topic/gwt-dispatch/e8zFUD5OWKM/discussion) )
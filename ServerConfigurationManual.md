# Introduction #

In some cases it may be necessary to configure the `DispatchServiceServlet` manually (as opposed to using [Guice](ServerConfigurationWithGuice.md) or [Spring](ServerConfigurationWithSpring.md)).

# Details #

There are two basic Servlet implementations available - `StandardDispatchServiceServlet` and `SecureDispatchServiceServlet`. They are both configured in the same way, but the secure version also needs an instance of a `SecureSessionValidator`. More detail about standard vs. secure is [here](SecureDispatchService.md).

Because both classes do not have a default constructor, projects which are configured manually will need to create a subclass that provides the required services. Below is an example of a subclass using a 'standard' system.

## `DispatchUtil` ##

We need to create a global instance of the `Dispatch` service. There are lots of ways to do this, but for simplicity we'll use a static instance in this example.

```
public final class DispatchUtil {

  private static final DefaultActionHandlerRegistry REGISTRY
      = new DefaultActionHandlerRegistry();

  private static final Dispatch DISPATCH = new DefaultDispatch( REGISTRY );

  public static void registerHandler( ActionHandler<?, ?> handler ) {
    REGISTRY.addHandler( handler );
  }

  public static Dispatch getDispatch() {
    return DISPATCH;
  }
}
```

## `MyDispatchServiceServlet` ##

Now, we create the servlet implementation, which is quite simple.

```
public class MyDispatchServiceServlet extends StandardDispatchServiceServlet {
  public MyDispatchServiceServlet() {
    super( DispatchUtil.getDispatch() );
  }
}
```

## `MyActionHandlersConfig` ##

Next we need to register any [ActionHandlers](ActionHandler.md) we need for the application. In this case we're going to use a `ServletContextListener` to do it when the application starts up, but it could be done anywhere.

```
public class MyActionHandlersConfig implements ServletContextListener {
  public void contextInitialized( ServletContextEvent evt ) {
    DispatchUtil.registerHandler( new GetFooHandler() );
    DispatchUtil.registerHandler( new SaveFooHandler() );
    // Etc.
  }

  public void contextDestroyed( ServletContextEvent evt ) {
    // Do nothing...
  }
}
```

## `WEB-INF/web.xml` ##

Lastly, we configure the servlet and context listener. Just add some standard elements to your web.xml file:

```
<web-app>

  <servlet>
    <servlet-name>dispatch</servlet-name>
    <servlet-class>my.package.MyDispatchServiceServlet</servlet-class>
  </servlet> 

  <servlet-mapping>
    <servlet-name>dispatch</servlet-name>
    <!-- '/dispatch' is the default location that the client-side classes point at. -->
    <url-pattern>/dispatch</servlet-name>
  </servlet-mapping>

  <listener>
    <listener-class>my.package.MyActionHandlersConfig</listener-class>
  </listener>

</web-app>
```

# Conclusion #

Once this is configured, you can get hold of the `Dispatch` instance from wherever you are and call the 'execute' method, be it from the client side or the server side.
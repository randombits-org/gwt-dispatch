# Guice configuration #

Configure the Guice as usual, by declaring a `GuiceFilter` to intercept all needed servlet calls and a `ServletContextLister` to bootstrap your modules. Your web.xml should minimally contain

```
<filter>
	<filter-name>guiceFilter</filter-name>
	<filter-class>com.google.inject.servlet.GuiceFilter</filter-class>
</filter>
<filter-mapping>
	<filter-name>guiceFilter</filter-name>
	<url-pattern>/*</url-pattern>
</filter-mapping>
	
<listener>
	<listener-class>com.example.BootstrapListener</listener-class>
</listener>
```

Please see the [Guice servlet reference documentation](http://code.google.com/p/google-guice/wiki/ServletModule) for more details.

# Dispatch-specific modules #

The first module you should install is a `ServletModule` which routs the dispatch calls to a DispatchServlet:

```
public class DispatchServletModule extends ServletModule {

    @Override
    protected void configureServlets() {    
        serve("/$MODULE/dispatch").with(GuiceStandardDispatchServlet.class);
    }
}
```

Substitute `$MODULE` for the location where the GWT remote services are found, e.g. `com.example.Module`.

Next you should bind your `ActionHandler`s . GWT-dispatch provides a base module which you can extend to easily bind your handlers:

```
public class ActionsModule extends ActionHandlerModule {

    @Override
    protected void configureHandlers() {

        bindHandler(IncrementCounter.class, IncrementCounterHandler.class);
    }

}
```

To wrap up, configure all the needed modules in your custom `ServletContextListener`

```
public class BootstrapListener extends GuiceServletContextListener {

    @Override
    protected Injector getInjector() {

        return Guice.createInjector(new ServerDispatchModule(), new ActionsModule(), new DispatchServletModule());
    }
}
```

# Sample application #

The [gwt-dispatch-sample project](https://github.com/rombert/gwt-dispatch-sample) contains an application built on Guice.
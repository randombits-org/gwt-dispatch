# Spring configuration #

The Spring web application context should be configured as usual, by using the ContextLoaderListener or ContextLoaderServlet. In its simplest form web.xml should contain the following:

```
<listener>
    <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
</listener>
```

Spring will then look for the configuration file in the default `/WEB-INF/applicationContext.xml` location. Please see [the Spring reference documentation](http://static.springsource.org/spring/docs/2.5.x/reference/beans.html#context-create) for more details.

# Dispatch servlet #

Map a dispatch servlet implementation in the web.xml file. This can be either the Standard or the Secure implementation.

```
<servlet>
    <servlet-name>dispatch</servlet-name>
    <servlet-class>net.customware.gwt.dispatch.server.spring.SpringStandardDispatchServlet</servlet-class>
    <load-on-startup>1</load-on-startup>
</servlet>

<servlet-mapping>
    <servlet-name>dispatch</servlet-name>
    <url-pattern>/$MODULE/dispatch</url-pattern>
</servlet-mapping>
```

Substitute `$MODULE` for the location where the GWT remote services are found, e.g. `com.example.Module`.

# Dispatch-specific beans #

The beans should be declared in the top-level application context file. You should declare at least

  * the dispatch bean
  * the action handler registry
  * one action handler

The minimal declaration you can use is:

```
<bean id="dispatch" class="net.customware.gwt.dispatch.server.spring.SpringDispatch">
    <constructor-arg index="0" ref="registry"/>
</bean>
	
<bean id="registry" class="net.customware.gwt.dispatch.server.DefaultActionHandlerRegistry">
    <property name="actionHandlers">
        <list>
            <bean class="net.customware.gwt.dispatch.server.counter.IncrementCounterHandler"/>
        </list>
    </property>
</bean>
```

Optionally, you can declare a secure session validator if you are using the Secure variant.

Obviously, you can use any form of configuration for your beans, including autowiring and classpath scanning.
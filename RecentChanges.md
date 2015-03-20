# Introduction #

A list of recent changes to the API.

# ???-??-??? (1.2.1) #

  * gwt-dispatch is now a valid OSGi bundle ( thanks pillingworth )

# 26-Apr-2011 (1.2.0) #

  * Added `SimpleActionHandler` with default implementations for `getActionType` and `rollback`;
  * Added `DefaultActionHandlerRegistry.setActionHandlers` for better integration with Spring;
  * Added `AbstractDispatch` hook methods `executing`, `executed` and `failed`.

# 21-Oct-2009 #

  * Refactored base API to remove Guice dependencies.
    * `DefaultDispatch` has become `GuiceDispatch` and moved into '`*`.server.guice'
    * `AbstractDispatch` was added and can serve as a simple base class for other implementations
    * `StandardDispatchServiceServlet` and `SecureDispatchServiceServlet` were refactored into `AbstractStandardDispatchServlet` and `AbstractSecureDispatchServlet`, and Guice-specific code put into the `GuiceStandardDispatchServlet` and `GuiceSecureDispatchServlet` classes.
  * Added initial Spring support classes into '`*`.server.spring'
  * Refactored 'secure' dispatch API
    * Added `SecureSessionAccessor` (client side) and `SecureSessionValidator` (server side) to support checking more varied authentication options.
  * Added initial support classes for Google App Engine integration.

# 27-Aug-2009 #

  * Released version 1.0.0
    * No major changes from the previous SNAPSHOT release.
  * Checked in new updates for 1.1.0-SNAPSHOT:
    * Added `SecureDispatchService/Async/Servlet` to support authenticated session checks.
    * Renamed `DispatchService/Async/Servlet` to `StandardDispatchService/Async/Servlet` to clarify difference with Secure versions. Existing references to `DispatchService` should switch to `StandardDispatchService`.
    * Removed the `addHandler` and `removeHandler` methods from `ActionHandlerRepository`.
    * Added the `InstanceActionHandlerRepository` interface, with methods for adding `ActionHandler` instances.
    * Added the `ClassActionHandlerRepository` interface, with methods for adding `ActionHandler` classes for lazy-loading.
    * Added the `LazyActionHandlerRepository` and `GuiceActionHandlerRepository` with support for lazy-loading `ActionHandler` classes on demand.


# 8-Jul-2009 #

  * `ServerDispatchModule` is now optional, and should only be installed to override the default configuration for `Dispatch` and `ActionHandlerRegistry`. If you do want to override, make sure your `ServerDispatchModule` is installed before any `ActionHandlerModules`.
  * `ActionHandlerModule` has been added, and should be used to register custom `ActionHandlers`. It will automatically bind `DefaultDispatch` and `DefaultActionHandlerRegistry` if no `ServerDispatchModule` has been installed.
  * Made the error message if no handler is installed for an action a little clearer.
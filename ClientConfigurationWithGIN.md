# Gin modules #

GWT-dispatch provides two built-in Gin modules, the `StandardDispatchModule` and `SecureDispatchModule`. These can be added to an existing `Ginjector` for usage as-is.

```
@MyGinjector({MyModule.class, StandardDispatchModule.class})
```

Alternatively you can use `install` in an existing module to override the default settings:

```
@Override
protected void configure() {

    install(new StandardDispatchModule(MyExceptionHandler.class)); 
}
```
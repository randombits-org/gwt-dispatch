# Introduction #

Actions and Results form the core of the Command Pattern.

# Details #

When you want to perform a command, the Action and Result objects define what the input and output will be. The Action contains the input parameter and the Result contains the output values. They do not define _how_ the action will take place.

In the Command Pattern these would usually be called the Command and Response, but both terms are quite overloaded in common Java APIs, particularly in GWT, so Action and Result are being used in this API instead.

# Defining an Action/Result #

An `Action` will always have on specific `Result` type. A `Result` may be returned by more than one `Action` type, however.

Here is a simple example that will retrieve a `Foo` object based on its ID.

## `Foo` Object ##

```
public class Foo implements Serializable {
  private String id;
  private String value;

  /* Required for serialization */
  Foo() {}

  /* Standard constructor */
  public Foo( String id, String value ) {
    this.id = id;
    this.value = value;
  }

  public String getId() {
    return id;
  }

  public String getValue() {
    return value;
  }
}
```

Key things to note here are that the object is `Serializable` and all fields are also `Serializable`. Also, there is a default constructor, which in this case is package-private.

## `GetFoo` ##

```
public class GetFoo implements Action<GetFooResult> {
  private String id;

  public GetFoo( String id ) {
    this.id = id;
  }

  public String getId() {
    return id;
  }
}
```

Here, the action specifies that it will return a `GetFooResult`, and will pass in a String id.

## `GetFooResult` ##

```
public class GetFooResult implements Result {
  private Foo value;

  /* For serialization */
  GetFooResult() {}

  public GetFooResult( Foo value ) {
    this.value = value;
  }

  public Foo getValue() {
    return value;
  }
}
```

Again, all fields must be Serializable for this to work from the client side.

# Usage #

Now that we have the Action and Result defined, we just need to send it to the Dispatch service to find our value.

You can actually do this from either the client-side or the server side, although the technique is a little different depending on your environment.

## Client-Side ##

On the client side, it will be executed through an asynchronous call to the server. For this we will use an implementation of the `DispatchAsync` interface. In the simple case you can simply create a new `DefaultDispatchAsync`, but in reality you will want to have a global instance somewhere.

The actual execution will look something like this:

```
DispatchAsync dispatch = ...; // eg, new DefaultDispatchAsync();
dispatch.execute( new GetFoo( myId ), new AsyncCallback<GetFooResult>() {
  public void onSuccess( GetFooResult result ) {
    doSomethingWith( result.getFoo() );
  }

  public void onFailure( Throwable e ) {
    // Add error handling here...
  }
} );
```

## Server-Side ##

On the server it's simpler. Again, we just call the `dispatch.execute(...)` method, but the call is synchronous here, so it will look like this:

```
try {
  Foo foo = dispatch.execute( new GetFoo( myId ) );
  doSomethingWith( foo );
} catch ( Exception e ) {
  // Add error handling here...
}
```

## Conclusion ##

Executing actions is fairly straight forward. The next step is creating the actual functionality that gets executed. For more information about that, see [ActionHandlers](ActionHandler.md).
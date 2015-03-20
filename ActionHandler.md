# Introduction #

`ActionHandlers` provide the actual functionality of Action/Response requests. They provide and `execute` and `rollback` methods to make it easy to do and undo an action.


# Details #

Working with our `Foo` example from [Actions and Results](ActionsAndResults.md), we will now create an `ActionHandler` implementation to actually get the `Foo`.

```
public class GetFooHandler implements ActionHandler<GetFoo, GetFooResult> {
  private final FooDOA fooDoa;

  public GetFooHandler( FooDOA fooDoa ) {
    this.fooDoa = fooDoa;
  }

  // Used by the registry to map to the Action class. 
  public Class<GetFoo> getActionType() { return GetFoo.class }

  public GetFooResult execute( GetFoo action, ExecutionContext context )
      throws ActionException {
    try {
      return new GetFooResult( fooDoa.get( action.getId() );
    } catch ( Exception e ) {
      // Normally, catch more specific exceptions
      throw new ActionException( e );
    }
  }

  public void rollback( GetFoo action, GetFooResult result, ExecutionContext context )
      throws ActionException {
    // Do nothing - can't rollback a 'get'.
  }
}
```

You'll note the 'rollback' function here is a null-op. 'Get' operations don't make changes, so it's kind of pointless. Here's how a 'CreateFoo' handler might look:

```
public class CreateFooHandler implements ActionHandler<CreateFoo, GetFooResult> {
  private final FooDOA fooDoa;

  public CreateFooHandler( FooDOA fooDoa ) {
    this.fooDoa = fooDoa;
  }

  // Used by the registry to map to the Action class. 
  public Class<CreateFoo> getActionType() { return CreateFoo.class }

  public GetFooResult execute( CreateFoo action, ExecutionContext context )
      throws ActionException {
    try {
      // Create the new Foo
      String id = fooDoa.create( action.getValue() );
      // Grab it and return the value.
      return new GetFooResult( fooDoa.get( action.getId() );
    } catch ( Exception e ) {
      // Normally, catch more specific exceptions
      throw new ActionException( e );
    }
  }

  public void rollback( GetFoo action, GetFooResult result, ExecutionContext context )
      throws ActionException {
    try {
      fooDoa.delete( result.getValue().getId() );
    } catch ( Exception e ) {
      throw new ActionException( e );
    }
  }
}
```

You'll note that we're reusing the `GetFooResult` class here, since it is essentially the same result. You might chose to have a unique result for `CreateFooResult` if you wish.

The other difference here is that `rollback` now has some work to do.
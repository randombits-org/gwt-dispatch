# Introduction #

You can create 'compound actions' by executing other actions via the `ExecutionContext`. The advantage of this is that any actions executed via `ExecutionContext.execute()` will automatically roll back if any action fails, including the current action.

## Example: Register a new user ##

For example, when you register a new user you may want to also add them to one or more groups. But in your authentication system creating a group and assigning users to groups are separate steps. If any of the group-addition steps fail, the whole thing should fail - the user should not exist in a groupless state.

The way to achieve this is to break up the action into sub-actions, and have the original action simply execute them internally via the `ExecutionContext` passed in to each `ActionHandler` when it is executed.

In the 'add user' example, this would break down as follows:

  * `RegisterUser` - Contains user and group details for the new user. The result is that the user is created and added to the listed groups.
  * `CreateUser` - Creates the new user with no groups assigned. The result is that tthe user is created, not assigned to any groups. Will fail if the user already exists.
  * `AddUserToGroup` - Adds the user to the specified group. Will fail if the group does not exist.

## `RegisterUser` ##

```
/** Register a new user added to the specified groups. */
public class RegisterUser implements Action<RegisterUserResult> {
    private User user;
    private Set<Group> groups;

    RegisterUser() {}

    public RegisterUser( User user, Set<Group> groups ) {
        this.user = user;
        this.groups = groups;
    }

    public User getUser() { return user; }
    public Set<Group> getGroups() { return groups; }

}

public class RegisterUserResponse implements Response {
}
```

## `CreateUser` ##

```
public class CreateUser implements Action<CreateUserResult> {
    private User user;

    CreateUser() {}

    public CreateUser( User user ) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}

public class CreateUserResult implements Result {
}
```

## `AddUserToGroup` ##

```
public class AddUserToGroup implements Action<AddUserToGroupResult> {
    private User user;
    private Group group;

    AddUserToGroup() {}

    public AddUserToGroup( User user, Group group ) {
        this.user = user;
        this.group = group;
    }

    public User getUser() { return user; }
    public Group getGroup() { return group; }
}

public class AddUserToGroupResult implements Result {}
```

## `ActionHandler` implementations ##

Next, you have to implement your handlers. We'll start with the `CreateUser` handler:

```
public class CreateUserHandler implements ActionHandler<CreateUser, CreateUserResult> {

    private final UserDAO userDao;

    @Inject
    public CreateUserHandler( UserDAO userDao ) {
        this.userDao = userDao;
    }

    public Class<CreateUser> getActionType() { return CreateUser.class; }

    public CreateUserResult execute( CreateUser action, ExecutionContext ctx )
            throws ActionException {
        try {
            // Create our new user somehow
            userDao.create( action.getUser() );
            return new CreateUserResult( action.getUser() );
        } catch ( SomeException e ) {
            // An exception was thrown. Pass it back up the chain
            throw new ActionException( e );
        }
    }

    public void rollback( CreateUser action, CreateUserResult result, ExecutionContext ctx )
            throws ActionException {
        try { 
            userDao.delete( action.getUser().getUsername() );
        } catch ( SomeException e ) {
           throw new ActionException( e );
        }
    }
}
```

You would do something similar for your `AddUserToGroupHandler`. The actual implementation would be specific to your application. The more interesting case is our `RegisterUserHandler`, which becomes fairly simple:

```
public class RegisterUserHandler implements ActionHandler<RegisterUser, RegisterUserResult> {
    public Class<RegisterUser> getActionType() { return RegisterUser.class; }

    public RegisterUserResult execute( RegisterUser action, ExecutionContext ctx )
            throws ActionException {
        // First, create the new user
        ctx.execute( new CreateUser( action.getUser() );
        
        // Next, add the user to each of the groups
        for ( Group group : action.getGroups() ) {
            ctx.execute( new AddUserToGroup( action.getUser(), group );
        }
    }

    public void rollback( RegisterUser action, RegisterUserResult result, ExecutionContext ctx )
            throws ActionException {
        // Nothing to do, because CreateUser and AddUserToGroup will automatically roll back.
    }
}
```

This setup will guarantee that if any step fails, be it creating the user or the 3rd group addition, the whole thing will be rolled back safely. To trigger it, just do the following:

```
try {
    dispatch.execute( new RegisterUser( user, groupSet ) );
} catch ( ActionException e ) {
    // Handle the error.
}
```

## Alternative: `BatchAction` ##

There is a built-in class called `BatchAction`. In general it is recommended to create custom actions for specific tasks, but if you can't be bothered, here is how you would execute the `register user` action using `BatchAction`:

```
try {
    dispatch.execute( new BatchAction( OnException.FAIL, new CreateUser( user ), 
        new AddUserToGroup( group1 ), new AddUserToGroup( group2 ) ) );
} catch (ActionException e ) {
    // Handle the error.
}
```

You will also need to bind the `BatchActionHandler` somewhere in your configuration.

This works in essentially the same way, executing each action passed in via the `ExecutionContext.execute()` method, and provides a handy way of bundling a few actions into one. The `BatchResult` will contain a list of `Result` values returned from each successfully executed Action, in the order they were executed.
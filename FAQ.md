**1. Why am I getting GWT compiler errors regarding `com.google.gwt.inject` and `com.google.inject` ?**

GWT-dispatch provides integration with [Gin](http://code.google.com/p/google-gin/), and classes from the above packages are required to succesfully compile the project.

However, if you are not using Gin and not referencing the classes from gwt-dispatch which interface with Gin, you will receive harmless compiler errors. You can safely ignore them.
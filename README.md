# gwt-dispatch
_Implements a reusable 'command pattern' API for GWT._

# Welcome

Inspired by Ray Ryan's [Best Practices For Architecting Your GWT App](http://code.google.com/events/io/sessions/GoogleWebToolkitBestPractices.html) session at Google I/O 2009, this is an implementation of the 'command pattern' discussed at the beginning of the video.

You may also be interested in the [gwt-presenter](https://github.com/randombits-org/gwt-presenter) library, which is an implementation of the 'Presenter' API mentioned in Ray's presentation.

# Links

* [Development Guide](https://github.com/randombits-org/gwt-dispatch/wiki/Home) - Useful information for developers.
* [Getting Started](https://github.com/randombits-org/gwt-dispatch/wiki/GettingStarted) - A quick intro to the API.
* [API Discussions](http://groups.google.com/group/gwt-dispatch) - The Google Group for this library. *Post any questions you have here!*

# Configuration

Currently [Guice](http://code.google.com/p/google-guice/) and [GIN](http://code.google.com/p/google-gin/) helpers are bundled in, but they are optional - the API can be initialised directly or via any other DI or other scheme you wish.

This is a local repository in which a snapshot-version of the Scalameta Paradise plugin is provided.
 
We ran into a bug with the application of Scalameta inline macros, which required us to move from the 
 3.0.0-M5 version of the plugin to the 3.0.0.95 development version. Due to the fact that this version
 was not available through Maven central, we had to include it in the project for the time being.
 
As soon as a new release of the plugin is made available, this construct will be removed in favor
of the normal method of retrieving dependencies.

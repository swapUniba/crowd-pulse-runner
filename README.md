crowd-pulse-runner
==================

Crowd Pulse main runner.

------------------

This package introduces the `CrowdBlade` class, that can be
used as the entry point for every Crowd Pulse configuration.

A Crowd Pulse configuration can then:

1. Specify this module as a dependency
2. Set `mainClassName = 'CrowdBlade'` in `build.gradle`
3. Add any needed plugin as a `compile` dependency
4. Add resources as needed
5. Build
6. Profit!

You can also build the runner and manually add jars to the classpath (but do you even, dude?).

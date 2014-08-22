EntityAPI
=========

A Bukkit API that enables you to easily control your own custom entities

Current code base only supports 1.7.2. We will be updating to the latest Minecraft version once we are satisfied with our API.

In meanwhile you can listen to this song: https://www.youtube.com/watch?v=2fngvQS_PmQ *cough*

And nope, there's currently no JavaDocs or any sort of documentation on the API...yet...

Todo:
=====

Stuff that needs to be done or is still unfinished (this list may be incomplete):
- API docs
- Player entity
- Pathfinding
- Game registry - DONE
- Modifications to spawning and despawning to work with NMS entity registration - DONE
- Entity serialization
- Fix/rewrite the Main/Core components of the plugin part. I don't really like the utils either.

ToFix
=====

Stuff that needs to be fixed
- When an entity is being killed, an NPE is thrown. http://puu.sh/b1TFy/12b0c6d5a5.txt

Contributing
============

[Pull Requests should always follow the EntityAPI guidelines](CONTRIBUTING.md).

Compiling
=========

We use maven to handle the compilation of EntityAPI
- Install [Maven 3](http://maven.apache.org/download.html)
- Run `mvn` and EntityAPI will automatically build and compile for you

All contributions must be thoroughly tested and debugged.

Collaborators
=============
- DSH105 (Author)
- Frostalf (Author)
- CaptainBern (Author, Master Yi, All hail the captain.)
- drtshock (Contributor <3)

# Flight Blocks x Sable Compat

Compatibility patch for Minecraft 1.21.1, NeoForge, Flight Blocks 1.0.8 and
Sable 2.0.3.

Flight Blocks normally compares the player's world position with a flight
block's stored plot position. For a flight block assembled into a Sable
sublevel, those coordinates refer to different spaces and the player loses
flight. This mod projects the block position out of the Sable sublevel before
the range check.

Build with Java 21:

```powershell
gradle build
```

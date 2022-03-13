
### Dimension Data Fix

In MC 1.18.2, sometimes, after uninstalling a dimension datapack or a dimension mod, Minecraft cannot open the world:

![2022-03-13_18.18.40.png](https://s2.loli.net/2022/03/13/JN7WlUrEzoyYDM9.png)

And clicking "Safe Mode" will have no effect.

This issue occurs when that datapack define its own WorldGen-related things (for example, noise settings).

This mod will fix this issue.

This mod does two things:
* Prevent the non-vanilla dimensions from being saved into `level.dat` file
* When loading a world, prevent the custom dimension data from being fed into DFU and cause error

Note: If you uninstalled a dimension datapack that does not define its own WorldGen-related things, then Minecraft will maybe successfully load the world but that datapack's dimension won't be removed. By installing this mod, the dimensions of the uninstalled datapacks will be removed.




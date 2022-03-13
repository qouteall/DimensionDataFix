
### Dimension Data Fix

In MC 1.18.2, after uninstalling a dimension datapack or a dimension mod, Minecraft cannot open the world:

![2022-03-13_18.18.40.png](https://s2.loli.net/2022/03/13/JN7WlUrEzoyYDM9.png)

This issue occurs when 

And clicking "Safe Mode" will have no effect.

This mod will fix this issue.

This mod does two things:
* Prevent the non-vanilla dimensions from being saved into `level.dat` file
* When loading a world, prevent the custom dimension data from being feeding into DFU


Note: If you are uninstalled a dimension datapack that does not define any of its own thing, then
 Minecraft will maybe successfully load the world but that datapack's dimension won't be removed.
 By installing this mod, the dimensions of uninstalled datapacks can be removed.


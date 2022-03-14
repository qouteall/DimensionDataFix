## Dimension Data Fix

In MC 1.18.2, sometimes, after uninstalling a dimension datapack or a dimension mod, Minecraft cannot open the world:

![2022-03-13_18.18.40.png](https://s2.loli.net/2022/03/13/JN7WlUrEzoyYDM9.png)

And clicking "Safe Mode" will have no effect.

> This issue occurs when that datapack or mod define its own WorldGen-related things (for example, noise settings, chunk generators). If the datapack does not define its own WorldGen thing, Minecraft can load it. But most dimension datapacks and dimension mods define their own things.

This mod will fix this issue.

This mod does two things:
* Prevent the non-vanilla dimensions from being saved into `level.dat` file
* When loading a world, prevent the custom dimension data from being fed into DFU and cause error

Note: This mod changes vanilla behavior, but in a good way. In vanilla (1.18.2), once you installed a dimension datapack, then the dimension will be stored in `level.dat`. Even if you remove the datapack, that dimension will still be loaded. You cannot remove the dimension without manually editing `level.dat`. (Minecraft had warned you that it's an unsupported experimental setting. This custom dimension functionality is indeed experimental.) By installing this mod, the dimensions of the uninstalled datapacks will be removed (but their world files won't be deleted).




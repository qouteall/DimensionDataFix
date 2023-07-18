## Dimension Data Fix

This mod fixes the issue that, when upgrading a world that has custom dimensions added by mods, the DFU will break the world saving, then the nether and end dimensions disappear.

**Note: it's always recommended backup the world before upgrading to a new MC version.**

With this mod, DFU will not swallow the nether and the end when upgrading if the world contains moded dimensions. If the world becomes broken before using this mod, this mod provides a command to recover the nether and the end. By using the command `/dimension_data_fix recover_default_nether_end`, then quit the world (if it's on a dedicated server, it needs to restart the server) then the nether and end dimensions will be added back.

Note: the nether and end dimensions recovered by the command will use the default vanilla world generation configuration. (If that world uses custom world type before, the recovered nether and end will use the vanilla default configuration. The world gen data in the broken world is already lost so this mod doesn't know. It's recommended to avoid having the world broken.)


Modrinth: https://modrinth.com/mod/dimension-data-fix 
CurseForge: https://legacy.curseforge.com/minecraft/mc-mods/dimension-data-fix
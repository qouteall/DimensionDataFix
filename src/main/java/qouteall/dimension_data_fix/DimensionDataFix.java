package qouteall.dimension_data_fix;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtIo;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import net.minecraft.world.level.storage.LevelStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import qouteall.dimension_data_fix.mixin.LevelStorageSessionAccessor;
import qouteall.dimension_data_fix.mixin.MinecraftServerAccessor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

public class DimensionDataFix implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("dimension_data_fix");
    
    public static boolean enableUpgradeFix = true;
    
    public static boolean addNetherEndOnServerClose = false;
    
    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register(
            (dispatcher, registryAccess, environment) -> registerCommands(dispatcher)
        );
        
        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            addNetherEndOnServerClose = false;
        });
        
        ServerLifecycleEvents.SERVER_STOPPED.register(server -> {
            if (addNetherEndOnServerClose) {
                addNetherEndOnServerClose = false;
                addDimensionToSaving(server, World.NETHER);
                addDimensionToSaving(server, World.END);
            }
        });
    }
    
    private static void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> builder = CommandManager
            .literal("dimension_data_fix")
            .requires(source -> source.hasPermissionLevel(2));
        
        builder.then(CommandManager.literal("recover_default_nether_end")
            .executes(context -> {
                MinecraftServer server = context.getSource().getPlayer().server;
                addNetherEndOnServerClose = true;
                context.getSource().sendMessage(Text.translatable("dim_data_fix.re_enter_to_see_change"));
                return 0;
            })
        );
        
        dispatcher.register(builder);
    }
    
    private static Path getLevelDatPath(MinecraftServer server) {
        LevelStorage.Session session = ((MinecraftServerAccessor) server).ddf_getSession();
        LevelStorage.LevelSave levelSave = ((LevelStorageSessionAccessor) session).ddf_getDirectory();
        Path levelDatPath = levelSave.getLevelDatPath();
        return levelDatPath;
    }
    
    /**
     * Got by reading nbt of normal minecraft worlds. Needs to update every MC version.
     * It does not call vanilla methods as that's more fragile and complex.
     * Nbt editor https://nbt.mcph.to/
     */
    private static NbtCompound getDefaultNetherNbt() {
        NbtCompound nbt = new NbtCompound();
        
        nbt.putString("type", "minecraft:the_nether");
        
        NbtCompound generator = new NbtCompound();
        generator.putString("type", "minecraft:noise");
        generator.putString("settings", "minecraft:nether");
        
        NbtCompound biomeSource = new NbtCompound();
        biomeSource.putString("type", "minecraft:multi_noise");
        biomeSource.putString("preset", "minecraft:nether");
        generator.put("biome_source", biomeSource);
        
        nbt.put("generator", generator);
        
        return nbt;
    }
    
    private static NbtCompound getDefaultEndNbt() {
        NbtCompound nbt = new NbtCompound();
        
        nbt.putString("type", "minecraft:the_end");
        
        NbtCompound generator = new NbtCompound();
        generator.putString("type", "minecraft:noise");
        generator.putString("settings", "minecraft:end");
        
        NbtCompound biomeSource = new NbtCompound();
        biomeSource.putString("type", "minecraft:the_end");
        generator.put("biome_source", biomeSource);
        
        nbt.put("generator", generator);
        
        return nbt;
    }
    
    private static boolean addDimensionToSaving(MinecraftServer server, RegistryKey<World> dimIdKey) {
        LOGGER.info("Adding default {} to level.dat", dimIdKey.getValue());
        
        Path levelDatPath = getLevelDatPath(server);
        try {
            Files.copy(levelDatPath, levelDatPath.resolveSibling(
                "level_dat_backup_" + UUID.randomUUID().toString() + ".dat"
            ));
            
            NbtCompound nbtCompound = NbtIo.readCompressed(levelDatPath.toFile());
            NbtCompound data = nbtCompound.getCompound("Data");
            NbtCompound worldGenSettings = data.getCompound("WorldGenSettings");
            NbtCompound dimensions = worldGenSettings.getCompound("dimensions");
            
            if (dimIdKey == World.NETHER) {
                if (dimensions.contains("minecraft:the_nether")) {
                    LOGGER.info("The nether already exists");
                    return false;
                }
                dimensions.put("minecraft:the_nether", getDefaultNetherNbt());
            }
            else if (dimIdKey == World.END) {
                if (dimensions.contains("minecraft:the_end")) {
                    LOGGER.info("The end already exists");
                    return false;
                }
                dimensions.put("minecraft:the_end", getDefaultEndNbt());
            }
            else {
                LOGGER.warn("Illegal arg {}", dimIdKey);
                return false;
            }
            
            LOGGER.info("updated {}", nbtCompound);
            
            NbtIo.writeCompressed(nbtCompound, levelDatPath.toFile());
            
            return true;
        }
        catch (Exception e) {
            LOGGER.error("Failed to add default dimension to level.dat", e);
            return false;
        }
    }
}
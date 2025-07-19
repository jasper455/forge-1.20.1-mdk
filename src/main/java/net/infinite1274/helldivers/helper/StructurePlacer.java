package net.infinite1274.helldivers.helper;

import net.infinite1274.helldivers.HelldiversMod;
import net.infinite1274.helldivers.worldgen.dimension.ModDimensions;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = HelldiversMod.MOD_ID)
public class StructurePlacer {

    private static boolean placed = false;

//    @SubscribeEvent
//    public static void onWorldTick(TickEvent.LevelTickEvent event) {
//        if (event.phase != TickEvent.Phase.END || placed) return;
//
//        ServerLevel level = (ServerLevel) event.level;
//        if (!level.dimension().equals(ModDimensions.SUPER_DESTROYER_LEVEL_KEY)) return;
//
//        placed = true;
//
//
//        ResourceLocation structureId = ResourceLocation.withDefaultNamespace("igloo");
//        StructureTemplate template = level.getStructureManager().getOrCreate(structureId);
//        Minecraft.getInstance().player.sendSystemMessage(Component.literal(String.valueOf(structureId)));
//
//        BlockPos placementPos = new BlockPos(0, 120, 0);
//        StructurePlaceSettings settings = new StructurePlaceSettings()
//                .setMirror(Mirror.NONE)
//                .setRotation(Rotation.NONE)
//                .setIgnoreEntities(false);
//        template.placeInWorld(
//            level,
//            placementPos,
//            placementPos,
//            settings,
//            level.getRandom(),
//            2 // update flags
//        );
//    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.LevelTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        // Make sure we’re only doing this once
        if (placed) return;

        // Make sure it's server side
        if (event.level.isClientSide) return;

        // Only for your dimension
        if (!event.level.dimension().equals(ModDimensions.SUPER_DESTROYER_LEVEL_KEY)) return;

        placed = true;
        event.level.getServer().getPlayerList().broadcastSystemMessage(Component.literal("StructurePlacer triggered"), false);
    }
}

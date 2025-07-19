package net.infinite1274.helldivers.datagen;

import net.infinite1274.helldivers.HelldiversMod;
import net.infinite1274.helldivers.worldgen.ModBiomeModifiers;
import net.infinite1274.helldivers.worldgen.ModConfiguredFeatures;
import net.infinite1274.helldivers.worldgen.ModPlacedFeatures;
import net.infinite1274.helldivers.worldgen.dimension.ModDimensions;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModDatapackEntries extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, ModPlacedFeatures::bootstrap)
            .add(ForgeRegistries.Keys.BIOME_MODIFIERS, ModBiomeModifiers::bootstrap)
            .add(Registries.DIMENSION_TYPE, ModDimensions::bootsrapType)
            .add(Registries.LEVEL_STEM, ModDimensions::bootstrapStem);

    public ModDatapackEntries(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(HelldiversMod.MOD_ID));
    }

    @Override
    public String getName() {
        return "Datapack Entries";
    }
}


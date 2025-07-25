package net.infinite1274.helldivers.sound;

import net.infinite1274.helldivers.HelldiversMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, HelldiversMod.MOD_ID);

    public static final RegistryObject<SoundEvent> STRATAGEM_INPUT = registerSoundEvent("stratagem_input");

    public static final RegistryObject<SoundEvent> FALLING_SHELL = registerSoundEvent("falling_shell");

    public static final RegistryObject<SoundEvent> EXPLOSION = registerSoundEvent("explosion");

    public static final RegistryObject<SoundEvent> STRATAGEM_ACTIVATE = registerSoundEvent("stratagem_activate");

    public static final RegistryObject<SoundEvent> FIRE_ORBITAL_STRIKE = registerSoundEvent("firing_orbital_strike");

    public static final RegistryObject<SoundEvent> HELLBOMB_ARMED = registerSoundEvent("hellbomb_armed");

    public static final RegistryObject<SoundEvent> HELLBOMB_EXPLOSION = registerSoundEvent("hellbomb_explosion");

    public static final RegistryObject<SoundEvent> CLEAR_THE_AREA = registerSoundEvent("clear_the_area");

    public static final RegistryObject<SoundEvent> STRATAGEM_FAIL_INPUT = registerSoundEvent("stratagem_fail_input");

    public static final RegistryObject<SoundEvent> STRATAGEM_MENU_OPEN = registerSoundEvent("stratagem_menu_open");

    public static final RegistryObject<SoundEvent> STRATAGEM_MENU_CLOSE = registerSoundEvent("stratagem_menu_close");

    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(HelldiversMod.MOD_ID, name);
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }


    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}

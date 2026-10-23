package net.minecraft.init;

import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class MobEffects {
    public static final Potion SPEED = effect("speed", false, 0x7CAFC6);
    public static final Potion SLOWNESS = effect("slowness", true, 0x5A6C81);
    public static final Potion HASTE = effect("haste", false, 0xD9A63C);
    public static final Potion MINING_FATIGUE = effect("mining_fatigue", true, 0x4A4217);
    public static final Potion STRENGTH = effect("strength", false, 0x932423);
    public static final Potion INSTANT_HEALTH = effect("instant_health", false, 0xF82423);
    public static final Potion INSTANT_DAMAGE = effect("instant_damage", true, 0x430A09);
    public static final Potion JUMP_BOOST = effect("jump_boost", false, 0x22FF4C);
    public static final Potion NAUSEA = effect("nausea", true, 0x551D4A);
    public static final Potion REGENERATION = effect("regeneration", false, 0xCD5CAB);
    public static final Potion RESISTANCE = effect("resistance", false, 0x9146F0);
    public static final Potion FIRE_RESISTANCE = effect("fire_resistance", false, 0xE49A3A);
    public static final Potion WATER_BREATHING = effect("water_breathing", false, 0x2E5299);
    public static final Potion INVISIBILITY = effect("invisibility", false, 0x7F8392);
    public static final Potion BLINDNESS = effect("blindness", true, 0x1F1F23);
    public static final Potion NIGHT_VISION = effect("night_vision", false, 0x1F1FA1);
    public static final Potion HUNGER = effect("hunger", true, 0x587653);
    public static final Potion WEAKNESS = effect("weakness", true, 0x484D48);
    public static final Potion POISON = effect("poison", true, 0x4E9331);
    public static final Potion WITHER = effect("wither", true, 0x352A27);
    public static final Potion HEALTH_BOOST = effect("health_boost", false, 0xF87D23);
    public static final Potion ABSORPTION = effect("absorption", false, 0x2552A5);
    public static final Potion SATURATION = effect("saturation", false, 0xF7E64A);
    public static final Potion GLOWING = effect("glowing", false, 0x94A061);
    public static final Potion LEVITATION = effect("levitation", true, 0xCEFFFF);
    public static final Potion LUCK = effect("luck", false, 0x59C23D);
    public static final Potion UNLUCK = effect("unluck", true, 0x8C7C2A);

    private static Potion effect(String name, boolean bad, int color) {
        return new Potion(bad, color).setPotionName(name);
    }
}

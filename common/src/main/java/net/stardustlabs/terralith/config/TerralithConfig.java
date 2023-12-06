package net.stardustlabs.terralith.config;

import java.nio.file.Path;

public class TerralithConfig extends ConfigFile
{
    public final Boolean terrablenderCompatibility;
    public final String cursedSkylands;

    public TerralithConfig(Path path, Boolean terrablenderCompatibilityDefault, String cursedSkylandsDefault)
    {
        super(path);

        Config terralithSettings = this.getSubConfig("terralith");
        this.terrablenderCompatibility = terralithSettings.addBool(
                """
                        This option determines whether Terralith should create a Terrablender region.
                        Enabling will allow for better biome rarity and compatibility with other mods.
                        Disabling will allow Terralith to function as it would a datapack.
                        Note: If compatibility is disabled, but BYG or BoP are installed, compatibility will be forced.
                        """,
                "terrablender_compatibility", terrablenderCompatibilityDefault
        );
        this.cursedSkylands = terralithSettings.add(
                """
                        This option determines whether Skylands will be cursed - ONLY takes effect when Terrablender compatibility is enabled!
                        The options are listed below. DO NOT use "some" or "very" in a real world, unless you know what you're doing!
                        It should be noted that if Terrablender compatibility is disabled, the cursed generation will not occur, no matter what.
                        \n None - Skylands generate as normally as they can
                        \n Some - Skylands can generate on top of each other, be very large, and sometimes "cancel" out ocean-based biomes such as Mirage Isles.
                        \n Very - Skylands can generate anywhere and everywhere. This includes on each other, above land, and *in* land!""",
                "cursed_skylands", cursedSkylandsDefault
        );
        this.addSubConfig("Terralith configuration", "terralith", terralithSettings);

        this.save();
    }
}
package net.stardustlabs.terralith.fabric.config.configs;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.stardustlabs.terralith.fabric.config.r.ConfigUtil;
import net.stardustlabs.terralith.fabric.config.r.JanksonOps;
import net.minecraft.Util;

import java.nio.file.Path;
import java.util.HashMap;

public record CommentedConfig(boolean terrablender) {

    public static final Path CONFIG_PATH = ConfigUtil.CONFIG_TERRALITH.resolve("terralith.json5");

    private static CommentedConfig INSTANCE = null;

    public static final CommentedConfig DEFAULT = new CommentedConfig(false);

    public static final Codec<CommentedConfig> CODEC = RecordCodecBuilder.create(builder ->
            builder.group(
                    Codec.BOOL.fieldOf("terrablender").orElse(false).forGetter(config -> config.terrablender)
            ).apply(builder, CommentedConfig::new)
    );

    public static CommentedConfig getConfig() {
        return getConfig(false, false);
    }

    public static CommentedConfig getConfig(boolean serialize, boolean recreate) {
        if (INSTANCE == null || serialize || recreate) {
            final Path path = CONFIG_PATH;
            if (!path.toFile().exists() || recreate) {
                createConfig(path);
            }
            INSTANCE = ConfigUtil.readConfig(path, CODEC, JanksonOps.INSTANCE);
        }
        return INSTANCE;
    }

    public static void setINSTANCE(CommentedConfig config){
        INSTANCE = config;
    }


    private static void createConfig(Path path) {
        HashMap<String, String> comments = Util.make(new HashMap<>(), map -> {
                    map.put("mode", """
                    Whether Terralith should create its own Terrablender region.
                    If set to true, Terrablender is required.
                    """);
                }
        );
        if(INSTANCE == null){
            INSTANCE = DEFAULT;
        }
        ConfigUtil.createConfig(path, CODEC, comments, JanksonOps.INSTANCE, INSTANCE);
    }
}
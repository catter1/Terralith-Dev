package net.stardustlabs.terralith;

import com.google.common.base.Suppliers;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public class Terralith {
    public static final String MOD_ID = "terralith";
    // We can use this if we don't want to use DeferredRegister
    // public static final Supplier<Registries> REGISTRIES = Suppliers.memoize(() -> Registries.get(MOD_ID));
    
    public static void init() {        
        // System.out.println(TerralithExpectPlatform.getConfigDirectory().toAbsolutePath().normalize().toString());
    }
}

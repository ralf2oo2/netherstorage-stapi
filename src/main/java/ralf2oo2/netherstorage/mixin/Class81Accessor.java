package ralf2oo2.netherstorage.mixin;

import net.minecraft.class_81;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.io.File;

@Mixin(class_81.class)
public interface Class81Accessor {
    @Accessor
    File getField_279();
}

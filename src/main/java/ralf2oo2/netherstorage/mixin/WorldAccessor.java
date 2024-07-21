package ralf2oo2.netherstorage.mixin;

import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(World.class)
public interface WorldAccessor {
    @Accessor
    DimensionData getDimensionData();
}

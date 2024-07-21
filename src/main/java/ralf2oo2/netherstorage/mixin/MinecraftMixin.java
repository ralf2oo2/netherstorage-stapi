package ralf2oo2.netherstorage.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ralf2oo2.netherstorage.StorageManager;

import java.util.HashMap;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Shadow public World world;

    @Inject(method = "method_2115", at = @At("TAIL"))
    private void netherstorage_startWorld(World string, String playerEntity, PlayerEntity par3, CallbackInfo ci){
        if(world != null && !world.isRemote){
            StorageManager.netherStorage = new HashMap<>();
            StorageManager.netherLabels = new HashMap<>();
            StorageManager.loadStorage(world);
        }
    }
}

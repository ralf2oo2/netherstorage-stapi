package ralf2oo2.netherstorage.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.FabricLoader;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.world.StationFlatteningWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ralf2oo2.netherstorage.NetherStorageClient;
import ralf2oo2.netherstorage.StorageManager;
import ralf2oo2.netherstorage.blockentity.NetherChestBlockEntity;

import java.util.HashMap;

@Mixin(StationFlatteningWorld.class)
public class StationFlatteningWorldMixin{

    @Inject(method = "setBlockState(IIILnet/modificationstation/stationapi/api/block/BlockState;)Lnet/modificationstation/stationapi/api/block/BlockState;", at = @At("TAIL"), remap = false)
    private void netherstorage_startWorld(int x, int y, int z, BlockState blockState, CallbackInfoReturnable<BlockState> cir){
        if(FabricLoader.INSTANCE.getEnvironmentType() == EnvType.CLIENT){
            World world = NetherStorageClient.getMc().player.world;
            if(!world.isRemote) return;
            BlockEntity blockEntity = world.getBlockEntity(x, y, z);
            if(blockEntity instanceof NetherChestBlockEntity){
                blockEntity.cancelRemoval();
                world.method_157(x, y, z, blockEntity);
                world.method_246(x, y, z);
            }
        }
    }
    @Inject(method = "setBlockStateWithNotify", at = @At("TAIL"), remap = false)
    private void netherstorage_startWorld(int x, int y, int z, BlockState blockState, int meta, CallbackInfoReturnable<BlockState> cir){
        if(FabricLoader.INSTANCE.getEnvironmentType() == EnvType.CLIENT){
            World world = NetherStorageClient.getMc().player.world;
            if(!world.isRemote) return;
            BlockEntity blockEntity = world.getBlockEntity(x, y, z);
            if(blockEntity instanceof NetherChestBlockEntity){
                blockEntity.cancelRemoval();
                world.method_157(x, y, z, blockEntity);
                world.method_246(x, y, z);
            }
        }
    }
}

package ralf2oo2.netherstorage.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.FabricLoader;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;
import ralf2oo2.netherstorage.NetherStorage;
import ralf2oo2.netherstorage.NetherStorageClient;
import ralf2oo2.netherstorage.state.NetherChestState;

public class NetherBagItem extends TemplateItem{
    public NetherBagItem(Identifier identifier) {
        super(identifier);
        setMaxCount(1);
    }

    @Override
    public ItemStack use(ItemStack stack, World world, PlayerEntity user) {
        if(FabricLoader.INSTANCE.getEnvironmentType() == EnvType.CLIENT && world.isRemote) return stack;
        NbtCompound nbtCompound = stack.getStationNbt();
        String channel = nbtCompound.getString("channel");
        if(channel != null && !channel.equals("")){
            NetherChestState state = getState(NetherStorage.getStateId() + channel, world);
            if(state != null){
                user.method_486(state);
            }
        }
        return stack;
    }

    private NetherChestState getState(String id, World world){
        NetherChestState state = (NetherChestState) world.persistentStateManager.getOrCreate(NetherChestState.class, id);
        return state;
    }
}

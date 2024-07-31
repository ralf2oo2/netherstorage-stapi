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
import ralf2oo2.netherstorage.inventory.NetherBagInventory;
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
            NetherChestState state = getState(NetherStorage.getStateId() + channel, channel, world);
            if(state != null){
                user.method_486(new NetherBagInventory(stack, user, state));
            }
        }
        return stack;
    }

    private NetherChestState getState(String id, String channel, World world){
        NetherChestState state = (NetherChestState) world.persistentStateManager.getOrCreate(NetherChestState.class, id);
        if(state == null){
            state = new NetherChestState(id);
            state.channel = channel;
            world.persistentStateManager.set(id, state);
            state.markDirty();
        }
        return state;
    }

    @Override
    public String getTranslationKey(ItemStack stack) {
        if(stack == null) return getTranslationKey();
        NbtCompound nbtCompound = stack.getStationNbt();
        String label = nbtCompound.getString("label");
        if(label != null && !label.equals("") && !label.equals("Nether Chest")){
            return label;
        }
        return getTranslationKey();
    }
}

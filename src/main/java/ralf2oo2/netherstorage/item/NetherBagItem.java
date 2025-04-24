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
import ralf2oo2.netherstorage.block.NetherChestBlock;
import ralf2oo2.netherstorage.blockentity.NetherChestBlockEntity;
import ralf2oo2.netherstorage.inventory.NetherBagInventory;
import ralf2oo2.netherstorage.registry.BlockRegistry;
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
                user.openChestScreen(new NetherBagInventory(stack, user, state));
            }
        }
        return stack;
    }

    @Override
    public boolean useOnBlock(ItemStack stack, PlayerEntity user, World world, int x, int y, int z, int side) {
        if(world.getBlockId(x, y, z) == BlockRegistry.netherChest.id && user.isSneaking()){
            NetherChestBlockEntity blockEntity = (NetherChestBlockEntity) world.getBlockEntity(x, y, z);
            NbtCompound nbtCompound = stack.getStationNbt();
            nbtCompound.putString("channel", blockEntity.getChannel());
            nbtCompound.putString("label", blockEntity.getLabel());
            nbtCompound.putString("color1", blockEntity.channelColors[0]);
            nbtCompound.putString("color2", blockEntity.channelColors[1]);
            nbtCompound.putString("color3", blockEntity.channelColors[2]);
            nbtCompound.putString("playerName", blockEntity.playerName);
            if(world.getBlockState(x, y, z).get(NetherChestBlock.PROTECTED).booleanValue()){
                nbtCompound.putString("playerName", blockEntity.playerName);
            }
            else {
                nbtCompound.putString("playerName", "");
            }
            return true;
        }
        return false;
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

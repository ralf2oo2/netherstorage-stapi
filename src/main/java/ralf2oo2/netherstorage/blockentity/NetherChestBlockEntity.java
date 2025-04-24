package ralf2oo2.netherstorage.blockentity;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.FabricLoader;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.PersistentState;
import net.modificationstation.stationapi.api.network.packet.PacketHelper;
import ralf2oo2.netherstorage.NetherStorage;
import ralf2oo2.netherstorage.NetherStorageClient;
import ralf2oo2.netherstorage.block.NetherChestBlock;
import ralf2oo2.netherstorage.packet.clientbound.SendBlockEntityDataPacket;
import ralf2oo2.netherstorage.packet.serverbound.RequestBlockEntityDataPacket;
import ralf2oo2.netherstorage.state.NetherChestState;

import java.util.ArrayList;
import java.util.List;

public class NetherChestBlockEntity extends BlockEntity implements Inventory{
    public String[] channelColors;
    public String playerName = "";

    public NetherChestBlockEntity(){
        channelColors = new String[3];
        for(int i = 0; i < channelColors.length; i++){
            channelColors[i] = "white";
        }
    }

    public String getChannel(){
        String key = !playerName.equals("") ? playerName + "&" : "";
        key += channelColors[0] + "&" + channelColors[1] + "&" + channelColors[2];
        return key;
    }

    private NetherChestState getOrCreateState(String id){;
        NetherChestState state = (NetherChestState) world.persistentStateManager.getOrCreate(NetherChestState.class, id);
        if(state == null){
            state = new NetherChestState(id);
            state.channel = getChannel();
            world.persistentStateManager.set(id, state);
            state.markDirty();
        }
        return state;
    }

    public NetherChestState getState(){
        return getOrCreateState(NetherStorage.getStateId() + getChannel());
    }

    public void setColor(int colorIndex, String color){
        channelColors[colorIndex] = color;
        NetherChestState state = (NetherChestState) world.persistentStateManager.getOrCreate(NetherChestState.class, getChannel());
        if(state != null){
            state.channel = getChannel();
            state.markDirty();
        }
        markDirty();
    }
    boolean syncedBlockEntity = false;
    @Override
    public void tick() {
        if(FabricLoader.INSTANCE.getEnvironmentType() == EnvType.CLIENT && world.isRemote){
            if(!syncedBlockEntity){
                syncedBlockEntity = true;
                PacketHelper.send(new RequestBlockEntityDataPacket(x, y ,z));
                if(NetherStorageClient.storedColors.containsKey(new BlockPos(x, y, z))){
                    NetherStorageClient.storedColors.remove(new BlockPos(x, y, z));
                }
            }
        }
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putString("color1", channelColors[0]);
        nbt.putString("color2", channelColors[1]);
        nbt.putString("color3", channelColors[2]);
        nbt.putString("player_name", playerName);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        channelColors[0] = nbt.getString("color1");
        channelColors[1] = nbt.getString("color2");
        channelColors[2] = nbt.getString("color3");
        playerName = nbt.getString("player_name");
    }

    public String getLabel(){
        NetherChestState state = getOrCreateState(NetherStorage.getStateId() + getChannel());
        if(state == null) return "Nether Chest";
        return state.label;
    }

    public void setLabel(String label){
        NetherChestState state = getOrCreateState(NetherStorage.getStateId() + getChannel());
        if(state == null) return;
        state.label = label;
        state.markDirty();
    }

    @Override
    public void markDirty() {
        super.markDirty();
        if(FabricLoader.INSTANCE.getEnvironmentType() == EnvType.SERVER){
            PlayerEntity[] players = getPlayerEntitiesInRange(20);
            if(players != null){
                for(int i = 0; i < players.length; i++){
                    PacketHelper.sendTo(players[i], new SendBlockEntityDataPacket(x, y, z, playerName, channelColors[0], channelColors[1], channelColors[2]));
                }
            }
        }
        getState().markDirty();
    }

    private PlayerEntity[] getPlayerEntitiesInRange(double range){
        List players = new ArrayList<PlayerEntity>();
        for(int var12 = 0; var12 < world.players.size(); ++var12) {
            PlayerEntity player = (PlayerEntity)world.players.get(var12);
            double distance = player.getSquaredDistance(x, y, z);

            if (distance < range && player.dimensionId == world.dimension.id) {
                players.add(player);
            }
        }
        PlayerEntity[] playerEntityArray = new PlayerEntity[players.size()];
        playerEntityArray = (PlayerEntity[]) players.toArray(playerEntityArray);
        return playerEntityArray;
    }

    @Override
    public int size() {
        return getState().size();
    }

    @Override
    public ItemStack getStack(int slot) {
        return getState().getStack(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        return getState().removeStack(slot, amount);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        getState().setStack(slot, stack);
    }

    @Override
    public String getName() {
        return getState().getName();
    }

    @Override
    public int getMaxCountPerStack() {
        return getState().getMaxCountPerStack();
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        if (!(this.world.getBlockEntity(this.x, this.y, this.z) instanceof NetherChestBlockEntity)) {
            return false;
        } else {
            return !(player.getSquaredDistance((double)this.x + 0.5, (double)this.y + 0.5, (double)this.z + 0.5) > 64.0);
        }
    }
}

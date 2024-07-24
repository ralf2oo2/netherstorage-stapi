package ralf2oo2.netherstorage.blockentity;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.PersistentState;
import ralf2oo2.netherstorage.NetherStorage;
import ralf2oo2.netherstorage.state.NetherChestState;

public class NetherChestBlockEntity extends BlockEntity {
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
        System.out.println(key);
        return key;
    }

    private NetherChestState getOrCreateState(String id){
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
}

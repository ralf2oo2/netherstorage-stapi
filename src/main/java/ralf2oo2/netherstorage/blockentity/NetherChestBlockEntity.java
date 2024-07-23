package ralf2oo2.netherstorage.blockentity;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.PersistentState;
import ralf2oo2.netherstorage.NetherStorage;
import ralf2oo2.netherstorage.StorageManager;
import ralf2oo2.netherstorage.state.NetherChestState;

public class NetherChestBlockEntity extends BlockEntity implements Inventory {
    // TODO: replace color strings with array to simplify code
    public String color1 = "white";
    public String color2 = "white";
    public String color3 = "white";
    public String playerName = "";

    public NetherChestBlockEntity(){
    }

    public String getKey(){
        String key = playerName != "" ? playerName + "&" : "";
        key += color1 + "&" + color2 + "&" + color3;
        System.out.println(key);
        return key;
    }
    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        color1 = nbt.getString("color1");
        color2 = nbt.getString("color2");
        color3 = nbt.getString("color3");
        playerName = nbt.getString("player_name");
    }

    private NetherChestState getOrCreateState(String id){
        NetherChestState state = (NetherChestState) world.persistentStateManager.getOrCreate(NetherChestState.class, id);
        if(state == null){
            state = new NetherChestState(id);
            world.persistentStateManager.set(id, state);
        }
        return state;
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putString("color1", color1);
        nbt.putString("color2", color2);
        nbt.putString("color3", color3);
        nbt.putString("player_name", playerName);
    }

    public ItemStack[] getInventory(){
        NetherChestState state = getOrCreateState(NetherStorage.getStateId() + getKey());
        if(state == null){
            return null;
        }
        return state.inventory;
    }

    @Override
    public int size() {
        if(getInventory() != null){
            return 27;
        }
        else {
            return 0;
        }
    }

    @Override
    public ItemStack getStack(int slot) {
        ItemStack stack = getInventory()[slot];
        // TODO: check if this is nescesary
        /*if(stack != null)
        {
            slotpos = i1;
            count = stack.stackSize;
        }*/
        return stack;
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        if(getInventory()[slot] != null) {
            ItemStack itemStack3;
            if(this.getInventory()[slot].count <= amount) {
                itemStack3 = this.getInventory()[slot];
                this.getInventory()[slot] = null;
                this.markDirty();
                return itemStack3;
            } else {
                itemStack3 = this.getInventory()[slot].split(amount);
                if(this.getInventory()[slot].count == 0) {
                    this.getInventory()[slot] = null;
                }

                this.markDirty();
                return itemStack3;
            }
        } else {
            return null;
        }
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        getInventory()[slot] = stack;
        markDirty();
    }

    @Override
    public String getName() {
        NetherChestState state = getOrCreateState(NetherStorage.getStateId() + getKey());
        if(state == null) return "Nether Chest";
        return state.label;
    }

    public void setLabel(String label){
        NetherChestState state = getOrCreateState(NetherStorage.getStateId() + getKey());
        if(state == null) return;
        state.label = label;
        state.markDirty();
    }

    @Override
    public int getMaxCountPerStack() {
        return 64;
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        if(getInventory() == null && getInventory().length == 1){
            return false;
        }
        else {
            return true;
        }
    }
    @Override
    public void markDirty() {
        super.markDirty();
        PersistentState state = getOrCreateState(NetherStorage.getStateId() + getKey());
        if(state != null){
            state.markDirty();
        }
    }
}

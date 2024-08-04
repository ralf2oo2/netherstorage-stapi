package ralf2oo2.netherstorage.state;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import ralf2oo2.netherstorage.NetherStorage;
import ralf2oo2.netherstorage.item.NetherBagItem;
import ralf2oo2.netherstorage.registry.ItemRegistry;

public class NetherChestState extends PersistentState implements Inventory {
    public String channel = "";
    public String label = "Nether Chest";
    public ItemStack[] inventory;
    public NetherChestState(String id) {
        super(id);
        inventory = new ItemStack[27];
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        this.channel = nbt.getString("channel");
        this.label = nbt.getString("label");

        NbtList nbtList = nbt.getList("Items");
        this.inventory = new ItemStack[this.inventory.length];

        for(int var3 = 0; var3 < nbtList.size(); ++var3) {
            NbtCompound var4 = (NbtCompound)nbtList.get(var3);
            int var5 = var4.getByte("Slot") & 255;
            if (var5 >= 0 && var5 < this.inventory.length) {
                this.inventory[var5] = new ItemStack(var4);
            }
        }
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        nbt.putString("channel", channel);
        nbt.putString("label", label);

        NbtList nbtList = new NbtList();

        for(int var3 = 0; var3 < this.inventory.length; ++var3) {
            if (this.inventory[var3] != null) {
                NbtCompound var4 = new NbtCompound();
                var4.putByte("Slot", (byte)var3);
                this.inventory[var3].writeNbt(var4);
                nbtList.add(var4);
            }
        }

        nbt.put("Items", nbtList);
    }

    public ItemStack[] getInventory(){
        return inventory;
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
        if(stack != null && stack.getItem() instanceof NetherBagItem){
            stack.getStationNbt().putString("channel", "");
            stack.getStationNbt().putString("label", "");
            stack.getStationNbt().putString("color1", "");
            stack.getStationNbt().putString("color2", "");
            stack.getStationNbt().putString("color3", "");
            stack.getStationNbt().putString("playerName", "");

        }
        getInventory()[slot] = stack;
        markDirty();
    }

    @Override
    public String getName() {
        return label;
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
}

package ralf2oo2.netherstorage.state;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.world.PersistentState;

public class NetherChestState extends PersistentState {
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
}

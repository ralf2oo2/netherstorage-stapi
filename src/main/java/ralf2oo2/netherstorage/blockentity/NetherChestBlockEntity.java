package ralf2oo2.netherstorage.blockentity;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.modificationstation.stationapi.api.registry.ItemRegistry;
import ralf2oo2.netherstorage.StorageManager;
import ralf2oo2.netherstorage.inventory.ChannelInventory;

public class NetherChestBlockEntity extends BlockEntity implements Inventory {
    public ItemStack[] channelSlots = new ItemStack[3];
    public ChannelInventory channelInventory;

    public String ownerUUID = "";

    public NetherChestBlockEntity(){
        channelInventory = new ChannelInventory(this);
    }

    public String getKey(){
        String key = ownerUUID != "" ? ownerUUID + "&" : "";
        for(int i = 0; i < channelSlots.length; i++){
            if(channelSlots[i] != null){
                key += ItemRegistry.INSTANCE.getId(channelSlots[i].getItem()).toString();
            }
            else{
                key += "empty";
            }
            if(i != channelSlots.length - 1){
                key += "&";
            }
        }
        System.out.println(key);
        return key;
    }
    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        NbtList nbtList = nbt.getList("ChannelItems");
        this.channelSlots = new ItemStack[channelSlots.length];

        for(int i3 = 0; i3 < nbtList.size(); ++i3) {
            NbtCompound itemNbt = (NbtCompound)nbtList.get(i3);
            int i5 = itemNbt.getByte("Slot") & 255;
            if(i5 >= 0 && i5 < this.channelSlots.length) {
                this.channelSlots[i5] = new ItemStack(itemNbt);
            }
        }
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        NbtList nbtList = new NbtList();

        for(int i3 = 0; i3 < this.channelSlots.length; ++i3) {
            if(this.channelSlots[i3] != null) {
                NbtCompound itemNbt = new NbtCompound();
                itemNbt.putByte("Slot", (byte)i3);
                this.channelSlots[i3].writeNbt(itemNbt);
                nbtList.add(itemNbt);
            }
        }

        nbt.put("ChannelItems", nbtList);
    }

    public ItemStack[] getInventory(){
        return StorageManager.getNetherInventory(getKey());
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
        if(StorageManager.netherLabels.containsKey(getKey())){
            return StorageManager.netherLabels.get("");
        }
        return "Nether Chest";
    }

    @Override
    public int getMaxCountPerStack() {
        return 64;
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        if(getInventory().length == 1){
            return false;
        }
        else {
            return true;
        }
    }

    @Override
    public void markDirty() {
        StorageManager.saveStorage(world);
        super.markDirty();
    }
}

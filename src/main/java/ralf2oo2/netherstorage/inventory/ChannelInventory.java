package ralf2oo2.netherstorage.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import ralf2oo2.netherstorage.blockentity.NetherChestBlockEntity;

public class ChannelInventory implements Inventory {
    public NetherChestBlockEntity blockEntity;

    public ChannelInventory(NetherChestBlockEntity blockEntity){
        this.blockEntity = blockEntity;
    }
    @Override
    public int size() {
        return 3;
    }

    @Override
    public ItemStack getStack(int slot) {
        return this.blockEntity.channelSlots[slot];
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        if(this.blockEntity.channelSlots[slot] != null) {
            ItemStack stack;
            if(this.blockEntity.channelSlots[slot].count <= amount) {
                stack = this.blockEntity.channelSlots[slot];
                this.blockEntity.channelSlots[slot] = null;
                this.markDirty();
                return stack;
            } else {
                stack = this.blockEntity.channelSlots[slot].split(amount);
                if(this.blockEntity.channelSlots[slot].count == 0) {
                    this.blockEntity.channelSlots[slot] = null;
                }

                this.markDirty();
                return stack;
            }
        } else {
            return null;
        }
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        this.blockEntity.channelSlots[slot] = stack;
        if(stack != null && stack.count > this.getMaxCountPerStack()) {
            stack.count = this.getMaxCountPerStack();
        }

        this.markDirty();
    }

    @Override
    public String getName() {
        return "Filter";
    }

    @Override
    public int getMaxCountPerStack() {
        return 1;
    }

    @Override
    public void markDirty() {
        blockEntity.markDirty();
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return true;
    }
}

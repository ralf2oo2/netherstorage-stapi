package ralf2oo2.netherstorage.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import ralf2oo2.netherstorage.state.NetherChestState;

public class NetherBagInventory implements Inventory {
    private String channel;
    private PlayerEntity player;
    private NetherChestState state;

    public NetherBagInventory(ItemStack itemStack, PlayerEntity player, NetherChestState state){
        this.channel = itemStack.getStationNbt().getString("channel");
        this.player = player;
        this.state = state;
    }

    @Override
    public int size() {
        return state.size();
    }

    @Override
    public ItemStack getStack(int slot) {
        return state.getStack(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        return state.removeStack(slot, amount);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        state.setStack(slot, stack);
    }

    @Override
    public String getName() {
        return state.getName();
    }

    @Override
    public int getMaxCountPerStack() {
        return state.getMaxCountPerStack();
    }

    @Override
    public void markDirty() {
        state.markDirty();
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        if (player.getHand() == null || player.getHand().getStationNbt().getString("channel") == null || !player.getHand().getStationNbt().getString("channel").equals(channel)) {
            return false;
        } else {
            return true;
        }
    }
}

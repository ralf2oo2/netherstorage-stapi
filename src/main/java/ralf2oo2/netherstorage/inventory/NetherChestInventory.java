package ralf2oo2.netherstorage.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import ralf2oo2.netherstorage.blockentity.NetherChestBlockEntity;
import ralf2oo2.netherstorage.state.NetherChestState;

public class NetherChestInventory implements Inventory {
    private NetherChestBlockEntity blockEntity;
    private NetherChestState state;
    private World world;
    public NetherChestInventory(NetherChestBlockEntity blockEntity, NetherChestState state, World world){
        this.blockEntity = blockEntity;
        this.state = state;
        this.world = world;
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
        if (!(this.world.getBlockEntity(this.blockEntity.x, this.blockEntity.y, this.blockEntity.z) instanceof NetherChestBlockEntity)) {
            return false;
        } else {
            return !(player.method_1347((double)this.blockEntity.x + 0.5, (double)this.blockEntity.y + 0.5, (double)this.blockEntity.z + 0.5) > 64.0);
        }
    }
}

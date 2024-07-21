package ralf2oo2.netherstorage.client.gui;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import ralf2oo2.netherstorage.blockentity.NetherChestBlockEntity;
import ralf2oo2.netherstorage.inventory.ChannelInventory;

public class ChannelScreenHandler extends ScreenHandler {

    NetherChestBlockEntity blockEntity;
    public ChannelScreenHandler(Inventory inventory, NetherChestBlockEntity blockEntity) {
        this.blockEntity = blockEntity;
        ChannelInventory filterInventory = blockEntity.channelInventory;

        int i3;
        int i4;
        for(i4 = 0; i4 < 3; ++i4) {
            this.addSlot(new Slot(filterInventory, i4, 62 + i4 * 18, 17 + 1 * 18));
        }

        for(i3 = 0; i3 < 3; ++i3) {
            for(i4 = 0; i4 < 9; ++i4) {
                this.addSlot(new Slot(inventory, i4 + i3 * 9 + 9, 8 + i4 * 18, 84 + i3 * 18));
            }
        }

        for(i3 = 0; i3 < 9; ++i3) {
            this.addSlot(new Slot(inventory, i3, 8 + i3 * 18, 142));
        }
    }
    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }
}

package ralf2oo2.netherstorage.registry;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.event.block.entity.BlockEntityRegisterEvent;
import ralf2oo2.netherstorage.blockentity.NetherChestBlockEntity;

public class BlockEntityRegistry {
    @EventListener
    public static void registerBlockEntities(BlockEntityRegisterEvent event) {
        event.register(NetherChestBlockEntity.class, "nether_chest");
    }
}

package ralf2oo2.netherstorage.registry;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.client.event.block.entity.BlockEntityRendererRegisterEvent;
import net.modificationstation.stationapi.api.event.block.entity.BlockEntityRegisterEvent;
import ralf2oo2.netherstorage.blockentity.NetherChestBlockEntity;
import ralf2oo2.netherstorage.client.render.NetherChestBlockEntityRenderer;

public class BlockEntityRendererRegistry {
    @EventListener
    private static void registerBlockEntities(BlockEntityRendererRegisterEvent event) {
        event.renderers.put(NetherChestBlockEntity.class, new NetherChestBlockEntityRenderer());
    }
}

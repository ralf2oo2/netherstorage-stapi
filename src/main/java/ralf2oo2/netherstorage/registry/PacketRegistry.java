package ralf2oo2.netherstorage.registry;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.client.event.block.entity.BlockEntityRendererRegisterEvent;
import net.modificationstation.stationapi.api.event.network.packet.PacketRegisterEvent;
import ralf2oo2.netherstorage.blockentity.NetherChestBlockEntity;
import ralf2oo2.netherstorage.client.render.NetherChestBlockEntityRenderer;
import ralf2oo2.netherstorage.packet.serverbound.SetLabelPacket;

public class PacketRegistry{
    @EventListener
    private static void registerPackets(PacketRegisterEvent event) {
        SetLabelPacket.register();
    }
}

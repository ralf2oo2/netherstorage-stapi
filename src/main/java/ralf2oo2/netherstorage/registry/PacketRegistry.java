package ralf2oo2.netherstorage.registry;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.event.network.packet.PacketRegisterEvent;
import ralf2oo2.netherstorage.packet.clientbound.SendBlockEntityDataPacket;
import ralf2oo2.netherstorage.packet.clientbound.ShowLabelScreenPacket;
import ralf2oo2.netherstorage.packet.serverbound.*;

public class PacketRegistry{
    @EventListener
    private static void registerPackets(PacketRegisterEvent event) {
        SetLabelPacket.register();
        ShowLabelScreenPacket.register();
        SetChannelValuePacket.register();
        RequestBlockEntityDataPacket.register();
        SendBlockEntityDataPacket.register();
        SetProtectedStatePacket.register();
        ShowChestScreenPacket.register();
    }
}

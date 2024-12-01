package ralf2oo2.netherstorage.packet.serverbound;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.NetworkHandler;
import net.minecraft.network.packet.Packet;
import net.minecraft.server.MinecraftServer;
import net.modificationstation.stationapi.api.network.packet.ManagedPacket;
import net.modificationstation.stationapi.api.network.packet.PacketType;
import net.modificationstation.stationapi.api.registry.PacketTypeRegistry;
import net.modificationstation.stationapi.api.registry.Registry;
import net.modificationstation.stationapi.api.util.Identifier;
import org.jetbrains.annotations.NotNull;
import ralf2oo2.netherstorage.NetherStorage;
import ralf2oo2.netherstorage.state.NetherChestState;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class SetLabelPacket extends Packet implements ManagedPacket<SetLabelPacket> {
    private String channel;
    private String labelContent;
    public static final PacketType<SetLabelPacket> TYPE = PacketType.builder(false, true, SetLabelPacket::new).build();
    public static final Identifier ID = Identifier.of(NetherStorage.NAMESPACE, "set_label");

    public SetLabelPacket(String channel, String labelContent){
        this.channel = channel;
        this.labelContent = labelContent;
    }
    public SetLabelPacket(){
    }

    @Override
    public void read(DataInputStream stream) {

        channel = readString(stream, 200);
        labelContent = readString(stream, 27);
    }

    @Override
    public void write(DataOutputStream stream) {
        writeString(channel, stream);
        writeString(labelContent, stream);
    }

    @Override
    public void apply(NetworkHandler networkHandler) {
        MinecraftServer server = ((MinecraftServer) FabricLoader.getInstance().getGameInstance());
        for(int i = 0; i < server.field_2841.length; i++){
            NetherChestState state = (NetherChestState) server.field_2841[i].getOrCreateState(NetherChestState.class, NetherStorage.getStateId() + channel);
            if(state != null){
                state.label = labelContent;
                state.markDirty();
            }
        }
    }

    @Override
    public int size() {
        return labelContent.length() + channel.length();
    }

    public static void register(){
        Registry.register(PacketTypeRegistry.INSTANCE, ID, TYPE);
    }

    @Override
    public @NotNull PacketType<SetLabelPacket> getType() {
        return TYPE;
    }
}

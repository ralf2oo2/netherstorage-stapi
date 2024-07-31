package ralf2oo2.netherstorage.packet.serverbound;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.NetworkHandler;
import net.minecraft.network.packet.Packet;
import net.minecraft.server.MinecraftServer;
import net.modificationstation.stationapi.api.network.packet.IdentifiablePacket;
import net.modificationstation.stationapi.api.network.packet.PacketHelper;
import net.modificationstation.stationapi.api.util.Identifier;
import ralf2oo2.netherstorage.NetherStorage;
import ralf2oo2.netherstorage.state.NetherChestState;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class SetLabelPacket extends Packet implements IdentifiablePacket {
    private String channel;
    private String labelContent;
    public static final Identifier ID = Identifier.of(NetherStorage.NAMESPACE, "set_label");

    public SetLabelPacket(String channel, String labelContent){
        this.channel = channel;
        this.labelContent = labelContent;
    }
    public SetLabelPacket(){
    }
    @Override
    public Identifier getId() {
        return ID;
    }

    @Override
    public void read(DataInputStream stream) {

        channel = readString(stream, 200);
        labelContent = readString(stream, 27);
        System.out.println(channel);
        System.out.println(labelContent);
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
        IdentifiablePacket.register(ID, false, true, SetLabelPacket::new);
    }

}

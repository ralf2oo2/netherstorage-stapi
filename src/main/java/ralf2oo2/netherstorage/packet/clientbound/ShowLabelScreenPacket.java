package ralf2oo2.netherstorage.packet.clientbound;

import net.minecraft.network.NetworkHandler;
import net.minecraft.network.packet.Packet;
import net.modificationstation.stationapi.api.network.packet.IdentifiablePacket;
import net.modificationstation.stationapi.api.util.Identifier;
import ralf2oo2.netherstorage.NetherStorage;
import ralf2oo2.netherstorage.NetherStorageClient;
import ralf2oo2.netherstorage.packet.serverbound.SetLabelPacket;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class ShowLabelScreenPacket extends Packet implements IdentifiablePacket {

    private String channel;
    private String label;
    public static final Identifier ID = Identifier.of(NetherStorage.NAMESPACE, "show_label_screen");
    public ShowLabelScreenPacket(){}
    public ShowLabelScreenPacket(String channel, String label){
        this.channel = channel;
        this.label = label;
    }
    @Override
    public void read(DataInputStream stream) {
        channel = readString(stream, 200);
        label = readString(stream, 27);
    }

    @Override
    public void write(DataOutputStream stream) {
        writeString(channel, stream);
        writeString(label, stream);
    }

    @Override
    public void apply(NetworkHandler networkHandler) {
        NetherStorageClient.showLabelScreen(channel, label);
    }

    @Override
    public int size() {
        return channel.length() + label.length();
    }

    @Override
    public Identifier getId() {
        return ID;
    }

    public static void register(){
        IdentifiablePacket.register(ID, true, false, ShowLabelScreenPacket::new);
    }
}

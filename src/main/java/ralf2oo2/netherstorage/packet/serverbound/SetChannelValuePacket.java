package ralf2oo2.netherstorage.packet.serverbound;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.NetworkHandler;
import net.minecraft.network.packet.Packet;
import net.modificationstation.stationapi.api.entity.player.PlayerHelper;
import net.modificationstation.stationapi.api.network.packet.IdentifiablePacket;
import net.modificationstation.stationapi.api.util.Identifier;
import ralf2oo2.netherstorage.NetherStorage;
import ralf2oo2.netherstorage.blockentity.NetherChestBlockEntity;
import ralf2oo2.netherstorage.state.NetherChestState;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class SetChannelValuePacket extends Packet implements IdentifiablePacket {
    private String value;
    private int index;
    private int x;
    private int y;
    private int z;
    public static final Identifier ID = Identifier.of(NetherStorage.NAMESPACE, "set_channel_value");
    public SetChannelValuePacket(){}
    public SetChannelValuePacket(String value, int index, int x, int y, int z){
        this.value = value;
        this.index = index;
        this.x = x;
        this.y = y;
        this.z = z;
    }
    @Override
    public void read(DataInputStream stream) {
        this.value = readString(stream, 100);
        try {
            this.index = stream.readInt();
            this.x = stream.readInt();
            this.y = stream.readInt();
            this.z = stream.readInt();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void write(DataOutputStream stream) {
        writeString(value, stream);
        try {
            stream.writeInt(index);
            stream.writeInt(x);
            stream.writeInt(y);
            stream.writeInt(z);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void apply(NetworkHandler networkHandler) {
        PlayerEntity player = PlayerHelper.getPlayerFromPacketHandler(networkHandler);
        if(player == null) return;
        NetherChestBlockEntity blockEntity = (NetherChestBlockEntity) player.world.getBlockEntity(x, y, z);
        switch (index){
            case 0:
                blockEntity.playerName = value;
                break;
            case 1:
            case 2:
            case 3:
                blockEntity.channelColors[index - 1] = value;
                break;
        }
        blockEntity.markDirty();
    }

    @Override
    public int size() {
        return value.length() + (4 * 4);
    }

    @Override
    public Identifier getId() {
        return ID;
    }

    public static void register(){
        IdentifiablePacket.register(ID, false, true, SetChannelValuePacket::new);
    }
}

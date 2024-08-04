package ralf2oo2.netherstorage.packet.serverbound;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.NetworkHandler;
import net.minecraft.network.packet.Packet;
import net.modificationstation.stationapi.api.entity.player.PlayerHelper;
import net.modificationstation.stationapi.api.network.packet.IdentifiablePacket;
import net.modificationstation.stationapi.api.network.packet.PacketHelper;
import net.modificationstation.stationapi.api.util.Identifier;
import ralf2oo2.netherstorage.NetherStorage;
import ralf2oo2.netherstorage.blockentity.NetherChestBlockEntity;
import ralf2oo2.netherstorage.packet.clientbound.SendBlockEntityDataPacket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class RequestBlockEntityDataPacket extends Packet implements IdentifiablePacket {
    private int x;
    private int y;
    private int z;
    public static final Identifier ID = Identifier.of(NetherStorage.NAMESPACE, "request_block_entity_data");
    public RequestBlockEntityDataPacket(){}
    public RequestBlockEntityDataPacket(int x, int y, int z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    @Override
    public void read(DataInputStream stream) {
        try {
            this.x = stream.readInt();
            this.y = stream.readInt();
            this.z = stream.readInt();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void write(DataOutputStream stream) {
        try {
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
        if(blockEntity == null) return;
        PacketHelper.sendTo(player, new SendBlockEntityDataPacket(x, y, z, blockEntity.playerName, blockEntity.channelColors[0], blockEntity.channelColors[1], blockEntity.channelColors[2]));
    }

    @Override
    public int size() {
        return 4 * 3;
    }

    @Override
    public Identifier getId() {
        return ID;
    }

    public static void register(){
        IdentifiablePacket.register(ID, false, true, RequestBlockEntityDataPacket::new);
    }
}

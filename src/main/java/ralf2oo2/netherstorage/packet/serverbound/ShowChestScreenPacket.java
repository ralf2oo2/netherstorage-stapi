package ralf2oo2.netherstorage.packet.serverbound;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.NetworkHandler;
import net.minecraft.network.packet.Packet;
import net.modificationstation.stationapi.api.entity.player.PlayerHelper;
import net.modificationstation.stationapi.api.network.packet.IdentifiablePacket;
import net.modificationstation.stationapi.api.util.Identifier;
import ralf2oo2.netherstorage.NetherStorage;
import ralf2oo2.netherstorage.blockentity.NetherChestBlockEntity;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ShowChestScreenPacket extends Packet implements IdentifiablePacket {
    private int x;
    private int y;
    private int z;
    public static final Identifier ID = Identifier.of(NetherStorage.NAMESPACE, "show_chest_screen");
    public ShowChestScreenPacket(){}
    public ShowChestScreenPacket(int x, int y, int z){
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
        BlockEntity blockEntity = player.world.getBlockEntity(x, y, z);
        if(!(blockEntity instanceof NetherChestBlockEntity)) return;
        player.method_486(((NetherChestBlockEntity) blockEntity).getState());
    }

    @Override
    public int size() {
        return 3 * 4;
    }

    @Override
    public Identifier getId() {
        return ID;
    }
    public static void register(){
        IdentifiablePacket.register(ID, false, true, ShowChestScreenPacket::new);
    }
}

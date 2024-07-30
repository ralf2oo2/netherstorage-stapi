package ralf2oo2.netherstorage.packet.clientbound;

import net.minecraft.network.NetworkHandler;
import net.minecraft.network.packet.Packet;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.network.packet.IdentifiablePacket;
import net.modificationstation.stationapi.api.util.Identifier;
import ralf2oo2.netherstorage.NetherStorage;
import ralf2oo2.netherstorage.NetherStorageClient;
import ralf2oo2.netherstorage.blockentity.NetherChestBlockEntity;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import static ralf2oo2.netherstorage.block.NetherChestBlock.PROTECTED;

public class UpdateBlockPacket extends Packet implements IdentifiablePacket {
    private int x;
    private int y;
    private int z;
    public static final Identifier ID = Identifier.of(NetherStorage.NAMESPACE, "update_block");
    public UpdateBlockPacket(){}
    public UpdateBlockPacket(int x, int y, int z){
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
        System.out.println("Updating block at X:" + x + " Y:" + y + " Z:" + z);
        NetherStorageClient.getMc().player.world.method_246(x, y, z);
        World world = NetherStorageClient.getMc().player.world;
        NetherChestBlockEntity blockEntity = (NetherChestBlockEntity) world.getBlockEntity(x, y, z);
        world.setBlockState(x, y, z, world.getBlockState(x, y, z).with(PROTECTED, true));
        blockEntity.cancelRemoval();
        world.method_157(x, y, z, blockEntity);
        world.method_246(x, y, z);
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
        IdentifiablePacket.register(ID, true, false, UpdateBlockPacket::new);
    }
}

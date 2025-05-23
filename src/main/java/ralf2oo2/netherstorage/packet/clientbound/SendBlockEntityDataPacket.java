package ralf2oo2.netherstorage.packet.clientbound;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.FabricLoader;
import net.minecraft.network.NetworkHandler;
import net.minecraft.network.packet.Packet;
import net.modificationstation.stationapi.api.network.packet.ManagedPacket;
import net.modificationstation.stationapi.api.network.packet.PacketType;
import net.modificationstation.stationapi.api.registry.PacketTypeRegistry;
import net.modificationstation.stationapi.api.registry.Registry;
import net.modificationstation.stationapi.api.util.Identifier;
import org.jetbrains.annotations.NotNull;
import ralf2oo2.netherstorage.NetherStorage;
import ralf2oo2.netherstorage.NetherStorageClient;
import ralf2oo2.netherstorage.blockentity.NetherChestBlockEntity;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class SendBlockEntityDataPacket extends Packet implements ManagedPacket<SendBlockEntityDataPacket> {
    private int x;
    private int y;
    private int z;
    private String playerName;
    private String color1;
    private String color2;
    private String color3;
    public static final PacketType<SendBlockEntityDataPacket> TYPE = PacketType.builder(true, false, SendBlockEntityDataPacket::new).build();
    public static final Identifier ID = Identifier.of(NetherStorage.NAMESPACE, "send_block_entity_data");

    public SendBlockEntityDataPacket(){}
    public SendBlockEntityDataPacket(int x, int y, int z, String playerName, String color1, String color2, String color3){
        this.x = x;
        this.y = y;
        this.z = z;
        this.playerName = playerName;
        this.color1 = color1;
        this.color2 = color2;
        this.color3 = color3;
    }
    @Override
    public void read(DataInputStream stream) {
        try {
            x = stream.readInt();
            y = stream.readInt();
            z = stream.readInt();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        playerName = readString(stream, 100);
        color1 = readString(stream, 100);
        color2 = readString(stream, 100);
        color3 = readString(stream, 100);
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
        writeString(playerName, stream);
        writeString(color1, stream);
        writeString(color2, stream);
        writeString(color3, stream);
    }

    public void apply(NetworkHandler networkHandler) {
        if(FabricLoader.INSTANCE.getEnvironmentType() == EnvType.SERVER)return;
        NetherChestBlockEntity blockEntity = (NetherChestBlockEntity) NetherStorageClient.getMc().player.world.getBlockEntity(x, y, z);
        blockEntity.playerName = playerName;
        blockEntity.channelColors[0] = color1;
        blockEntity.channelColors[1] = color2;
        blockEntity.channelColors[2] = color3;
        blockEntity.markDirty();
    }

    @Override
    public int size() {
        return playerName.length() + color1.length() + color2.length() + color3.length() + 4 * 3;
    }

    public static void register(){
        Registry.register(PacketTypeRegistry.INSTANCE, ID, TYPE);
    }

    @Override
    public @NotNull PacketType getType() {
        return TYPE;
    }
}

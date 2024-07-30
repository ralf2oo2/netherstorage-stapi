package ralf2oo2.netherstorage.packet.serverbound;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.FabricLoader;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.NetworkHandler;
import net.minecraft.network.packet.Packet;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.entity.player.PlayerHelper;
import net.modificationstation.stationapi.api.network.packet.IdentifiablePacket;
import net.modificationstation.stationapi.api.network.packet.PacketHelper;
import net.modificationstation.stationapi.api.util.Identifier;
import ralf2oo2.netherstorage.NetherStorage;
import ralf2oo2.netherstorage.block.NetherChestBlock;
import ralf2oo2.netherstorage.blockentity.NetherChestBlockEntity;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class SetProtectedStatePacket extends Packet implements IdentifiablePacket {

    private int x;
    private int y;
    private int z;
    private boolean state;
    public static final Identifier ID = Identifier.of(NetherStorage.NAMESPACE, "set_protected_state");

    public SetProtectedStatePacket(){}
    public SetProtectedStatePacket(int x, int y, int z, boolean state){
        this.x = x;
        this.y = y;
        this.z = z;
        this.state = state;
    }
    @Override
    public void read(DataInputStream stream) {
        try {
            this.x = stream.readInt();
            this.y = stream.readInt();
            this.z = stream.readInt();
            this.state = stream.readBoolean();
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
            stream.writeBoolean(state);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //TODO: fix blockstate syncing
    @Override
    public void apply(NetworkHandler networkHandler) {
        if(FabricLoader.INSTANCE.getEnvironmentType() == EnvType.CLIENT){
            PlayerEntity player = PlayerHelper.getPlayerFromPacketHandler(networkHandler);
            if(player == null || !state) return;
            World world = player.world;
            NetherChestBlockEntity blockEntity = (NetherChestBlockEntity) world.getBlockEntity(x, y, z);
            world.setBlockState(x, y, z, world.getBlockState(x, y, z).with(NetherChestBlock.PROTECTED, state));
            blockEntity.cancelRemoval();
            world.method_157(x, y, z, blockEntity);
            world.method_246(x, y, z);
        } else if (FabricLoader.INSTANCE.getEnvironmentType() == EnvType.SERVER) {
            PlayerEntity player = PlayerHelper.getPlayerFromPacketHandler(networkHandler);
            if(player == null) return;
            World world = player.world;
            NetherChestBlockEntity blockEntity = (NetherChestBlockEntity) world.getBlockEntity(x, y, z);
            world.setBlockState(x, y, z, world.getBlockState(x, y, z).with(NetherChestBlock.PROTECTED, state));
            blockEntity.cancelRemoval();
            world.method_157(x, y, z, blockEntity);
            world.method_246(x, y, z);

            PacketHelper.sendTo(player, new SetProtectedStatePacket(x, y, z, state));
        }
    }

    @Override
    public int size() {
        return 4 * 4;
    }

    @Override
    public Identifier getId() {
        return ID;
    }

    public static void register(){
        IdentifiablePacket.register(ID, true, true, SetProtectedStatePacket::new);
    }
}

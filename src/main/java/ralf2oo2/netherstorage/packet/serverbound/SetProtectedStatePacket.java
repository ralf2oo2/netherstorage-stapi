package ralf2oo2.netherstorage.packet.serverbound;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.network.NetworkHandler;
import net.minecraft.network.packet.Packet;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.entity.player.PlayerHelper;
import net.modificationstation.stationapi.api.network.packet.ManagedPacket;
import net.modificationstation.stationapi.api.network.packet.PacketType;
import net.modificationstation.stationapi.api.registry.PacketTypeRegistry;
import net.modificationstation.stationapi.api.registry.Registry;
import net.modificationstation.stationapi.api.util.Identifier;
import org.jetbrains.annotations.NotNull;
import ralf2oo2.netherstorage.NetherStorage;
import ralf2oo2.netherstorage.block.NetherChestBlock;
import ralf2oo2.netherstorage.blockentity.NetherChestBlockEntity;
import ralf2oo2.netherstorage.registry.BlockRegistry;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class SetProtectedStatePacket extends Packet implements ManagedPacket<SetProtectedStatePacket> {

    private int x;
    private int y;
    private int z;
    private boolean state;
    public static final PacketType<SetProtectedStatePacket> TYPE = PacketType.builder(false, true, SetProtectedStatePacket::new).build();
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

    @Override
    public void apply(NetworkHandler networkHandler) {
        PlayerEntity player = PlayerHelper.getPlayerFromPacketHandler(networkHandler);
        if(player == null) return;
        if(state){
            if(player.getHand() == null || player.getHand().getItem().id != Item.DIAMOND.id){
                return;
            }
            else {
                player.getHand().count--;
                if(player.getHand() != null && player.getHand().count == 0){
                    player.inventory.main[player.inventory.selectedSlot] = null;
                }
            }
        }

        World world = player.world;

        if(!state && !world.getBlockState(x, y, z).get(NetherChestBlock.PROTECTED).booleanValue()){
            return;
        }

        NetherChestBlockEntity blockEntity = (NetherChestBlockEntity) world.getBlockEntity(x, y, z);
        NetherChestBlock.changingBlockstate = true;
        world.setBlockStateWithNotify(x, y, z, world.getBlockState(x, y, z).with(NetherChestBlock.PROTECTED, state));
        blockEntity.cancelRemoval();
        world.setBlockEntity(x, y, z, blockEntity);
        world.setBlockDirty(x, y, z);
        NetherChestBlock.changingBlockstate = false;
        if(!state){
            ((NetherChestBlock)BlockRegistry.netherChest).ejectDiamond(world, x, y, z);
        }
        //PacketHelper.sendTo(player, new SendBlockEntityDataPacket(x ,y ,z, blockEntity.playerName, blockEntity.channelColors[0], blockEntity.channelColors[1], blockEntity.channelColors[2]));
    }

    @Override
    public int size() {
        return 4 * 4;
    }

    public static void register(){
        Registry.register(PacketTypeRegistry.INSTANCE, ID, TYPE);
    }

    @Override
    public @NotNull PacketType<SetProtectedStatePacket> getType() {
        return TYPE;
    }
}

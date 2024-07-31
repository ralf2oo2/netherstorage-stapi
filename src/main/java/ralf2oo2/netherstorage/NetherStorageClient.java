package ralf2oo2.netherstorage;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ralf2oo2.netherstorage.blockentity.NetherChestBlockEntity;
import ralf2oo2.netherstorage.client.gui.LabelScreen;
import ralf2oo2.netherstorage.state.NetherChestState;

import java.util.HashMap;
import java.util.Map;

public class NetherStorageClient {

    public static Map<BlockPos, String[]> storedColors = new HashMap<>();

    public static void showLabelScreen(NetherChestBlockEntity blockEntity){
        getMc().setScreen(new LabelScreen(blockEntity.getChannel(), blockEntity.getLabel()));
    }
    public static void showLabelScreen(String channel, String label){
        getMc().setScreen(new LabelScreen(channel, label));
    }

    public static Minecraft getMc(){
        return (Minecraft) FabricLoader.getInstance().getGameInstance();
    }

    public static NetherChestState getOrCreateState(String channel){
        World world = getMc().world;
        NetherChestState state = (NetherChestState) world.persistentStateManager.getOrCreate(NetherChestState.class, NetherStorage.getStateId() + channel);
        if(state == null){
            state = new NetherChestState(NetherStorage.getStateId() + channel);
            state.channel = channel;
            world.persistentStateManager.set(NetherStorage.getStateId() + channel, state);
            state.markDirty();
        }
        return state;
    }

    public static boolean isRemote(){
        return getMc().world.isRemote;
    }
}

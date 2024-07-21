package ralf2oo2.netherstorage;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import ralf2oo2.netherstorage.blockentity.NetherChestBlockEntity;
import ralf2oo2.netherstorage.client.gui.ChannelScreen;
import ralf2oo2.netherstorage.client.gui.LabelScreen;

public class NetherStorageClient {
    public static void showChannelScreen(PlayerInventory playerInventory, NetherChestBlockEntity blockEntity){
        getMc().setScreen(new ChannelScreen(playerInventory, blockEntity));
    }

    public static void showLabelScreen(String channel){
        getMc().setScreen(new LabelScreen(channel));
    }

    public static Minecraft getMc(){
        return (Minecraft) FabricLoader.getInstance().getGameInstance();
    }
}

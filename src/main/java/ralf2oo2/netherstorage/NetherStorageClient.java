package ralf2oo2.netherstorage;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import ralf2oo2.netherstorage.client.gui.LabelScreen;

public class NetherStorageClient {

    public static void showLabelScreen(String channel){
        getMc().setScreen(new LabelScreen(channel));
    }

    public static Minecraft getMc(){
        return (Minecraft) FabricLoader.getInstance().getGameInstance();
    }
}

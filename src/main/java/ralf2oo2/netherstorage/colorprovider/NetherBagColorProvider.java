package ralf2oo2.netherstorage.colorprovider;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.modificationstation.stationapi.api.client.color.item.ItemColorProvider;
import ralf2oo2.netherstorage.NetherStorage;

public class NetherBagColorProvider implements ItemColorProvider {
    @Override
    public int getColor(ItemStack stack, int index) {
        NbtCompound nbtCompound = stack.getStationNbt();
        if(nbtCompound != null){
            switch (index){
                case 1:
                    String color1 = nbtCompound.getString("color1");
                    if(color1 != null && color1 != ""){
                        return NetherStorage.getColorFromString(color1);
                    }
                    break;
                case 2:
                    String color2 = nbtCompound.getString("color2");
                    if(color2 != null && color2 != ""){
                        return NetherStorage.getColorFromString(color2);
                    }
                    break;
                case 3:
                    String color3 = nbtCompound.getString("color3");
                    if(color3 != null && color3 != ""){
                        return NetherStorage.getColorFromString(color3);
                    }
                    break;
                case 4:
                    String playerName = nbtCompound.getString("playerName");
                    if(playerName != null && !playerName.equals("")){
                        return 0x36F9D6;
                    }
                    else {
                        return 0xFFDB2B;
                    }
            }
        }
        return 0xFFFFFF;
    }
}

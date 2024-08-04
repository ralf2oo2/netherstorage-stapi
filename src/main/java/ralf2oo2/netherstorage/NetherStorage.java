package ralf2oo2.netherstorage;

import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;

public class NetherStorage {
    @Entrypoint.Namespace
    public static final Namespace NAMESPACE = Null.get();

    public static final String[] DYE_COLORS = {
            "black", "red", "green", "brown", "blue", "purple", "cyan",
            "light_gray", "gray", "pink", "lime", "yellow", "light_blue",
            "magenta", "orange", "white"
    };

    public static int getColorFromString(String colorString){
        int color = 0xFFFFFF;
        switch (colorString){
            case "white":
                color = 0xFFFFFF;
                break;
            case "black":
                color = 0x181414;
                break;
            case "purple":
                color = 0x7e34bf;
                break;
            case "cyan":
                color = 0x267191;
                break;
            case "light_gray":
                color = 0xa0a7a7;
                break;
            case "gray":
                color = 0x414141;
                break;
            case "pink":
                color = 0xd98199;
                break;
            case "lime":
                color = 0x39ba2e;
                break;
            case "light_blue":
                color = 0x6387d2;
                break;
            case "magenta":
                color = 0xbe49c9;
                break;
            case "orange":
                color = 0xea7e35;
                break;
            case "brown":
                color = 0x56331c;
                break;
            case "green":
                color = 0x364b18;
                break;
            case "blue":
                color = 0x253193;
                break;
            case "yellow":
                color = 0xc2b51c;
                break;
            case "red":
                color = 0x9e2b27;
                break;
        }
        return color;
    }

    public static String getStateId(){
        return "netherstorage_netherchest_";
    }
}

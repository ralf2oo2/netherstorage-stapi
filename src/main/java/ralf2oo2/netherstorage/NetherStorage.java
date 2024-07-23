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

    public static String getStateId(){
        return "netherstorage_netherchest_";
    }
}

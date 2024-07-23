package ralf2oo2.netherstorage;

import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;

public class NetherStorage {
    @Entrypoint.Namespace
    public static final Namespace NAMESPACE = Null.get();

    public static String getStateId(){
        return "netherstorage_netherchest_";
    }
}

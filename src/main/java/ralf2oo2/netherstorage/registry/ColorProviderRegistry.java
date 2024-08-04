package ralf2oo2.netherstorage.registry;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.client.event.color.item.ItemColorsRegisterEvent;
import ralf2oo2.netherstorage.colorprovider.NetherBagColorProvider;

public class ColorProviderRegistry {

    @EventListener
    private static void registerItemColors(ItemColorsRegisterEvent event) {
        event.itemColors.register(new NetherBagColorProvider(), ItemRegistry.blackNetherBagItem);
        event.itemColors.register(new NetherBagColorProvider(), ItemRegistry.blueNetherBagItem);
        event.itemColors.register(new NetherBagColorProvider(), ItemRegistry.brownNetherBagItem);
        event.itemColors.register(new NetherBagColorProvider(), ItemRegistry.cyanNetherBagItem);
        event.itemColors.register(new NetherBagColorProvider(), ItemRegistry.grayNetherBagItem);
        event.itemColors.register(new NetherBagColorProvider(), ItemRegistry.greenNetherBagItem);
        event.itemColors.register(new NetherBagColorProvider(), ItemRegistry.lightBlueNetherBagItem);
        event.itemColors.register(new NetherBagColorProvider(), ItemRegistry.lightGrayNetherBagItem);
        event.itemColors.register(new NetherBagColorProvider(), ItemRegistry.limeNetherBagItem);
        event.itemColors.register(new NetherBagColorProvider(), ItemRegistry.magentaNetherBagItem);
        event.itemColors.register(new NetherBagColorProvider(), ItemRegistry.orangeNetherBagItem);
        event.itemColors.register(new NetherBagColorProvider(), ItemRegistry.pinkNetherBagItem);
        event.itemColors.register(new NetherBagColorProvider(), ItemRegistry.purpleNetherBagItem);
        event.itemColors.register(new NetherBagColorProvider(), ItemRegistry.redNetherBagItem);
        event.itemColors.register(new NetherBagColorProvider(), ItemRegistry.whiteNetherBagItem);
        event.itemColors.register(new NetherBagColorProvider(), ItemRegistry.yellowNetherBagItem);
    }
}

package ralf2oo2.netherstorage.registry;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Material;
import net.minecraft.item.Item;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.util.Identifier;
import ralf2oo2.netherstorage.NetherStorage;
import ralf2oo2.netherstorage.block.NetherChestBlock;
import ralf2oo2.netherstorage.item.NetherChannelEditorItem;
import ralf2oo2.netherstorage.item.NetherLabelItem;

public class ItemRegistry {
    public static Item netherChannelEditorItem;
    public static Item netherLabelItem;
    @EventListener
    private static void registerBlocks(ItemRegistryEvent event) {
        netherChannelEditorItem = new NetherChannelEditorItem(NetherStorage.NAMESPACE.id("nether_channel_editor")).setTranslationKey(NetherStorage.NAMESPACE, "nether_channel_editor");
        netherLabelItem = new NetherLabelItem(NetherStorage.NAMESPACE.id("nether_label")).setTranslationKey(NetherStorage.NAMESPACE, "nether_label");

    }
}

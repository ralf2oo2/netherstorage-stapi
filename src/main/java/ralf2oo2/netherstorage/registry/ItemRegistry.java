package ralf2oo2.netherstorage.registry;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.item.Item;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import ralf2oo2.netherstorage.NetherStorage;
import ralf2oo2.netherstorage.item.NetherBagItem;
import ralf2oo2.netherstorage.item.NetherCoreItem;
import ralf2oo2.netherstorage.item.NetherLabelItem;

public class ItemRegistry {
    public static Item netherLabelItem;
    public static Item netherCoreItem;
    public static Item blackNetherBagItem;
    public static Item blueNetherBagItem;
    public static Item brownNetherBagItem;
    public static Item cyanNetherBagItem;
    public static Item grayNetherBagItem;
    public static Item greenNetherBagItem;
    public static Item lightBlueNetherBagItem;
    public static Item lightGrayNetherBagItem;
    public static Item limeNetherBagItem;
    public static Item magentaNetherBagItem;
    public static Item orangeNetherBagItem;
    public static Item pinkNetherBagItem;
    public static Item purpleNetherBagItem;
    public static Item redNetherBagItem;
    public static Item whiteNetherBagItem;
    public static Item yellowNetherBagItem;
    @EventListener
    private static void registerItems(ItemRegistryEvent event) {
        netherLabelItem = new NetherLabelItem(NetherStorage.NAMESPACE.id("nether_label")).setTranslationKey(NetherStorage.NAMESPACE, "nether_label");
        netherCoreItem = new NetherCoreItem(NetherStorage.NAMESPACE.id("nether_core")).setTranslationKey(NetherStorage.NAMESPACE, "nether_core");

        blackNetherBagItem = new NetherBagItem(NetherStorage.NAMESPACE.id("black_nether_bag")).setTranslationKey(NetherStorage.NAMESPACE, "black_nether_bag");
        blueNetherBagItem = new NetherBagItem(NetherStorage.NAMESPACE.id("blue_nether_bag")).setTranslationKey(NetherStorage.NAMESPACE, "blue_nether_bag");
        brownNetherBagItem = new NetherBagItem(NetherStorage.NAMESPACE.id("brown_nether_bag")).setTranslationKey(NetherStorage.NAMESPACE, "brown_nether_bag");
        cyanNetherBagItem = new NetherBagItem(NetherStorage.NAMESPACE.id("cyan_nether_bag")).setTranslationKey(NetherStorage.NAMESPACE, "cyan_nether_bag");
        grayNetherBagItem = new NetherBagItem(NetherStorage.NAMESPACE.id("gray_nether_bag")).setTranslationKey(NetherStorage.NAMESPACE, "gray_nether_bag");
        greenNetherBagItem = new NetherBagItem(NetherStorage.NAMESPACE.id("green_nether_bag")).setTranslationKey(NetherStorage.NAMESPACE, "green_nether_bag");
        lightBlueNetherBagItem = new NetherBagItem(NetherStorage.NAMESPACE.id("light_blue_nether_bag")).setTranslationKey(NetherStorage.NAMESPACE, "light_blue_nether_bag");
        lightGrayNetherBagItem = new NetherBagItem(NetherStorage.NAMESPACE.id("light_gray_nether_bag")).setTranslationKey(NetherStorage.NAMESPACE, "light_gray_nether_bag");
        limeNetherBagItem = new NetherBagItem(NetherStorage.NAMESPACE.id("lime_nether_bag")).setTranslationKey(NetherStorage.NAMESPACE, "lime_nether_bag");
        magentaNetherBagItem = new NetherBagItem(NetherStorage.NAMESPACE.id("magenta_nether_bag")).setTranslationKey(NetherStorage.NAMESPACE, "magenta_nether_bag");
        orangeNetherBagItem = new NetherBagItem(NetherStorage.NAMESPACE.id("orange_nether_bag")).setTranslationKey(NetherStorage.NAMESPACE, "orange_nether_bag");
        pinkNetherBagItem = new NetherBagItem(NetherStorage.NAMESPACE.id("pink_nether_bag")).setTranslationKey(NetherStorage.NAMESPACE, "pink_nether_bag");
        purpleNetherBagItem = new NetherBagItem(NetherStorage.NAMESPACE.id("purple_nether_bag")).setTranslationKey(NetherStorage.NAMESPACE, "purple_nether_bag");
        redNetherBagItem = new NetherBagItem(NetherStorage.NAMESPACE.id("red_nether_bag")).setTranslationKey(NetherStorage.NAMESPACE, "red_nether_bag");
        whiteNetherBagItem = new NetherBagItem(NetherStorage.NAMESPACE.id("white_nether_bag")).setTranslationKey(NetherStorage.NAMESPACE, "white_nether_bag");
        yellowNetherBagItem = new NetherBagItem(NetherStorage.NAMESPACE.id("yellow_nether_bag")).setTranslationKey(NetherStorage.NAMESPACE, "yellow_nether_bag");
    }
}

package ralf2oo2.netherstorage.registry;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.util.Identifier;
import ralf2oo2.netherstorage.NetherStorage;
import ralf2oo2.netherstorage.block.NetherChestBlock;

public class BlockRegistry {
    public static Block netherChest;
    @EventListener
    public static void registerBlocks(BlockRegistryEvent event) {
        netherChest = new NetherChestBlock(Identifier.of(NetherStorage.NAMESPACE, "nether_chest"), Material.WOOD).setTranslationKey(NetherStorage.NAMESPACE, "nether_chest");
    }
}

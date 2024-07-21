package ralf2oo2.netherstorage;

import net.minecraft.class_81;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.world.World;
import ralf2oo2.netherstorage.mixin.Class81Accessor;
import ralf2oo2.netherstorage.mixin.WorldAccessor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class StorageManager {
    public static Map<String, Object> netherStorage = new HashMap<>();
    public static Map<String, String> netherLabels = new HashMap<>();

    public static ItemStack[] getNetherInventory(String channel){
        ItemStack[] netherInventory;
        if(netherStorage != null){
            if(netherStorage.containsKey(channel)){
                netherInventory = (ItemStack[]) netherStorage.get(channel);
            }
            else{
                netherStorage.put(channel, new ItemStack[32]);
                netherInventory = (ItemStack[]) netherStorage.get(channel);
            }
            return netherInventory;
        }
        else {
            netherStorage = new HashMap();
            netherStorage.put(channel, new ItemStack[32]);
            return (ItemStack[]) netherStorage.get(channel);
        }
    }

    static long prevSaveTime = 0;
    public static void saveStorage(World world){
        if(prevSaveTime == world.getTime())
        {
            return;
        }
        if(netherStorage.keySet().toArray().length == 0)
        {
            return;
        }
        try{
            File savePath = getWorldSaveLocation(world);
            File storageFile = new File(savePath, "netherstorage.dat");

            if(!storageFile.exists()) {
                NbtIo.writeCompressed(new NbtCompound(), new FileOutputStream(storageFile));
            }

            NbtCompound compound = NbtIo.readCompressed(new FileInputStream(storageFile));
            NbtCompound storageCompound = new NbtCompound();
            NbtCompound keyCompound = new NbtCompound();
            for (Object key: netherStorage.keySet().toArray()) {
                String keyString = (String)key;
                keyCompound.putString(keyString, keyString);
                NbtCompound entryCompound = new NbtCompound();
                NbtList keyInvCompound = new NbtList();
                if(netherLabels.containsKey(keyString))
                {
                    entryCompound.putString("label", netherLabels.get(keyString));
                }
                for (int index = 0; index <  ((ItemStack[])netherStorage.get(keyString)).length; index++) {
                    ItemStack[] inventory = (ItemStack[])netherStorage.get(keyString);
                    if(inventory[index] != null) {
                        NbtCompound nBTTagCompound4 = new NbtCompound();
                        nBTTagCompound4.putByte("Slot", (byte)index);
                        inventory[index].writeNbt(nBTTagCompound4);
                        keyInvCompound.add(nBTTagCompound4);
                    }
                }
                entryCompound.put("inventory", keyInvCompound);
                storageCompound.put(keyString, entryCompound);
            }
            compound.put("storage", storageCompound);
            compound.put("keys", keyCompound);
            NbtIo.writeCompressed(compound, new FileOutputStream(storageFile));
            prevSaveTime = world.getTime();
        }
        catch (Exception e){
            System.out.println("Netherchests: Save failed");
            System.out.println(e);
        }
    }

    public static void loadStorage(World world){
        if(netherStorage.keySet().toArray().length != 0)
        {
            return;
        }
        try{
            File savePath = getWorldSaveLocation(world);
            File storageFile = new File(savePath, "netherstorage.dat");

            if(!storageFile.exists()) {
                NbtIo.writeCompressed(new NbtCompound(), new FileOutputStream(storageFile));
            }

            NbtCompound compound =  NbtIo.readCompressed(new FileInputStream(storageFile));
            NbtCompound storageCompound = compound.getCompound("storage");
            NbtCompound keyCompound = compound.getCompound("keys");
            Iterator iterator = keyCompound.values().iterator();
            while(iterator.hasNext()) {
                NbtElement nbtBaseKey = (NbtElement) iterator.next();
                if(nbtBaseKey instanceof NbtString) {
                    NbtString nbtTagStringKey = (NbtString)nbtBaseKey;
                    String keyString = nbtTagStringKey.value;
                    NbtCompound entryCompound = storageCompound.getCompound(keyString);
                    if(entryCompound.contains("label"))
                    {
                        netherLabels.put(keyString, entryCompound.getString("label"));
                    }
                    NbtList keyInvCompound = entryCompound.getList("inventory");
                    ItemStack[] inventory = new ItemStack[32];
                    for (int index = 0; index < keyInvCompound.size(); index++) {
                        NbtCompound nBTTagCompound4 = (NbtCompound)keyInvCompound.get(index);
                        int i5 = nBTTagCompound4.getByte("Slot") & 255;
                        if(i5 >= 0 && i5 < inventory.length) {
                            inventory[i5] = new ItemStack(nBTTagCompound4);
                        }
                    }
                    netherStorage.put(keyString, inventory);
                }
            }
        }
        catch (Exception e){
            System.out.println("Netherchests: Load failed");
            System.out.println(e);
        }
    }

    public static File getWorldSaveLocation(World world) {
        return ((WorldAccessor)world).getDimensionData() instanceof class_81 ? ((Class81Accessor)((class_81)((WorldAccessor)world).getDimensionData())).getField_279() : null;
    }
}

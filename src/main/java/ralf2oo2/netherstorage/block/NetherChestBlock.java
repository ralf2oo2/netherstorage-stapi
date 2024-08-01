package ralf2oo2.netherstorage.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.network.packet.PacketHelper;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.state.property.BooleanProperty;
import net.modificationstation.stationapi.api.state.property.EnumProperty;
import net.modificationstation.stationapi.api.template.block.TemplateBlockWithEntity;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.math.Direction;
import ralf2oo2.netherstorage.NetherStorage;
import ralf2oo2.netherstorage.NetherStorageClient;
import ralf2oo2.netherstorage.blockentity.NetherChestBlockEntity;
import ralf2oo2.netherstorage.item.NetherBagItem;
import ralf2oo2.netherstorage.packet.clientbound.ShowLabelScreenPacket;
import ralf2oo2.netherstorage.packet.serverbound.SetChannelValuePacket;
import ralf2oo2.netherstorage.packet.serverbound.SetProtectedStatePacket;
import ralf2oo2.netherstorage.packet.serverbound.ShowChestScreenPacket;
import ralf2oo2.netherstorage.registry.ItemRegistry;

import java.util.Random;

public class NetherChestBlock extends TemplateBlockWithEntity {
    private Random random = new Random();
    public static boolean changingBlockstate = false;
    public static final EnumProperty<Direction> FACING = EnumProperty.of("facing", Direction.class, dir -> dir.getAxis().isHorizontal());
    public static final BooleanProperty PROTECTED = BooleanProperty.of("protected");
    public NetherChestBlock(Identifier identifier, Material material) {
        super(identifier, material);
        setHardness(0.4F);
        setSoundGroup(STONE_SOUND_GROUP);
        setDefaultState(getDefaultState().with(FACING, Direction.NORTH).with(PROTECTED, false));
    }

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, PROTECTED);
    }

    @Override
    public void onPlaced(World world, int x, int y, int z, LivingEntity placer) {
        int direction = MathHelper.floor((double)(placer.yaw * 4.0F / 360.0F) + 0.5) & 3;
        switch (direction){
            case 0:
                world.setBlockState(x, y, z, world.getBlockState(x, y, z).with(FACING, Direction.NORTH));
                break;
            case 1:
                world.setBlockState(x, y, z, world.getBlockState(x, y, z).with(FACING, Direction.EAST));
                break;
            case 2:
                world.setBlockState(x, y, z, world.getBlockState(x, y, z).with(FACING, Direction.SOUTH));
                break;
            case 3:
                world.setBlockState(x, y, z, world.getBlockState(x, y, z).with(FACING, Direction.WEST));
                break;
        }
    }

    @Override
    protected BlockEntity createBlockEntity() {
        return new NetherChestBlockEntity();
    }

    @Override
    public boolean onUse(World world, int x, int y, int z, PlayerEntity player) {
        NetherChestBlockEntity blockEntity = (NetherChestBlockEntity) world.getBlockEntity(x, y, z);
        if(player.getHand() != null){
            if(shouldIgnoreActions(player.getHand())){
                return false;
            }
            else if(player.getHand().getItem() instanceof NetherBagItem && player.method_1373()){
                NbtCompound nbtCompound = player.getHand().getStationNbt();
                nbtCompound.putString("channel", blockEntity.getChannel());
                nbtCompound.putString("label", blockEntity.getLabel());
            }
            else if(player.getHand().itemId == ItemRegistry.netherLabelItem.id){
                if(FabricLoader.INSTANCE.getEnvironmentType() == EnvType.CLIENT){
                    if(!world.isRemote){
                        NetherStorageClient.showLabelScreen(blockEntity);
                    }
                }
                else {
                    PacketHelper.sendTo(player, new ShowLabelScreenPacket(blockEntity.getChannel(), blockEntity.getLabel()));
                }
            }
            else if(player.getHand().itemId == Item.DIAMOND.id && !world.getBlockState(x, y, z).get(PROTECTED).booleanValue()){
                if(FabricLoader.INSTANCE.getEnvironmentType() == EnvType.SERVER) return true;
                if(hitChestLock(world, x, y, z, player)){
                    blockEntity.playerName = player.name;
                    if(!world.isRemote){
                        changingBlockstate = true;
                        world.setBlockState(x, y, z, world.getBlockState(x, y, z).with(PROTECTED, true));
                        blockEntity.cancelRemoval();
                        world.method_157(x, y, z, blockEntity);
                        world.method_246(x, y, z);
                        changingBlockstate = false;
                        player.getHand().count--;
                    } else {
                        NetherStorageClient.storedColors.put(new BlockPos(x, y, z), blockEntity.channelColors);
                        PacketHelper.send(new SetChannelValuePacket(player.name, 0, x, y, z));
                        PacketHelper.send(new SetProtectedStatePacket(x, y ,z, true));
                    }
                }
                else {
                    if(!world.isRemote){
                        showChestScreen(player, blockEntity, world);
                    } else {
                        PacketHelper.send(new ShowChestScreenPacket(x, y, z));
                    }
                }
            }
            else if(player.getHand().itemId == Item.DYE.id){
                if(FabricLoader.INSTANCE.getEnvironmentType() == EnvType.SERVER) return true;
                int hitColorBlock = getHitColorBlock(world, x, y, z, player);
                if(hitColorBlock != -1){
                    String color = NetherStorage.DYE_COLORS[player.getHand().getDamage()];
                    if(!blockEntity.channelColors[hitColorBlock].equals(color)){
                        blockEntity.setColor(hitColorBlock, color);
                        if(!world.isRemote){
                            player.getHand().count--;
                        } else {
                            PacketHelper.send(new SetChannelValuePacket(color, hitColorBlock + 1, x, y, z));
                        }
                        world.method_246(x, y, z);
                    }
                    else {
                        if(!world.isRemote){
                            showChestScreen(player, blockEntity, world);
                        } else {
                            PacketHelper.send(new ShowChestScreenPacket(x, y, z));
                        }
                    }
                }
                else{
                    if(!world.isRemote){
                        showChestScreen(player, blockEntity, world);
                    }
                    else {
                        PacketHelper.send(new ShowChestScreenPacket(x, y, z));
                    }
                }
            }
            else {
                showChestScreen(player, blockEntity, world);
            }
        }
        else {
            showChestScreen(player, blockEntity, world);
        }
        return true;
    }

    private boolean shouldIgnoreActions(ItemStack item){
        boolean ignoreActions = false;

        return ignoreActions;
    }

    private void showChestScreen(PlayerEntity player, Inventory state, World world){
        if(FabricLoader.INSTANCE.getEnvironmentType() == EnvType.CLIENT){
            if(!world.isRemote){
                player.method_486(state);
            }
        }
        else {
            player.method_486(state);
        }
    }

    @Override
    public void onBlockBreakStart(World world, int x, int y, int z, PlayerEntity player) {
        if(world.getBlockState(x, y, z).get(PROTECTED).booleanValue() && hitChestLock(world, x, y, z, player)){
            if(FabricLoader.INSTANCE.getEnvironmentType() == EnvType.SERVER) return;
            NetherChestBlockEntity blockEntity = (NetherChestBlockEntity) world.getBlockEntity(x, y, z);
            blockEntity.playerName = "";
            if(!world.isRemote){
                changingBlockstate = true;
                world.setBlockState(x, y, z, world.getBlockState(x, y, z).with(PROTECTED, false));
                blockEntity.cancelRemoval();
                world.method_157(x, y, z, blockEntity);
                world.method_246(x, y, z);
                changingBlockstate = false;
                ejectDiamond(world, x, y, z);
            } else {
                NetherStorageClient.storedColors.put(new BlockPos(x, y, z), blockEntity.channelColors);
                PacketHelper.send(new SetChannelValuePacket("", 0, x, y, z));
                PacketHelper.send(new SetProtectedStatePacket(x, y ,z, false));
            }
        }
    }

    @Override
    public void onBreak(World world, int x, int y, int z) {
        if (!((NetherChestBlockEntity)world.getBlockEntity(x, y, z)).playerName.equals("") && !changingBlockstate) {
            if(!world.isRemote || FabricLoader.INSTANCE.getEnvironmentType() == EnvType.SERVER){
                float var8 = this.random.nextFloat() * 0.8F + 0.1F;
                float var9 = this.random.nextFloat() * 0.8F + 0.1F;
                float var10 = this.random.nextFloat() * 0.8F + 0.1F;

                ItemEntity var12 = new ItemEntity(world, ((float)x + var8), ((float)y + var9), ((float)z + var10), new ItemStack(Item.DIAMOND));
                float var13 = 0.05F;
                var12.velocityX = ((float)this.random.nextGaussian() * var13);
                var12.velocityY = ((float)this.random.nextGaussian() * var13 + 0.2F);
                var12.velocityZ = ((float)this.random.nextGaussian() * var13);
                world.method_210(var12);
            }
        }
        super.onBreak(world, x, y, z);
    }

    private boolean hitChestLock(World world, int x, int y, int z, PlayerEntity player){
        double pitch = player.pitch;
        double yaw = player.yaw;
        double distance = 18.0d;

        double pitchRadians = Math.toRadians(pitch);
        double yawRadians = Math.toRadians(yaw);

        double normalX = -Math.sin(yawRadians) * Math.cos(pitchRadians);
        double normalY = -Math.sin(pitchRadians);
        double normalZ = Math.cos(yawRadians) * Math.cos(pitchRadians);

        normalX *= distance;
        normalY *= distance;
        normalZ *= distance;

        double targetX = player.x + normalX;
        double targetY = player.y + normalY;
        double targetZ = player.z + normalZ;
        Vec3d playerPos = Vec3d.create(player.x, player.y, player.z);

        Vec3d targetPos = Vec3d.create(targetX, targetY, targetZ);
        HitResult hitResult = world.method_162(playerPos, targetPos, true, true);
        Direction facing = world.getBlockState(x, y, z).get(FACING);
        double hitPixelX = (hitResult.pos.x - x) / 10 * 16;
        double hitPixelY = (hitResult.pos.y - y) / 10 * 16;
        double hitPixelZ = (hitResult.pos.z - z) / 10 * 16;
        if(hitPixelY > 0.9 && hitPixelY < 1.3){
            if(hitResult.pos.z == (double)z){
                // North
                if(facing == Direction.NORTH){
                    if(hitPixelX > 0.7 && hitPixelX < 0.9){
                        return true;
                    }
                }
            }
            if(hitResult.pos.x == (double)x + 1){
                // East
                if(facing == Direction.EAST){
                    if(hitPixelZ > 0.7 && hitPixelZ < 0.9){
                        return true;
                    }
                }
            }
            if(hitResult.pos.z == (double)z + 1){
                // South
                if(facing == Direction.SOUTH){
                    if(hitPixelX > 0.7 && hitPixelX < 0.9){
                        return true;
                    }
                }
            }
            if(hitResult.pos.x == (double)x){
                // West
                if(facing == Direction.WEST){
                    if(hitPixelZ > 0.7 && hitPixelZ < 0.9){
                        return true;
                    }
                }
            }
            if(hitResult.pos.y == (double)y + 1){
                // Top
            }
            if(hitResult.pos.y == (double)y){
                // Bottom
            }
        }
        return false;
    }

    private int getHitColorBlock(World world, int x, int y, int z, PlayerEntity player){
        int hitColorBlock = -1;

        double pitch = player.pitch;
        double yaw = player.yaw;
        double distance = 18.0d;

        double pitchRadians = Math.toRadians(pitch);
        double yawRadians = Math.toRadians(yaw);

        double normalX = -Math.sin(yawRadians) * Math.cos(pitchRadians);
        double normalY = -Math.sin(pitchRadians);
        double normalZ = Math.cos(yawRadians) * Math.cos(pitchRadians);

        normalX *= distance;
        normalY *= distance;
        normalZ *= distance;

        double targetX = player.x + normalX;
        double targetY = player.y + normalY;
        double targetZ = player.z + normalZ;
        Vec3d playerPos = Vec3d.create(player.x, player.y, player.z);

        Vec3d targetPos = Vec3d.create(targetX, targetY, targetZ);
        HitResult hitResult = world.method_162(playerPos, targetPos, true, true);
        Direction facing = world.getBlockState(x, y, z).get(FACING);
        double hitPixelX = (hitResult.pos.x - x) / 10 * 16;
        double hitPixelY = (hitResult.pos.y - y) / 10 * 16;
        double hitPixelZ = (hitResult.pos.z - z) / 10 * 16;
        if(hitResult.pos.y == (double)y + 1){
            if(facing == Direction.NORTH || facing == Direction.SOUTH){
                if(hitPixelZ > 0.6f && hitPixelZ < 1f){
                    if(hitPixelX > 0.3f && hitPixelX < 0.5f){
                        if(facing == Direction.NORTH){
                            hitColorBlock = 2;
                        }
                        else{
                            hitColorBlock = 0;
                        }
                    }
                    if(hitPixelX > 0.7f && hitPixelX < 0.9f){
                        hitColorBlock = 1;
                    }
                    if(hitPixelX > 1.1f && hitPixelX < 1.3f){
                        if(facing == Direction.NORTH){
                            hitColorBlock = 0;
                        }
                        else{
                            hitColorBlock = 2;
                        }
                    }
                }
            }
            if(facing == Direction.EAST || facing == Direction.WEST){
                if(hitPixelX > 0.6f && hitPixelX < 1f){
                    if(hitPixelZ > 0.3f && hitPixelZ < 0.5f){
                        if(facing == Direction.EAST){
                            hitColorBlock = 2;
                        }
                        else{
                            hitColorBlock = 0;
                        }
                    }
                    if(hitPixelZ > 0.7f && hitPixelZ < 0.9f){
                        hitColorBlock = 1;
                    }
                    if(hitPixelZ > 1.1f && hitPixelZ < 1.3f){
                        if(facing == Direction.EAST){
                            hitColorBlock = 0;
                        }
                        else{
                            hitColorBlock = 2;
                        }
                    }
                }
            }
        }

        return hitColorBlock;
    }
    public void ejectDiamond(World world, int x, int y, int z){
        Direction facing = world.getBlockState(x, y, z).get(FACING);
        byte var9 = 0;
        byte var10 = 0;
        if (facing == Direction.SOUTH) {
            var10 = 1;
        } else if (facing == Direction.NORTH) {
            var10 = -1;
        } else if (facing == Direction.EAST) {
            var9 = 1;
        } else if(facing == Direction.WEST){
            var9 = -1;
        }

        ItemStack diamondStack = new ItemStack(Item.DIAMOND);
        diamondStack.count = 1;
        double var13 = (double)x + (double)var9 * 0.6 + 0.5;
        double var15 = (double)y + 0.7;
        double var17 = (double)z + (double)var10 * 0.6 + 0.5;

        ItemEntity var24 = new ItemEntity(world, var13, var15 - 0.3, var17, diamondStack);
        double var20 = random.nextDouble() * 0.1 + 0.2;
        var24.velocityX = (double)var9 * var20;
        var24.velocityY = 0.20000000298023224;
        var24.velocityZ = (double)var10 * var20;
        var24.velocityX += random.nextGaussian() * 0.007499999832361937 * 6.0;
        var24.velocityY += random.nextGaussian() * 0.007499999832361937 * 6.0;
        var24.velocityZ += random.nextGaussian() * 0.007499999832361937 * 6.0;
        world.method_210(var24);
    }
}

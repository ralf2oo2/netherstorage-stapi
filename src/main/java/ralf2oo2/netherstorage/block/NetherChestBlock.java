package ralf2oo2.netherstorage.block;

import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.state.property.BooleanProperty;
import net.modificationstation.stationapi.api.state.property.EnumProperty;
import net.modificationstation.stationapi.api.template.block.TemplateBlockWithEntity;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.math.Direction;
import ralf2oo2.netherstorage.NetherStorage;
import ralf2oo2.netherstorage.NetherStorageClient;
import ralf2oo2.netherstorage.blockentity.NetherChestBlockEntity;
import ralf2oo2.netherstorage.client.gui.ChannelScreen;
import ralf2oo2.netherstorage.inventory.ChannelInventory;
import ralf2oo2.netherstorage.registry.ItemRegistry;

import java.util.Random;

public class NetherChestBlock extends TemplateBlockWithEntity {
    private Random random = new Random();
    public static final EnumProperty<Direction> FACING = EnumProperty.of("facing", Direction.class, dir -> dir.getAxis().isHorizontal());
    public static final BooleanProperty PROTECTED = BooleanProperty.of("protected");
    public NetherChestBlock(Identifier identifier, Material material) {
        super(identifier, material);
        setDefaultState(getDefaultState().with(FACING, Direction.NORTH).with(PROTECTED, false));
    }

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, PROTECTED);
    }

    @Override
    public void onPlaced(World world, int x, int y, int z, LivingEntity placer) {
        int direction = MathHelper.floor((double)(placer.yaw * 4.0F / 360.0F) + 0.5) & 3;
        System.out.println(direction);
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
        if(player.getHand() != null && !world.isRemote){
           if(player.getHand().itemId == ItemRegistry.netherChannelEditorItem.id){
               NetherStorageClient.showChannelScreen(player.inventory, blockEntity);
           }
           else {
               player.method_486(blockEntity);
           }
        }
        else {
            player.method_486(blockEntity);
        }
        return true;
    }

    @Override
    public void onBreak(World world, int x, int y, int z) {
        ChannelInventory channelInventory = ((NetherChestBlockEntity)world.getBlockEntity(x, y, z)).channelInventory;

        for(int l = 0; l < channelInventory.size(); ++l) {
            ItemStack itemstack = channelInventory.getStack(l);
            if (itemstack != null) {
                float f = this.random.nextFloat() * 0.8F + 0.1F;
                float f1 = this.random.nextFloat() * 0.8F + 0.1F;
                float f2 = this.random.nextFloat() * 0.8F + 0.1F;

                while (itemstack.count > 0) {
                    int i1 = this.random.nextInt(21) + 10;
                    if (i1 > itemstack.count) {
                        i1 = itemstack.count;
                    }

                    itemstack.count -= i1;
                    ItemEntity itemEntity = new ItemEntity(world, ((float) x + f), ((float) y + f1), ((float) z + f2), new ItemStack(itemstack.itemId, i1, itemstack.getDamage()));
                    float f3 = 0.05F;
                    itemEntity.velocityX = ((float) this.random.nextGaussian() * f3);
                    itemEntity.velocityY = ((float) this.random.nextGaussian() * f3 + 0.2F);
                    itemEntity.velocityZ = ((float) this.random.nextGaussian() * f3);
                    world.method_210(itemEntity);
                }
            }
        }
    }
}

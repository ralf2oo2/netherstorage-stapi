package ralf2oo2.netherstorage.client.render;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.util.math.BlockPos;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.util.math.Direction;
import org.lwjgl.opengl.GL11;
import ralf2oo2.netherstorage.NetherStorage;
import ralf2oo2.netherstorage.NetherStorageClient;
import ralf2oo2.netherstorage.blockentity.NetherChestBlockEntity;
import ralf2oo2.netherstorage.registry.BlockRegistry;

import static ralf2oo2.netherstorage.block.NetherChestBlock.FACING;
import static ralf2oo2.netherstorage.block.NetherChestBlock.PROTECTED;

public class NetherChestBlockEntityRenderer extends BlockEntityRenderer {
    @Override
    public void render(BlockEntity blockEntity, double x, double y, double z, float f) {

        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);
        NetherChestBlockEntity netherChestBlockEntity = (NetherChestBlockEntity) blockEntity;
        BlockState state = dispatcher.world.getBlockState(netherChestBlockEntity.x, netherChestBlockEntity.y, netherChestBlockEntity.z);
        if(state == null || !state.contains(FACING) || !state.contains(PROTECTED)){
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glPopMatrix();
            return;
        }
        Direction facing = state.get(FACING);

        float brightness = dispatcher.world.method_1782(blockEntity.x, blockEntity.y + 1, blockEntity.z);

        Block block = BlockRegistry.netherChest;
        bindTexture("/assets/netherstorage/textures/block/nether_chest_channel.png");
        if(NetherStorageClient.storedColors.containsKey(new BlockPos(blockEntity.x, blockEntity.y, blockEntity.z))){
            ((NetherChestBlockEntity) blockEntity).channelColors = NetherStorageClient.storedColors.get(new BlockPos(blockEntity.x, blockEntity.y, blockEntity.z));
        }

        setGlColor(NetherStorage.getColorFromString(netherChestBlockEntity.channelColors[0]), brightness);
        renderColorCube(x, y, z, 0, facing);

        setGlColor(NetherStorage.getColorFromString(netherChestBlockEntity.channelColors[1]), brightness);
        renderColorCube(x, y, z, 4, facing);

        setGlColor(NetherStorage.getColorFromString(netherChestBlockEntity.channelColors[2]), brightness);
        renderColorCube(x, y, z, 8, facing);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }

    public void setGlColor(int color, float brightness){
        float var10 = (float)(color >> 16 & 255) / 255.0F;
        float var7 = (float)(color >> 8 & 255) / 255.0F;
        float var8 = (float)(color & 255) / 255.0F;
        float var9 = (float)(color >> 24 & 255) / 255.0F;
        if (var9 == 0.0F) {
            var9 = 1.0F;
        }

        GL11.glColor4f(var10 * brightness, var7 * brightness, var8 * brightness, var9);
    }

    public void renderColorCube(double x, double y, double z, int pixelOffset, Direction facing){
        switch(facing){
            case NORTH:
                renderColorCubeNorth(x, y, z, pixelOffset);
                break;
            case EAST:
                renderColorCubeEast(x, y, z, pixelOffset);
                break;
            case SOUTH:
                renderColorCubeSouth(x, y, z, pixelOffset);
                break;
            case WEST:
                renderColorCubeWest(x, y, z, pixelOffset);
                break;
        }
    }

    public void renderColorCubeNorth(double x, double y, double z, int pixelOffset) {
        Tessellator var9 = Tessellator.INSTANCE;
        var9.startQuads();
        double pixelSize = 1d / 16d;

        double offset = pixelSize * pixelOffset;

        double var28 = z + pixelSize * 6d;
        double var30 = z + pixelSize * 10d;
        double var32 = y + 1d;
        double var34 = x + (pixelSize * 11d) - offset;
        double var36 = x + (pixelSize * 13d) - offset;

        double width = pixelSize * 2d;
        double height = pixelSize * 4d;

        var9.vertex(var36, var32 + 0.001, var30, 0d, 0d);
        var9.vertex(var36, var32 + 0.001, var28, 0d, height);
        var9.vertex(var34, var32 + 0.001, var28, width, height);
        var9.vertex(var34, var32 + 0.001, var30, width, 0d);

        var9.draw();
    }

    public void renderColorCubeWest(double x, double y, double z, int pixelOffset) {
        Tessellator var9 = Tessellator.INSTANCE;
        var9.startQuads();
        double pixelSize = 1d / 16d;

        double offset = pixelSize * pixelOffset;

        double var28 = x + pixelSize * 6d;
        double var30 = x + pixelSize * 10d;
        double var32 = y + 1d;
        double var34 = z + (pixelSize * 3d) + offset;
        double var36 = z + (pixelSize * 5d) + offset;

        double width = pixelSize * 2d;
        double height = pixelSize * 4d;

        var9.vertex(var30, var32 + 0.001, var36, width, 0d);
        var9.vertex(var30, var32 + 0.001, var34, 0d, 0d);
        var9.vertex(var28, var32 + 0.001, var34, 0d, height);
        var9.vertex(var28, var32 + 0.001, var36, width, height);

        var9.draw();
    }

    public void renderColorCubeEast(double x, double y, double z, int pixelOffset){
        Tessellator var9 = Tessellator.INSTANCE;
        var9.startQuads();
        double pixelSize = 1d / 16d;

        double offset = pixelSize * pixelOffset;

        double var28 = x + pixelSize * 6d;
        double var30 = x + pixelSize * 10d;
        double var32 = y + 1d;
        double var34 = z + (pixelSize * 11d) - offset;
        double var36 = z + (pixelSize * 13d) - offset;

        double width = pixelSize * 2d;
        double height = pixelSize * 4d;

        var9.vertex(var30, var32 + 0.001, var36, 0d, height);
        var9.vertex(var30, var32 + 0.001, var34, width, height);
        var9.vertex(var28, var32 + 0.001, var34, width, 0d);
        var9.vertex(var28, var32 + 0.001, var36, 0d, 0d);

        var9.draw();
    }

    public void renderColorCubeSouth(double x, double y, double z, int pixelOffset){
        Tessellator var9 = Tessellator.INSTANCE;
        var9.startQuads();
        double pixelSize = 1d / 16d;

        double offset = pixelSize * pixelOffset;

        double var28 = z + pixelSize * 6d;
        double var30 = z + pixelSize * 10d;
        double var32 = y + 1d;
        double var34 = x + (pixelSize * 3d) + offset;
        double var36 = x + (pixelSize * 5d) + offset;

        double width = pixelSize * 2d;
        double height = pixelSize * 4d;

        var9.vertex(var36, var32 + 0.001, var30, width, height);
        var9.vertex(var36, var32 + 0.001, var28, width, 0d);
        var9.vertex(var34, var32 + 0.001, var28, 0d, 0d);
        var9.vertex(var34, var32 + 0.001, var30, 0d, height);

        var9.draw();
    }
}

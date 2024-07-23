package ralf2oo2.netherstorage.client.render;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.modificationstation.stationapi.api.util.math.Direction;
import org.lwjgl.opengl.GL11;
import ralf2oo2.netherstorage.NetherStorageClient;
import ralf2oo2.netherstorage.blockentity.NetherChestBlockEntity;
import ralf2oo2.netherstorage.registry.BlockRegistry;

import static ralf2oo2.netherstorage.block.NetherChestBlock.FACING;

public class NetherChestBlockEntityRenderer extends BlockEntityRenderer {

    private int getColorFromString(String colorString){
        int color = 0xFFFFFF;
        switch (colorString){
            case "white":
                color = 0xFFFFFF;
                break;
            case "black":
                color = 0x181414;
                break;
            case "purple":
                color = 0x7e34bf;
                break;
            case "cyan":
                color = 0x267191;
                break;
            case "light_gray":
                color = 0xa0a7a7;
                break;
            case "gray":
                color = 0x414141;
                break;
            case "pink":
                color = 0xd98199;
                break;
            case "lime":
                color = 0x39ba2e;
                break;
            case "light_blue":
                color = 0x6387d2;
                break;
            case "magenta":
                color = 0xbe49c9;
                break;
            case "orange":
                color = 0xea7e35;
                break;
            case "brown":
                color = 0x56331c;
                break;
            case "green":
                color = 0x364b18;
                break;
            case "blue":
                color = 0x253193;
                break;
            case "yellow":
                color = 0xc2b51c;
                break;
            case "red":
                color = 0x9e2b27;
                break;
        }
        return color;
    }
    @Override
    public void render(BlockEntity blockEntity, double x, double y, double z, float f) {

        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);
        NetherChestBlockEntity netherChestBlockEntity = (NetherChestBlockEntity) blockEntity;
        Direction facing = dispatcher.field_1557.getBlockState(netherChestBlockEntity.x, netherChestBlockEntity.y, netherChestBlockEntity.z).get(FACING);

        float brightness = dispatcher.field_1557.method_1782(blockEntity.x, blockEntity.y + 1, blockEntity.z);

        Block block = BlockRegistry.netherChest;
        bindTexture("assets/netherstorage/textures/block/nether_chest_channel.png");

        setGlColor(getColorFromString(netherChestBlockEntity.color1), brightness);
        renderColorCube(x, y, z, 0, facing);

        setGlColor(getColorFromString(netherChestBlockEntity.color2), brightness);
        renderColorCube(x, y, z, 4, facing);

        setGlColor(getColorFromString(netherChestBlockEntity.color3), brightness);
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

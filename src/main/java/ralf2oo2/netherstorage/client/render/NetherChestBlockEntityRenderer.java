package ralf2oo2.netherstorage.client.render;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import org.lwjgl.opengl.GL11;
import ralf2oo2.netherstorage.NetherStorageClient;
import ralf2oo2.netherstorage.registry.BlockRegistry;

public class NetherChestBlockEntityRenderer extends BlockEntityRenderer {
    @Override
    public void render(BlockEntity blockEntity, double x, double y, double z, float f) {
        Tessellator var9 = Tessellator.INSTANCE;
        var9.startQuads();
        Block block = BlockRegistry.netherChest;
        bindTexture("assets/netherstorage/textures/block/nether_chest_channel.png");
        GL11.glColor3f(1f,1f,1f);


        double var28 = x + 1f / 16f * 6f;
        double var30 = x + 1f / 16f * 10f;
        double var32 = y + block.maxY;
        double var34 = z + 1f / 16f * 11f;
        double var36 = z + 1f / 16f * 13f;

        double width = 1f / 16f * 2f;
        double height = 1f / 16f * 4f;

        var9.vertex(var30, var32 + 0.0001, var36, 0f, 0f);
        var9.vertex(var30, var32 + 0.0001, var34, width, 0f);
        var9.vertex(var28, var32 + 0.0001, var34, width, height);
        var9.vertex(var28, var32 + 0.0001, var36, 0f, height);

        var9.draw();
    }
}

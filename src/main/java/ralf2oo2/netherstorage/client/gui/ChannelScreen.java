package ralf2oo2.netherstorage.client.gui;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import org.lwjgl.opengl.GL11;
import ralf2oo2.netherstorage.blockentity.NetherChestBlockEntity;

public class ChannelScreen extends HandledScreen {
    public ChannelScreen(PlayerInventory playerInventory, NetherChestBlockEntity blockEntity) {
        super(new ChannelScreenHandler(playerInventory, blockEntity));
    }

    @Override
    protected void drawBackground(float tickDelta) {
        int textureId = this.minecraft.textureManager.getTextureId("assets/netherstorage/textures/gui/filter_screen.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.textureManager.bindTexture(textureId);
        int i3 = (this.width - this.backgroundWidth) / 2;
        int i4 = (this.height - this.backgroundHeight) / 2;
        this.drawTexture(i3, i4, 0, 0, this.backgroundWidth, this.backgroundHeight);
    }

    @Override
    protected void drawForeground() {
        super.drawForeground();
    }
}

package ralf2oo2.netherstorage.client.gui;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.modificationstation.stationapi.api.network.packet.PacketHelper;
import org.lwjgl.input.Keyboard;
import ralf2oo2.netherstorage.NetherStorageClient;
import ralf2oo2.netherstorage.packet.serverbound.SetLabelPacket;
import ralf2oo2.netherstorage.state.NetherChestState;

public class LabelScreen extends Screen {
    private TextFieldWidget textField;
    private String channel;
    private String labelContent;

    private String originalName;
    public LabelScreen(String channel, String labelContent){
        this.channel = channel;
        this.labelContent = labelContent;
        originalName = labelContent;
    }

    @Override
    public boolean shouldPause() {
        return true;
    }

    @Override
    public void init() {
        Keyboard.enableRepeatEvents(true);
        this.buttons.clear();
        this.buttons.add(new ButtonWidget(0, this.width / 2 - 100, this.height / 4 + 96 + 12, "Set"));
        this.buttons.add(new ButtonWidget(1, this.width / 2 - 100, this.height / 4 + 120 + 12, "Cancel"));
        this.textField = new TextFieldWidget(this, this.textRenderer, this.width / 2 - 100, this.height / 4 - 10 + 50 + 18, 200, 20, "");
        this.textField.focused = true;
        this.textField.setMaxLength(27);
        this.textField.setText(this.labelContent);
        ((ButtonWidget)this.buttons.get(0)).active = false;
    }

    @Override
    public void tick() {
        super.tick();
        if(originalName.equals(textField.getText())){
            ((ButtonWidget)this.buttons.get(0)).active = false;
        }
    }

    public void removed() {
        Keyboard.enableRepeatEvents(false);
    }

    protected void buttonClicked(ButtonWidget button) {
        if (button.active) {
            if (button.id == 1) {
                this.minecraft.setScreen(null);
            } else if (button.id == 0) {
                String labelContent = this.textField.getText().trim();
                if(labelContent.length() > 0)
                {
                    if(!NetherStorageClient.isRemote()){
                        NetherChestState state = NetherStorageClient.getOrCreateState(channel);
                        if(state != null){
                            state.label = labelContent;
                            state.markDirty();
                        }
                    }
                    else {
                        PacketHelper.send(new SetLabelPacket(channel, labelContent));
                    }
                    System.out.println(labelContent);
                    this.minecraft.setScreen(null);
                }
            }

        }
    }

    protected void keyPressed(char character, int keyCode) {
        this.textField.keyPressed(character, keyCode);
        if (character == '\r') {
            this.buttonClicked((ButtonWidget)this.buttons.get(0));
        }

        ((ButtonWidget)this.buttons.get(0)).active = this.textField.getText().length() > 0;
    }

    protected void mouseClicked(int mouseX, int mouseY, int button) {
        super.mouseClicked(mouseX, mouseY, button);
        this.textField.mouseClicked(mouseX, mouseY, button);
    }

    public void render(int mouseX, int mouseY, float delta) {
        this.renderBackground();
        this.drawCenteredTextWithShadow(this.textRenderer, "Set label", this.width / 2, this.height / 4 - 60 + 20, 0xFFFFFF);
        this.textField.render();
        super.render(mouseX, mouseY, delta);
    }
}

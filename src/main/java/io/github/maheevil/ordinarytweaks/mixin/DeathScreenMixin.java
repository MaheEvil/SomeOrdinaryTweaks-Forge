package io.github.maheevil.ordinarytweaks.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.maheevil.ordinarytweaks.SomeOrdinaryTweaksMod;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(DeathScreen.class)
public abstract class DeathScreenMixin extends Screen {
    @Shadow
    @Final
    private List<Button> exitButtons;

    @Unique
    private String lastDeathCord;

    protected DeathScreenMixin(Component component) {
        super(component);
    }

    @Inject(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    shift = At.Shift.BEFORE,
                    target = "net/minecraft/client/gui/screens/Screen.render(Lcom/mojang/blaze3d/vertex/PoseStack;IIF)V"
            )
    )
    public void render(PoseStack poseStack, int i, int j, float f, CallbackInfo ci){
        if(SomeOrdinaryTweaksMod.config.deathCordsClipBoardButton){
            String xyz = "[X: " + minecraft.player.blockPosition().getX() + "/ Y: " + minecraft.player.blockPosition().getY() + "/ Z: " + minecraft.player.blockPosition().getZ() + "]";
            drawCenteredString(poseStack,this.font, xyz, this.width / 2, this.height / 4 + 145, 16777215);
        }
    }

    @Inject(
            method = "init",
            at = @At(
                    value = "INVOKE",
                    shift = At.Shift.BEFORE,
                    target = "net/minecraft/client/gui/components/Button.<init>(IIIILnet/minecraft/network/chat/Component;Lnet/minecraft/client/gui/components/Button$OnPress;)V"
            )
    )
    protected void init(CallbackInfo ci){
        String xyz =  this.minecraft.player.blockPosition().getX() + " / " + minecraft.player.blockPosition().getY() + " / " + minecraft.player.blockPosition().getZ();
        if(SomeOrdinaryTweaksMod.config.deathCordsClipBoardButton){
            this.exitButtons.add(
                    this.addRenderableWidget(
                            new Button(
                                    this.width / 2 - 100, this.height / 4 + 120, 200, 20,
                                    Component.translatable("Copy Location To Clipboard"),
                                    (buttonx) -> this.minecraft.keyboardHandler.setClipboard(xyz)
                            )
                    )
            );
        }
        this.lastDeathCord = xyz;
    }

    @Inject(
            method = "method_19809(Lnet/minecraft/client/gui/components/Button;)V",
            at = @At(
                    value = "INVOKE",
                    target = "net/minecraft/client/Minecraft.setScreen (Lnet/minecraft/client/gui/screens/Screen;)V",
                    shift = At.Shift.AFTER
            )
    )
    private void handle(Button button, CallbackInfo ci){
        if(SomeOrdinaryTweaksMod.config.sendDeathCords)
            this.minecraft.player.sendSystemMessage(Component.literal(lastDeathCord));
    }

    @Inject(
            method = "confirmResult",
            at = @At(
                    value = "INVOKE",
                    target = "net/minecraft/client/Minecraft.setScreen (Lnet/minecraft/client/gui/screens/Screen;)V",
                    shift = At.Shift.AFTER
            )
    )
    private void handleConfirm(boolean bl, CallbackInfo ci){
        if(SomeOrdinaryTweaksMod.config.sendDeathCords)
            this.minecraft.player.sendSystemMessage(Component.literal(lastDeathCord));
    }
}
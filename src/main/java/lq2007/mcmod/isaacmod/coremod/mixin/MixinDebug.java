package lq2007.mcmod.isaacmod.coremod.mixin;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(Minecraft.class)
public abstract class MixinDebug {

    @ModifyConstant(method = "getWindowTitle", constant = @Constant(stringValue = "Minecraft"))
    private String modifyGetWindowTitle(String title) {
        return "[Isaac: Debug Mode] " + title;
    }
}

package lq2007.mcmod.isaacmod.client.render;

import lq2007.mcmod.isaacmod.client.model.BaseObjModel;
import lq2007.mcmod.isaacmod.common.entity.friend.EntityBobby;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class RenderBobby extends EntityRenderer<EntityBobby> {

    private final BaseObjModel<EntityBobby> model;

    protected RenderBobby(EntityRendererManager renderManager) {
        super(renderManager);
        model = new BaseObjModel<>(EntityBobby.NAME, EntityBobby.TYPE);
    }

    @Override
    public ResourceLocation getEntityTexture(EntityBobby entity) {
        return model.texture;
    }
}

package net.infinite1274.helldivers.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.infinite1274.helldivers.HelldiversMod;
import net.infinite1274.helldivers.entity.custom.HellpodProjectileEntity;
import net.infinite1274.helldivers.entity.custom.MissileProjectileEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class HellpodProjectileRenderer extends EntityRenderer<HellpodProjectileEntity> {
    private HellpodProjectileModel model;
    public HellpodProjectileRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        model = new HellpodProjectileModel(pContext.bakeLayer(ModModelLayers.HELLPOD));
    }

    @Override
    public ResourceLocation getTextureLocation(HellpodProjectileEntity ratEntity) {
        return ResourceLocation.fromNamespaceAndPath(HelldiversMod.MOD_ID, "textures/entity/hellpod/hellpod.png");
    }

    @Override
    public void render(HellpodProjectileEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();

        poseStack.mulPose(Axis.XP.rotationDegrees(180f));

        VertexConsumer vertexConsumer = ItemRenderer.getFoilBufferDirect(
                buffer, this.model.renderType(this.getTextureLocation(entity)), false, false);
        poseStack.translate(0f, 2, 0f);
        this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);

        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public boolean shouldRender(HellpodProjectileEntity pLivingEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
        return true;
    }
}
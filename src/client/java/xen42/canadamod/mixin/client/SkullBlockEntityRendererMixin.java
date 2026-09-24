package xen42.canadamod.mixin.client;

import com.google.common.collect.ImmutableMap;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.SkullBlock;
import net.minecraft.block.SkullBlock.SkullType;
import net.minecraft.block.SkullBlock.Type;
import net.minecraft.client.render.block.entity.SkullBlockEntityModel;
import net.minecraft.client.render.block.entity.SkullBlockEntityRenderer;
import net.minecraft.client.render.entity.model.DragonHeadEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.client.render.entity.model.PiglinHeadEntityModel;
import net.minecraft.client.render.entity.model.SkullEntityModel;
import net.minecraft.util.Identifier;
import xen42.canadamod.CanadaModClient;
import xen42.canadamod.block.skull.CanadaSkullType;
import xen42.canadamod.entity.MooseSkullBlockEntityModel;
import xen42.canadamod.entity.MooseSkullBlockEntityRenderer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(SkullBlockEntityRenderer.class)
@Environment(EnvType.CLIENT)
public abstract class SkullBlockEntityRendererMixin
{
	@Inject(
		at = @At("RETURN"),
		method = "getModels",
		cancellable = true
	)
	private static void canadamod$getModels(
		LoadedEntityModels models,
		SkullType type,
		CallbackInfoReturnable<SkullBlockEntityModel> info
	) {
		if (type instanceof CanadaSkullType cType) {
			SkullBlockEntityModel model = (SkullBlockEntityModel)(switch (cType) {
				case MOOSE -> new MooseSkullBlockEntityModel(models.getModelPart(CanadaModClient.MODEL_MOOSE_SKULL_LAYER));
				default -> throw new MatchException(null, null);
			});
			info.setReturnValue(model);
			info.cancel();
		}
	}
}
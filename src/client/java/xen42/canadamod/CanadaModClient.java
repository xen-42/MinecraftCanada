package xen42.canadamod;

import java.util.function.Supplier;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.WoodType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.block.entity.HangingSignBlockEntityRenderer;
import net.minecraft.client.render.block.entity.SignBlockEntityRenderer;
import net.minecraft.client.render.entity.BoatEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.BoatEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import xen42.canadamod.armor.BeaverHatModel;
import xen42.canadamod.armor.MooseHatModel;
import xen42.canadamod.block.MooseSkullBlock;
import xen42.canadamod.entities.BeaverChopTreeEffectPayload;
import xen42.canadamod.entities.BeaverChopTreeGoal;
import xen42.canadamod.entities.BeaverEntity;
import xen42.canadamod.entities.MapleBoatEntity;
import xen42.canadamod.entity.BeaverEntityModel;
import xen42.canadamod.entity.BeaverEntityRenderer;
import xen42.canadamod.entity.MooseEntityModel;
import xen42.canadamod.entity.MooseEntityRenderer;
import xen42.canadamod.entity.MooseSkullBlockEntityRenderer;
import xen42.canadamod.screen.CookingPotHandledScreen;

public class CanadaModClient implements ClientModInitializer {
	public static final EntityModelLayer MAPLE_BOAT = new EntityModelLayer(Identifier.of(CanadaMod.MOD_ID, "boat/maple"), "main");
	public static final EntityModelLayer MAPLE_CHEST_BOAT = new EntityModelLayer(Identifier.of(CanadaMod.MOD_ID, "chest_boat/maple"), "main");
	public static final EntityModelLayer MODEL_BEAVER_LAYER = new EntityModelLayer(Identifier.of(CanadaMod.MOD_ID, "beaver"), "main");
	public static final EntityModelLayer MODEL_MOOSE_LAYER = new EntityModelLayer(Identifier.of(CanadaMod.MOD_ID, "moose"), "main");

	public class MapleBoat implements EntityModelLayerRegistry.TexturedModelDataProvider {
		@Override
		public TexturedModelData createModelData() {
			return BoatEntityModel.getTexturedModelData();
		}
	}

	public class MapleChestBoat implements EntityModelLayerRegistry.TexturedModelDataProvider {
		@Override
		public TexturedModelData createModelData() {
			return BoatEntityModel.getChestTexturedModelData();
		}
	}

	@Override
	public void onInitializeClient() {
		addCustomWoodTypeTexture(CanadaBlocks.MAPLE_WOOD_TYPE);

		BlockRenderLayerMap.INSTANCE.putBlock(CanadaBlocks.MAPLE_DOOR, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(CanadaBlocks.MAPLE_SAPLING, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(CanadaBlocks.POTTED_MAPLE_SAPLING, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(CanadaBlocks.TREE_TAP, RenderLayer.getCutout());

		EntityRendererRegistry.register(MapleBoatEntity.MAPLE_BOAT, context -> new BoatEntityRenderer(context, MAPLE_BOAT));
		EntityRendererRegistry.register(MapleBoatEntity.MAPLE_CHEST_BOAT, context -> new BoatEntityRenderer(context, MAPLE_CHEST_BOAT));
		EntityModelLayerRegistry.registerModelLayer(MAPLE_BOAT, new MapleBoat());
		EntityModelLayerRegistry.registerModelLayer(MAPLE_CHEST_BOAT, new MapleChestBoat());

		ParticleFactoryRegistry.getInstance().register(CanadaBlocks.MAPLE_LEAF_PARTICLE, MapleLeavesFactory::new);

		BlockEntityRendererFactories.register(CanadaBlocks.MAPLE_SIGN_BLOCK_ENTITY, SignBlockEntityRenderer::new);
		BlockEntityRendererFactories.register(CanadaBlocks.MAPLE_HANGING_SIGN_BLOCK_ENTITY, HangingSignBlockEntityRenderer::new);

		HandledScreens.register(CanadaMod.COOKING_POT_SCREEN_HANDLER_TYPE, CookingPotHandledScreen::new);

		EntityRendererRegistry.register(CanadaMod.BEAVER_ENTITY, context -> new BeaverEntityRenderer(context));
		EntityModelLayerRegistry.registerModelLayer(MODEL_BEAVER_LAYER, BeaverEntityModel::getTexturedModelData);

		EntityRendererRegistry.register(CanadaMod.MOOSE_ENTITY, context -> new MooseEntityRenderer(context));
		EntityModelLayerRegistry.registerModelLayer(MODEL_MOOSE_LAYER, MooseEntityModel::getTexturedModelData);

		ArmorRenderer.register(new CustomArmorRenderer(BeaverHatModel::getModel), CanadaItems.BEAVER_HELMET);
		ArmorRenderer.register(new CustomArmorRenderer(MooseHatModel::getModel), CanadaItems.MOOSE_HELMET);
		ArmorRenderer.register(new BlockOnHeadArmorRenderer(CanadaBlocks.MOOSE_HEAD), CanadaItems.MOOSE_HEAD);

		BlockEntityRendererFactories.register(CanadaMod.MOOSE_HEAD_ENTITY, MooseSkullBlockEntityRenderer::new);

		ClientPlayNetworking.registerGlobalReceiver(BeaverChopTreeEffectPayload.PAYLOAD_ID, (payload, context) -> {
			context.client().execute(() -> {
				// Animations and rendering all done on the client side, but Goal behaviours run on the server
				var beaver = (BeaverEntity)context.client().world.getEntityById(payload.id);
				if (beaver != null) {
					beaver.isChopping = payload.stage != -1;
				}
				context.client().worldRenderer.setBlockBreakingInfo(payload.id, payload.pos, payload.stage);
				
				// Don't play sound when setting a guy to -1
				if (payload.stage > 0) {
					context.client().world.playSoundAtBlockCenterClient(
						payload.pos, BlockSoundGroup.WOOD.getHitSound(), SoundCategory.BLOCKS, 1.0f, 1.0f, false
					);
				}
			});
		});
	}

	public void addCustomWoodTypeTexture(WoodType type) {
		Identifier textureId = Identifier.of(CanadaMod.MOD_ID, "entity/signs/" + type.name());
		SpriteIdentifier spriteId = new SpriteIdentifier(TexturedRenderLayers.SIGN_TYPE_TEXTURES.get(WoodType.OAK).getAtlasId(), textureId);
		TexturedRenderLayers.SIGN_TYPE_TEXTURES.put(type, spriteId);

		Identifier hangingTextureId = Identifier.of(CanadaMod.MOD_ID, "entity/signs/hanging/" + type.name());
		SpriteIdentifier hangingSpriteId = new SpriteIdentifier(TexturedRenderLayers.HANGING_SIGN_TYPE_TEXTURES.get(WoodType.OAK).getAtlasId(), hangingTextureId);
		TexturedRenderLayers.HANGING_SIGN_TYPE_TEXTURES.put(type, hangingSpriteId);
	}

	private class CustomArmorRenderer implements ArmorRenderer {
		private Supplier<TexturedModelData> model;

		public CustomArmorRenderer(Supplier<TexturedModelData> model) {
			this.model = model;
		}

		@Override
		public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack stack,
				BipedEntityRenderState bipedEntityRenderState, EquipmentSlot slot, int light,
				BipedEntityModel<BipedEntityRenderState> contextModel) {
			if (slot != EquipmentSlot.HEAD) {
				return;
			}

			var name = stack.getItem().toString().split(":")[1];

			ModelPart part = model.get().createModel().getChild("hat");
			part.copyTransform(contextModel.getHead());
			part.render(matrices, vertexConsumers.getBuffer(RenderLayer.getArmorCutoutNoCull(Identifier.of(CanadaMod.MOD_ID, "textures/armor/" + name + ".png"))),
				light, OverlayTexture.DEFAULT_UV);

			MinecraftClient.getInstance().getItemRenderer().renderItem(stack, ItemDisplayContext.HEAD, light, OverlayTexture.DEFAULT_UV, matrices,
				vertexConsumers, MinecraftClient.getInstance().world, 0);
		}
	}

	private class BlockOnHeadArmorRenderer implements ArmorRenderer {
		private Block block;

		public BlockOnHeadArmorRenderer(Block block) {
			this.block = block;
		}

		@Override
		public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack stack,
				BipedEntityRenderState bipedEntityRenderState, EquipmentSlot slot, int light,
				BipedEntityModel<BipedEntityRenderState> contextModel) {
			if (slot != EquipmentSlot.HEAD) {
				return;
			}

			matrices.push();

			var head = contextModel.getHead();
			matrices.translate(head.originX / 16.0f, head.originY / 16.0f, head.originZ / 16.0f);
			matrices.multiply(RotationAxis.POSITIVE_Z.rotation(head.roll));
			matrices.multiply(RotationAxis.POSITIVE_Y.rotation(head.yaw));
			matrices.multiply(RotationAxis.POSITIVE_X.rotation(head.pitch));
			matrices.translate(0.5f, -0.025f, -0.75f);
			matrices.multiply(RotationAxis.POSITIVE_Z.rotation((float)Math.PI));
			matrices.scale(1.05f, 1.05f, 1.05f);
			matrices.translate(-0.025f, -0.025f, -0.025f);

			var state = block.getDefaultState();
			if (state.isOf(CanadaBlocks.MOOSE_HEAD)) {
				state = state.with(MooseSkullBlock.WEIRD_HACK, false);
			}

			MinecraftClient.getInstance().getBlockRenderManager().renderBlockAsEntity(state, matrices, vertexConsumers,
				light, OverlayTexture.DEFAULT_UV);

			matrices.pop();
		}
	}
}
package xen42.canadamod;

import java.util.Map;
import java.util.stream.Collectors;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.impl.object.builder.client.SignTypeTextureHelper;
import net.minecraft.block.WoodType;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.data.TexturedModel;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.particle.LeavesParticle.CherryLeavesFactory;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory.Context;
import net.minecraft.client.render.block.entity.HangingSignBlockEntityRenderer;
import net.minecraft.client.render.block.entity.SignBlockEntityRenderer;
import net.minecraft.client.render.entity.BoatEntityRenderer;
import net.minecraft.client.render.entity.model.BoatEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.item.model.special.SignModelRenderer;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Identifier;

public class CanadaModClient implements ClientModInitializer {
	public static final EntityModelLayer MAPLE_BOAT = new EntityModelLayer(Identifier.of(CanadaMod.MOD_ID, "boat/maple"), "main");
	public static final EntityModelLayer MAPLE_CHEST_BOAT = new EntityModelLayer(Identifier.of(CanadaMod.MOD_ID, "chest_boat/maple"), "main");

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

	public class MapleSignBlockEntityRenderer extends SignBlockEntityRenderer {
		public MapleSignBlockEntityRenderer(Context ctx) {
			super(ctx);
		}
		
		@Override
		protected SpriteIdentifier getTextureId(WoodType woodType) {
			return new SpriteIdentifier(
				SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE,
				Identifier.of("minecraft", "entity/signs/oak")
			);
		}
	}

	@Override
	public void onInitializeClient() {
		addCustomWoodTypeTexture(CanadaBlocks.MAPLE_WOOD_TYPE);

		BlockRenderLayerMap.INSTANCE.putBlock(CanadaBlocks.MAPLE_DOOR, RenderLayer.getCutout());

		EntityRendererRegistry.register(MapleBoatEntity.MAPLE_BOAT, context -> new BoatEntityRenderer(context, MAPLE_BOAT));
		EntityRendererRegistry.register(MapleBoatEntity.MAPLE_CHEST_BOAT, context -> new BoatEntityRenderer(context, MAPLE_CHEST_BOAT));
		EntityModelLayerRegistry.registerModelLayer(MAPLE_BOAT, new MapleBoat());
		EntityModelLayerRegistry.registerModelLayer(MAPLE_CHEST_BOAT, new MapleChestBoat());

		ParticleFactoryRegistry.getInstance().register(CanadaBlocks.MAPLE_LEAF_PARTICLE, MapleLeavesFactory::new);

		BlockEntityRendererFactories.register(CanadaBlocks.MAPLE_SIGN_BLOCK_ENTITY, SignBlockEntityRenderer::new);
		BlockEntityRendererFactories.register(CanadaBlocks.MAPLE_HANGING_SIGN_BLOCK_ENTITY, HangingSignBlockEntityRenderer::new);
	}

	public void addCustomWoodTypeTexture(WoodType type) {
		Identifier textureId = Identifier.of(CanadaMod.MOD_ID, "entity/signs/" + type.name());
		SpriteIdentifier spriteId = new SpriteIdentifier(TexturedRenderLayers.SIGN_TYPE_TEXTURES.get(WoodType.OAK).getAtlasId(), textureId);
		TexturedRenderLayers.SIGN_TYPE_TEXTURES.put(type, spriteId);

		Identifier hangingTextureId = Identifier.of(CanadaMod.MOD_ID, "entity/signs/hanging/" + type.name());
		SpriteIdentifier hangingSpriteId = new SpriteIdentifier(TexturedRenderLayers.HANGING_SIGN_TYPE_TEXTURES.get(WoodType.OAK).getAtlasId(), hangingTextureId);
		TexturedRenderLayers.HANGING_SIGN_TYPE_TEXTURES.put(type, hangingSpriteId);
	}
}
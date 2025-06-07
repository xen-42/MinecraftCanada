// Save this class in your mod and generate all required imports
/**
 * Made with Blockbench 4.12.4
 * Exported for Minecraft version 1.19 or later with Yarn mappings
 * @author Author
 */
public class mooseAnimation {
	public static final Animation attack = Animation.Builder.create(1.0F)
		.addBoneAnimation("head", new Transformation(Transformation.Targets.ROTATE, 
			new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.375F, AnimationHelper.createRotationalVector(7.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.5833F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.7917F, AnimationHelper.createRotationalVector(7.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("head", new Transformation(Transformation.Targets.TRANSLATE, 
			new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.375F, AnimationHelper.createTranslationalVector(0.0F, 19.0F, 14.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.7917F, AnimationHelper.createTranslationalVector(0.0F, 19.0F, 14.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.9583F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("body", new Transformation(Transformation.Targets.ROTATE, 
			new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.375F, AnimationHelper.createRotationalVector(40.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.7917F, AnimationHelper.createRotationalVector(40.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.9583F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("body", new Transformation(Transformation.Targets.TRANSLATE, 
			new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.375F, AnimationHelper.createTranslationalVector(0.0F, 9.0F, 2.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.7917F, AnimationHelper.createTranslationalVector(0.0F, 9.0F, 2.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.9583F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("left_front_leg", new Transformation(Transformation.Targets.ROTATE, 
			new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.375F, AnimationHelper.createRotationalVector(-55.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.5833F, AnimationHelper.createRotationalVector(-80.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.7917F, AnimationHelper.createRotationalVector(-80.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.9583F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("left_front_leg", new Transformation(Transformation.Targets.TRANSLATE, 
			new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.375F, AnimationHelper.createTranslationalVector(0.0F, 15.0F, 2.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.7917F, AnimationHelper.createTranslationalVector(0.0F, 15.0F, 2.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.9583F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("right_front_leg", new Transformation(Transformation.Targets.ROTATE, 
			new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.375F, AnimationHelper.createRotationalVector(-55.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.5833F, AnimationHelper.createRotationalVector(-80.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.7917F, AnimationHelper.createRotationalVector(-80.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.9583F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.addBoneAnimation("right_front_leg", new Transformation(Transformation.Targets.TRANSLATE, 
			new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.375F, AnimationHelper.createTranslationalVector(0.0F, 15.0F, 2.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.7917F, AnimationHelper.createTranslationalVector(0.0F, 15.0F, 2.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.9583F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.build();
}
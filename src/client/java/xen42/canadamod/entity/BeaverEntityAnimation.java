package xen42.canadamod.entity;

import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;

public class BeaverEntityAnimation {
    public static final Animation CHOP = Animation.Builder.create(4.0F)
		.addBoneAnimation("head", new Transformation(Transformation.Targets.ROTATE, 
			new Keyframe(0.0F, AnimationHelper.createRotationalVector(25.0F, 0.0F, -35.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.125F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -35.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.25F, AnimationHelper.createRotationalVector(25.0F, 0.0F, -35.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.375F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -35.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.5F, AnimationHelper.createRotationalVector(25.0F, 0.0F, -35.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.625F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -35.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.75F, AnimationHelper.createRotationalVector(25.0F, 0.0F, -35.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.875F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -35.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.0F, AnimationHelper.createRotationalVector(25.0F, 0.0F, -35.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.125F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -35.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.25F, AnimationHelper.createRotationalVector(25.0F, 0.0F, -35.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.375F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -35.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.5F, AnimationHelper.createRotationalVector(25.0F, 0.0F, -35.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.625F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -35.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.75F, AnimationHelper.createRotationalVector(25.0F, 0.0F, -35.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(1.875F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -35.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.0F, AnimationHelper.createRotationalVector(25.0F, 0.0F, -35.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.125F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -35.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.25F, AnimationHelper.createRotationalVector(25.0F, 0.0F, -35.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.375F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -35.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.5F, AnimationHelper.createRotationalVector(25.0F, 0.0F, -35.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.625F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -35.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.75F, AnimationHelper.createRotationalVector(25.0F, 0.0F, -35.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(2.875F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -35.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.0F, AnimationHelper.createRotationalVector(25.0F, 0.0F, -35.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.125F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -35.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.25F, AnimationHelper.createRotationalVector(25.0F, 0.0F, -35.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.375F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -35.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.5F, AnimationHelper.createRotationalVector(25.0F, 0.0F, -35.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.625F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -35.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.75F, AnimationHelper.createRotationalVector(25.0F, 0.0F, -35.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(3.875F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -35.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(4.0F, AnimationHelper.createRotationalVector(25.0F, 0.0F, -35.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(4.125F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -35.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(4.25F, AnimationHelper.createRotationalVector(25.0F, 0.0F, -35.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(4.375F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -35.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(4.5F, AnimationHelper.createRotationalVector(25.0F, 0.0F, -35.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(4.625F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -35.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(4.75F, AnimationHelper.createRotationalVector(25.0F, 0.0F, -35.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(4.875F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -35.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(5.0F, AnimationHelper.createRotationalVector(25.0F, 0.0F, -35.0F), Transformation.Interpolations.LINEAR)
		))
		.build();
}

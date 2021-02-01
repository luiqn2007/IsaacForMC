package lq2007.mcmod.isaacformc.common.entity.ai.controller;

import lq2007.mcmod.isaacformc.common.entity.friend.EntityFriend;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.pathfinding.NodeProcessor;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.VoxelShape;

public class FriendMovementController {

    protected final EntityFriend<?> friend;
    protected double posX;
    protected double posY;
    protected double posZ;
    protected double speed;
    protected float moveForward;
    protected float moveStrafe;
    protected MovementController.Action action = MovementController.Action.WAIT;

    private final int maximumPitchChange;

    public FriendMovementController(EntityFriend<?> friend, int maximumPitchChange) {
        this.friend = friend;
        this.maximumPitchChange = maximumPitchChange;
    }

    public boolean isUpdating() {
        return this.action == MovementController.Action.MOVE_TO;
    }

    public double getSpeed() {
        return this.speed;
    }

    /**
     * Sets the speed and location to move to
     */
    public void setMoveTo(double x, double y, double z, double speedIn) {
        this.posX = x;
        this.posY = y;
        this.posZ = z;
        this.speed = speedIn;
        this.action = MovementController.Action.MOVE_TO;
    }

    public void strafe(float forward, float strafe) {
        this.action = MovementController.Action.STRAFE;
        this.moveForward = forward;
        this.moveStrafe = strafe;
        this.speed = 0.25D;
    }

    public void tick() {
        switch (action) {
            case STRAFE: strafe(); break;
            case JUMPING:
            case MOVE_TO: moveTo(); break;
            case WAIT:
            default: break;
        }
    }

    protected void strafe() {
        float f = (float)this.friend.getAttributeValue(Attributes.MOVEMENT_SPEED);
        float f1 = (float)this.speed * f;
        float f2 = this.moveForward;
        float f3 = this.moveStrafe;
        float f4 = MathHelper.sqrt(f2 * f2 + f3 * f3);
        if (f4 < 1.0F) {
            f4 = 1.0F;
        }

        f4 = f1 / f4;
        f2 = f2 * f4;
        f3 = f3 * f4;
        float f5 = MathHelper.sin(this.friend.rotationYaw * ((float)Math.PI / 180F));
        float f6 = MathHelper.cos(this.friend.rotationYaw * ((float)Math.PI / 180F));
        float f7 = f2 * f6 - f3 * f5;
        float f8 = f3 * f6 + f2 * f5;
        if (!this.func_234024_b_(f7, f8)) {
            this.moveForward = 1.0F;
            this.moveStrafe = 0.0F;
        }

        this.friend.setAIMoveSpeed(f1);
        this.friend.setMoveForward(this.moveForward);
        this.friend.setMoveStrafing(this.moveStrafe);
        this.action = MovementController.Action.WAIT;
    }

    protected void moveTo() {
        this.action = MovementController.Action.WAIT;
        double d0 = this.posX - this.friend.getPosX();
        double d1 = this.posZ - this.friend.getPosZ();
        double d2 = this.posY - this.friend.getPosY();
        double d3 = d0 * d0 + d2 * d2 + d1 * d1;
        if (d3 < (double)2.5000003E-7F) {
            this.friend.setMoveForward(0.0F);
            return;
        }

        float f9 = (float)(MathHelper.atan2(d1, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
        this.friend.rotationYaw = this.limitAngle(this.friend.rotationYaw, f9, 90.0F);
        this.friend.setAIMoveSpeed((float)(this.speed * this.friend.getAttributeValue(Attributes.MOVEMENT_SPEED)));
        BlockPos blockpos = this.friend.getPosition();
        BlockState blockstate = this.friend.world.getBlockState(blockpos);
        Block block = blockstate.getBlock();
        VoxelShape voxelshape = blockstate.getCollisionShape(this.friend.world, blockpos);
        if (d2 > (double)this.friend.stepHeight && d0 * d0 + d1 * d1 < (double)Math.max(1.0F, this.friend.getWidth()) || !voxelshape.isEmpty() && this.friend.getPosY() < voxelshape.getEnd(Direction.Axis.Y) + (double)blockpos.getY() && !block.isIn(BlockTags.DOORS) && !block.isIn(BlockTags.FENCES)) {
            this.friend.getJumpController().setJumping();
            this.action = MovementController.Action.JUMPING;
        }

        // by fly
        this.action = MovementController.Action.WAIT;
        this.friend.setNoGravity(true);
        double d0 = this.posX - this.friend.getPosX();
        double d1 = this.posY - this.friend.getPosY();
        double d2 = this.posZ - this.friend.getPosZ();
        double d3 = d0 * d0 + d1 * d1 + d2 * d2;
        if (d3 < (double)2.5000003E-7F) {
            this.friend.setMoveVertical(0.0F);
            this.friend.setMoveForward(0.0F);
            return;
        }

        float f = (float)(MathHelper.atan2(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
        this.friend.rotationYaw = this.limitAngle(this.friend.rotationYaw, f, 90.0F);
        float f1;
        if (this.friend.isOnGround()) {
            f1 = (float)(this.speed * this.friend.getAttributeValue(Attributes.MOVEMENT_SPEED));
        } else {
            f1 = (float)(this.speed * this.friend.getAttributeValue(Attributes.FLYING_SPEED));
        }

        this.friend.setAIMoveSpeed(f1);
        double d4 = (double)MathHelper.sqrt(d0 * d0 + d2 * d2);
        float f2 = (float)(-(MathHelper.atan2(d1, d4) * (double)(180F / (float)Math.PI)));
        this.friend.rotationPitch = this.limitAngle(this.friend.rotationPitch, f2, maximumPitchChange);
        this.friend.setMoveVertical(d1 > 0.0D ? f1 : -f1);
    }

    private boolean func_234024_b_(float p_234024_1_, float p_234024_2_) {
        PathNavigator pathnavigator = this.friend.getNavigator();
        if (pathnavigator != null) {
            NodeProcessor nodeprocessor = pathnavigator.getNodeProcessor();
            if (nodeprocessor != null && nodeprocessor.getPathNodeType(this.friend.world, MathHelper.floor(this.friend.getPosX() + (double)p_234024_1_), MathHelper.floor(this.friend.getPosY()), MathHelper.floor(this.friend.getPosZ() + (double)p_234024_2_)) != PathNodeType.WALKABLE) {
                return false;
            }
        }

        return true;
    }

    /**
     * Attempt to rotate the first angle to become the second angle, but only allow overall direction change to at max be
     * third parameter
     */
    protected float limitAngle(float sourceAngle, float targetAngle, float maximumChange) {
        float f = MathHelper.wrapDegrees(targetAngle - sourceAngle);
        if (f > maximumChange) {
            f = maximumChange;
        }

        if (f < -maximumChange) {
            f = -maximumChange;
        }

        float f1 = sourceAngle + f;
        if (f1 < 0.0F) {
            f1 += 360.0F;
        } else if (f1 > 360.0F) {
            f1 -= 360.0F;
        }

        return f1;
    }

    public double getX() {
        return this.posX;
    }

    public double getY() {
        return this.posY;
    }

    public double getZ() {
        return this.posZ;
    }
}

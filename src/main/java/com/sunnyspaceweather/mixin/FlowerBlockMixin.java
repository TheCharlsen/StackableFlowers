package com.sunnyspaceweather.mixin;

import net.minecraft.block.*;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(targets = "net.minecraft.block.FlowerBlock")
public class FlowerBlockMixin extends PlantBlock implements SuspiciousStewIngredient, Fertilizable{
    private final StatusEffect effectInStew;
    private final int effectInStewDuration;
    private static final IntProperty FLOWER_AMOUNT = Properties.FLOWER_AMOUNT;
    private static final VoxelShape ONE_FLOWER_SHAPE = Block.createCuboidShape(5.0, 0.0, 5.0, 11.0, 10.0, 11.0);
    private static final VoxelShape TWO_FLOWER_SHAPE = Block.createCuboidShape(3.0, 0.0, 3.0, 13.0, 10.0, 13.0);
    private static final VoxelShape THREE_FLOWER_SHAPE = Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 10.0, 14.0);
    private static final VoxelShape FOUR_FLOWER_SHAPE = Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 10.0, 14.0);


    public FlowerBlockMixin(StatusEffect suspiciousStewEffect, int effectDuration, AbstractBlock.Settings settings) {
        super(settings);
        this.effectInStew = suspiciousStewEffect;
        this.effectInStewDuration = suspiciousStewEffect.isInstant() ? effectDuration : effectDuration * 20;
        this.setDefaultState((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(FLOWER_AMOUNT, 1)));
    }

    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        if (!context.shouldCancelInteraction() && context.getStack().isOf(this.asItem()) && state.get(FLOWER_AMOUNT) < 4) {
            return true;
        }
        return super.canReplace(state, context);
    }


    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos());
        if (blockState.isOf(this)) {
            return blockState.with(FLOWER_AMOUNT, Math.min(4, blockState.get(FLOWER_AMOUNT) + 1));
        }
        return this.getDefaultState();
    }


    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FLOWER_AMOUNT);
    }


    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Vec3d vec3d = state.getModelOffset(world, pos);
        switch (state.get(FLOWER_AMOUNT)) {
            default: {
                return ONE_FLOWER_SHAPE.offset(vec3d.x, vec3d.y, vec3d.z);
            }
            case 2: {
                return TWO_FLOWER_SHAPE.offset(vec3d.x, vec3d.y, vec3d.z);
            }
            case 3: {
                return THREE_FLOWER_SHAPE.offset(vec3d.x, vec3d.y, vec3d.z);
            }
            case 4:
        }
        return FOUR_FLOWER_SHAPE.offset(vec3d.x, vec3d.y, vec3d.z);
    }


    public StatusEffect getEffectInStew() {
        return this.effectInStew;
    }


    public int getEffectInStewDuration() {
        return this.effectInStewDuration;
    }


    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }


    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }


    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        int i = state.get(FLOWER_AMOUNT);
        if (i < 4) {
            world.setBlockState(pos, state.with(FLOWER_AMOUNT, i + 1), Block.NOTIFY_LISTENERS);
        } else {
            FlowerbedBlock.dropStack(world, pos, new ItemStack(this));
        }
    }
}

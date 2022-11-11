package com.dreamtea.mobs;

import com.dreamtea.imixin.IParrotDisc;
import net.minecraft.block.BlockState;
import net.minecraft.block.JukeboxBlock;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.ParrotEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.dreamtea.consts.PARROT_DISC;
import static net.minecraft.block.Blocks.AIR;
import static net.minecraft.block.Blocks.JUKEBOX;

public class PartyParrot {
  public boolean hasDisc = true;
  private final String PROVIDED_DISC_KEY = "provided_disc";

  public void writeCustomDataToNbt(NbtCompound nbt) {
    nbt.putBoolean(PROVIDED_DISC_KEY, hasDisc);
  }

  public void readCustomDataFromNbt(NbtCompound nbt) {
    if(nbt.contains(PROVIDED_DISC_KEY)){
      hasDisc = nbt.getBoolean(PROVIDED_DISC_KEY);
      return;
    }
    hasDisc = true;
  }

  public static class FlyOntoJukebox extends MoveToTargetPosGoal {

    private static final int PARROT_VARIANTS = 5;

    private BlockPos lastJukeSeen;
    private List<ParrotEntity> friends;
    private int xRange = 8, yRange = 2, zRange = 8;
    private int state = 0;

    public FlyOntoJukebox(PathAwareEntity mob, double speed, int range) {
      super(mob, speed, range);
      this.xRange = range;
      this.zRange = range;
    }

    @Override
    public void tick() {
      super.tick();
      if(this.getParrot() instanceof IParrotDisc self){
        if(!self.canDropDisc()){
          state = 2;
        }
      }
      if(state == 1 && !this.getParrot().isInAir()){
        if(this.hasReached()){
          BlockState juke = this.mob.world.getBlockState(lastJukeSeen);
          if(juke.isOf(JUKEBOX) && juke.getBlock() instanceof JukeboxBlock jukebox){
            jukebox.setRecord(this.getParrot(), this.mob.world, this.lastJukeSeen, juke, PARROT_DISC.get());
            for(ParrotEntity friend: friends){
              if(friend instanceof IParrotDisc ipd) {
                ipd.setDropDisc(false);
              }
            }
            state = 2;
          }
        }
      }
    }

    protected boolean findTargetPos(){
      boolean found = super.findTargetPos();
      if(!found){
        this.lastJukeSeen = null;
      }
      return found;
    }

    @Override
    public void stop() {
      if(state < 2){
        state = 0;
      }
    }

    @Override
    public void start() {
      state = 1;
      this.getParrot().setSitting(false);
      super.start();
    }

    @Override
    public boolean canStart() {
      if(state != 0 || this.getParrot().getOwner() == null || !super.canStart()){
        return false;
      }
      friends = friendlyParrots();
      return friends != null;
    }

    protected int getInterval(PathAwareEntity mob) {
      return MoveToTargetPosGoal.toGoalTicks(10 + mob.getRandom().nextInt(40));
    }

    @Override
    protected boolean isTargetPos(WorldView world, BlockPos pos) {
      if(world.getBlockState(pos).isOf(JUKEBOX)){
        if(world.getBlockState(pos.up()).isOf(AIR)){
          lastJukeSeen = pos;
          return true;
        }
      }
      return false;
    }

    @Nullable
    private List<ParrotEntity> friendlyParrots() {
      List<ParrotEntity> parrots = this.mob.world.getNonSpectatingEntities(ParrotEntity.class, getBox(lastJukeSeen));
      if(parrots.size() < PARROT_VARIANTS){
        return null;
      }
      Set<Integer> variants = new HashSet<>();
      List<ParrotEntity> acceptableParrots = new ArrayList<>();
      for(ParrotEntity parrot: parrots){
        if(parrot instanceof IParrotDisc pDisc){
          if(pDisc.canDropDisc() && parrot.isOwner(this.getParrot().getOwner())){
            if(variants.add(parrot.getVariant())){
              acceptableParrots.add(parrot);
            }
          }
        }
      }
      if(acceptableParrots.size() < PARROT_VARIANTS){
        return null;
      }
      return acceptableParrots;
    }

    private ParrotEntity getParrot(){
      if(this.mob instanceof ParrotEntity tamable){
        return tamable;
      }
      throw new IllegalStateException("This goal is only applicable to parrots");
    }

    private Box getBox(BlockPos pos){
      return new Box(pos.add(xRange, yRange, zRange), pos.add(-xRange, -yRange, -zRange));
    }
  }
}

package com.dreamtea.mixin.block_entity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.GrindstoneScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.world.WorldEvents;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.dreamtea.CloneLimitGamerule.CLONE_LIMIT;
import static com.dreamtea.util.DiskGenerator.COPIES_KEY;
import static com.dreamtea.util.DiskGenerator.EDITION_KEY;
import static com.dreamtea.util.DiskGenerator.cloneDisk;
import static net.minecraft.tag.ItemTags.MUSIC_DISCS;

@Mixin(GrindstoneScreenHandler.class)
public abstract class GrindstoneScreenHandlerMixin extends ScreenHandler {

  @Shadow @Final Inventory input;
  @Shadow @Final private ScreenHandlerContext context;

  @Shadow @Final private Inventory result;


  private PlayerEntity player;


  protected GrindstoneScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId) {
    super(type, syncId);
  }

  @Inject(method = "updateResult", at = @At("HEAD"), cancellable = true)
  public void setResultForDiskClone(CallbackInfo ci){
    if(cloningDisk()){
      this.result.setStack(0, cloneDisk(input.getStack(0), 1, 0));
      sendContentUpdates();
      ci.cancel();
    }
  }

  @Inject(method = "<init>(ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/screen/ScreenHandlerContext;)V", at = @At("RETURN"))
  public void storeWorld(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context, CallbackInfo ci){
    player = playerInventory.player;
  }

  @Redirect(method = "<init>(ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/screen/ScreenHandlerContext;)V",
    at = @At(
      value = "INVOKE",
      target = "Lnet/minecraft/screen/GrindstoneScreenHandler;addSlot(Lnet/minecraft/screen/slot/Slot;)Lnet/minecraft/screen/slot/Slot;",
      ordinal = 2
    )
  )
  public Slot canPrintDiscs(GrindstoneScreenHandler instance, Slot slot) {
    Inventory inputSlots = this.input;
    return this.addSlot(new Slot(slot.inventory, slot.getIndex(), slot.x, slot.y){

      @Override
      public boolean canInsert(ItemStack stack) {
        return false;
      }

      @Override
      public void onTakeItem(PlayerEntity player, ItemStack stack) {
        context.run((world, pos) -> {
          world.syncWorldEvent(WorldEvents.GRINDSTONE_USED, pos, 0);
        });
        if(!inputSlots.getStack(0).isIn(MUSIC_DISCS)) {
          inputSlots.setStack(0, ItemStack.EMPTY);
        } else {
          inputSlots.setStack(0, cloneDisk(inputSlots.getStack(0), 0, 1));
        }

        inputSlots.setStack(1, ItemStack.EMPTY);
      }
    });
  }

  @Redirect(method = "<init>(ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/screen/ScreenHandlerContext;)V",
    at = @At(
      value = "INVOKE",
      target = "Lnet/minecraft/screen/GrindstoneScreenHandler;addSlot(Lnet/minecraft/screen/slot/Slot;)Lnet/minecraft/screen/slot/Slot;",
      ordinal = 1

    )
  )
  public Slot canTakeDiscs2(GrindstoneScreenHandler instance, Slot slot) {
    return this.addSlot(new Slot(slot.inventory, slot.getIndex(), slot.x, slot.y) {
      @Override
      public boolean canInsert(ItemStack stack) {
        return stack.isDamageable() || stack.isOf(Items.ENCHANTED_BOOK)
                 || stack.hasEnchantments() || stack.isIn(MUSIC_DISCS);
      }
    });
  }

  @Redirect(method = "<init>(ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/screen/ScreenHandlerContext;)V",
    at = @At(
      value = "INVOKE",
      target = "Lnet/minecraft/screen/GrindstoneScreenHandler;addSlot(Lnet/minecraft/screen/slot/Slot;)Lnet/minecraft/screen/slot/Slot;",
      ordinal = 0

    )
  )
  public Slot canTakeDiscs1(GrindstoneScreenHandler instance, Slot slot) {
    return this.addSlot(new Slot(slot.inventory, slot.getIndex(), slot.x, slot.y) {
      @Override
      public boolean canInsert(ItemStack stack) {
        return stack.isDamageable() || stack.isOf(Items.ENCHANTED_BOOK)
                 || stack.hasEnchantments() || stack.isIn(MUSIC_DISCS);
      }
    });
  }

  public boolean cloningDisk(){
    return input.getStack(0).isIn(MUSIC_DISCS)
             && input.getStack(1).isIn(MUSIC_DISCS)
             && canClone(input.getStack(0));
  }

  public boolean canClone(ItemStack stack){
    int mints = 0;
    if(stack.getOrCreateNbt().contains(EDITION_KEY)){
      mints += stack.getNbt().getInt(EDITION_KEY);
    }
    if(stack.getOrCreateNbt().contains(COPIES_KEY)){
      mints += stack.getNbt().getInt(COPIES_KEY);
    }
    return mints < player.getWorld().getGameRules().getInt(CLONE_LIMIT);
  }


}

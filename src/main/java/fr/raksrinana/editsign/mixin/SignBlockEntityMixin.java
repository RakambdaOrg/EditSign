package fr.raksrinana.editsign.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.SignBlock;
import net.minecraft.block.WallSignBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.SignItem;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SignBlockEntity.class)
public final class SignBlockEntityMixin{
	@Inject(method = "onActivate", at = @At("HEAD"))
	public void useOnBlock(PlayerEntity player, CallbackInfoReturnable<Boolean> callback){
		SignBlockEntity sign = (SignBlockEntity) (Object) this;
		if(player.isSneaking() && !isHoldingEditor(player)){
			sign.setEditable(true);
			if(sign.isEditable()){
				player.openEditSignScreen(sign);
			}
			else{
				player.sendSystemMessage(new TranslatableText("edit_sign.action.not_editable"), Util.NIL_UUID);
			}
		}
	}
	
	private static boolean isHoldingEditor(PlayerEntity player){
		for(ItemStack stack : player.getItemsHand()){
			if(stack.getItem() instanceof SignItem){
				return true;
			}
		}
		return false;
	}
}

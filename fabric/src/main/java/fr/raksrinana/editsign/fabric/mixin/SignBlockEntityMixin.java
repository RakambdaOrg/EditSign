package fr.raksrinana.editsign.fabric.mixin;

import fr.raksrinana.editsign.fabric.EditSign;
import fr.raksrinana.editsign.fabric.common.wrapper.HandWrapper;
import fr.raksrinana.editsign.fabric.common.wrapper.PlayerWrapper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SignBlockEntity.class)
public final class SignBlockEntityMixin{
	@Shadow
	private boolean isEditable;
	
	@Inject(method = "executeClickCommands", at = @At("HEAD"))
	public void useOnBlock(ServerPlayer serverPlayer, CallbackInfoReturnable<Boolean> callback){
		var itemStack = serverPlayer.getItemInHand(serverPlayer.getUsedItemHand());
		var item = itemStack.getItem();
		
		var isDye = item instanceof DyeItem;
		var isGlowInkSack = itemStack.is(Items.GLOW_INK_SAC);
		var isInkSack = itemStack.is(Items.INK_SAC);
		
		if(isDye || isGlowInkSack || isInkSack){
			return;
		}
		
		var wrappedPlayer = new PlayerWrapper(serverPlayer);
		var wrappedHand = new HandWrapper(serverPlayer.getUsedItemHand());
		if(EditSign.getMod().canPlayerEdit(wrappedPlayer, wrappedHand)){
			isEditable = true;
			SignBlockEntity sign = (SignBlockEntity) (Object) this;
			serverPlayer.openTextEdit(sign);
		}
	}
}

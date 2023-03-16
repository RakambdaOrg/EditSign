package fr.rakambda.editsign.fabric.mixin;

import fr.rakambda.editsign.fabric.EditSign;
import fr.rakambda.editsign.fabric.common.wrapper.HandWrapper;
import fr.rakambda.editsign.fabric.common.wrapper.PlayerWrapper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SignBlockEntity.class)
public abstract class SignBlockEntityMixin{
	@Shadow
	private boolean isEditable;
	
	@Inject(method = "executeClickCommands", at = @At("HEAD"), cancellable = true)
	public void useOnBlock(ServerPlayer serverPlayer, CallbackInfoReturnable<Boolean> callback){
		var wrappedPlayer = new PlayerWrapper(serverPlayer);
		var wrappedHand = new HandWrapper(serverPlayer.getUsedItemHand());
		
		if(!EditSign.getMod().canPlayerEdit(wrappedPlayer, wrappedHand)){
			return;
		}
		if(EditSign.getMod().playerHasSignModifier(wrappedPlayer, wrappedHand)){
			return;
		}
		
		isEditable = true;
		SignBlockEntity sign = (SignBlockEntity) (Object) this;
		serverPlayer.openTextEdit(sign);
		callback.setReturnValue(true);
	}
}

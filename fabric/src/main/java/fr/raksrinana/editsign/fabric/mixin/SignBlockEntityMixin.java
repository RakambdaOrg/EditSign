package fr.raksrinana.editsign.fabric.mixin;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import static fr.raksrinana.editsign.fabric.EditSignUtils.canPlayerEdit;

@Mixin(SignBlockEntity.class)
public final class SignBlockEntityMixin{
	@Shadow
	private boolean isEditable;
	
	@Inject(method = "executeClickCommands", at = @At("HEAD"))
	public void useOnBlock(Player player, CallbackInfoReturnable<Boolean> callback){
		if(canPlayerEdit(player)){
			isEditable = true;
			SignBlockEntity sign = (SignBlockEntity) (Object) this;
			player.openTextEdit(sign);
		}
	}
}

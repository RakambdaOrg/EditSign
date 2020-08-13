package fr.raksrinana.editsign.mixin;

import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SignBlockEntity.class)
public final class SignBlockEntityMixin{
	@Inject(method = "onActivate", at = @At("HEAD"))
	public void useOnBlock(PlayerEntity player, CallbackInfoReturnable<Boolean> callback){
		if(player.abilities.allowModifyWorld){
			SignBlockEntity sign = (SignBlockEntity) (Object) this;
			player.openEditSignScreen(sign);
		}
	}
}

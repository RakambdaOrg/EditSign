package fr.raksrinana.editsign.mixin;

import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Util;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SignBlockEntity.class)
public final class SignBlockEntityMixin{
	@Inject(method = "onActivate", at = @At("HEAD"))
	public void useOnBlock(PlayerEntity player, CallbackInfoReturnable<Boolean> callback){
		if(player instanceof ServerPlayerEntity){
			ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) player;
			GameMode gameMode = serverPlayerEntity.interactionManager.getGameMode();
			if(gameMode == GameMode.SURVIVAL || gameMode == GameMode.CREATIVE){
				SignBlockEntity sign = (SignBlockEntity) (Object) this;
				sign.setEditable(true);
				if(sign.isEditable()){
					player.openEditSignScreen(sign);
				}
				else{
					player.sendSystemMessage(new TranslatableText("edit_sign.action.not_editable"), Util.NIL_UUID);
				}
			}
		}
	}
}

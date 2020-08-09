package fr.raksrinana.editsign.mixin;

import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.gui.screen.ingame.SignEditScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SignItem;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import static net.minecraft.text.StringRenderable.plain;

@Mixin(SignEditScreen.class)
public final class SignEditScreenMixin{
	@Shadow
	@Final
	private String[] field_24285;
	@Shadow
	@Final
	private SignBlockEntity sign;
	
	@Inject(method = "init", at = @At("TAIL"))
	public void init(CallbackInfo callback){
		for(int i = 0; i < 4; i++){
			field_24285[i] = sign.getTextBeingEditedOnRow(i, text -> plain(text.getString())).getString();
		}
	}
}

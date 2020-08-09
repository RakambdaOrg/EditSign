package fr.raksrinana.editsign.mixin;

import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.gui.screen.ingame.SignEditScreen;
import net.minecraft.text.StringRenderable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.Optional;
import java.util.function.UnaryOperator;
import static net.minecraft.text.StringRenderable.plain;

@Mixin(SignEditScreen.class)
public final class SignEditScreenMixin{
	private static final UnaryOperator<StringRenderable> unaryOperator = text -> plain(text.getString());
	@Shadow
	@Final
	private String[] field_24285;
	@Shadow
	@Final
	private SignBlockEntity sign;
	
	@Inject(method = "init", at = @At("TAIL"))
	public void init(CallbackInfo callback){
		for(int i = 0; i < 4; i++){
			String line = Optional.ofNullable(sign.getTextBeingEditedOnRow(i, unaryOperator))
					.map(StringRenderable::getString)
					.orElse("");
			field_24285[i] = line;
		}
	}
}

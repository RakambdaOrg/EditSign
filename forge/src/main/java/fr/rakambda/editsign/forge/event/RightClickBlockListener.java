package fr.rakambda.editsign.forge.event;

import fr.rakambda.editsign.common.EditSignCommon;
import fr.rakambda.editsign.forge.EditSign;
import fr.rakambda.editsign.forge.common.wrapper.HandWrapper;
import fr.rakambda.editsign.forge.common.wrapper.PlayerWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import org.jetbrains.annotations.NotNull;

@Log4j2
@RequiredArgsConstructor
public final class RightClickBlockListener{
	private static final String[] IS_EDITABLE_FIELDS = {
			"f_59721_",
			"isEditable",
			};
	
	@NotNull
	private final EditSignCommon mod;
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event){
		var player = event.getEntity();
		
		var playerWrapper = new PlayerWrapper(event.getEntity());
		var handWrapper = new HandWrapper(event.getHand());
		
		if(!mod.canPlayerEdit(playerWrapper, handWrapper)){
			return;
		}
		if(mod.playerHasSignModifier(playerWrapper, handWrapper)){
			return;
		}
		
		var blockEntity = event.getLevel().getBlockEntity(event.getPos());
		if(blockEntity instanceof SignBlockEntity sign){
			setSignEditable(sign);
			if(sign.isEditable()){
				player.openTextEdit(sign);
			}
			else if(player instanceof ServerPlayer serverPlayer){
				serverPlayer.sendSystemMessage((Component) mod.translate(EditSign.MOD_ID + ".action.not_editable").getRaw(), false);
			}
		}
	}
	
	private static void setSignEditable(SignBlockEntity sign){
		for(var field : IS_EDITABLE_FIELDS){
			try{
				ObfuscationReflectionHelper.setPrivateValue(SignBlockEntity.class, sign, true, field);
				return;
			}
			catch(ObfuscationReflectionHelper.UnableToFindFieldException e){
				log.debug("Failed to get field {}", field);
			}
		}
		log.debug("Couldn't set sign editable");
	}
}

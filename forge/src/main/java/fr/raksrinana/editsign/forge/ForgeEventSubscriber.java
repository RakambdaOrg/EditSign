package fr.raksrinana.editsign.forge;

import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import static fr.raksrinana.editsign.forge.EditSignUtils.canPlayerEdit;

@Mod.EventBusSubscriber(modid = EditSign.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class ForgeEventSubscriber{
	private static final String[] IS_EDITABLE_FIELDS = {
			"f_59721_",
			"isEditable",
			};
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event){
		var player = event.getPlayer();
		if(canPlayerEdit(player, event.getItemStack())){
			var blockEntity = event.getWorld().getBlockEntity(event.getPos());
			if(blockEntity instanceof SignBlockEntity sign){
				setSignEditable(sign);
				if(sign.isEditable()){
					player.openTextEdit(sign);
				}
				else{
					if(player instanceof ServerPlayer serverPlayer){
						serverPlayer.m_215098_(MutableComponent.m_237204_(new TranslatableContents(EditSign.MOD_ID + ".action.not_editable")), ChatType.f_130598_); //Send it directly to player?
					}
				}
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
				EditSign.LOGGER.debug("Failed to get field {}", field);
			}
		}
		EditSign.LOGGER.debug("Couldn't set sign editable");
	}
}

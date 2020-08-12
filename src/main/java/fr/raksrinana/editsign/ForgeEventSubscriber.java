package fr.raksrinana.editsign;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Util;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.GameType;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EditSign.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class ForgeEventSubscriber{
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event){
		TileEntity tileentity = event.getWorld().getTileEntity(event.getPos());
		if(tileentity instanceof SignTileEntity){
			PlayerEntity player = event.getPlayer();
			if(!player.isCrouching() && player instanceof ServerPlayerEntity){
				ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) player;
				GameType gameMode = serverPlayerEntity.interactionManager.getGameType();
				if(gameMode == GameType.SURVIVAL || gameMode == GameType.CREATIVE){
					SignTileEntity sign = (SignTileEntity) tileentity;
					sign.setEditable(true);
					if(sign.getIsEditable()){
						player.openSignEditor(sign);
					}
					else{
						player.sendMessage(new TranslationTextComponent("edit_sign.action.not_editable"), Util.field_240973_b_);
					}
				}
			}
		}
	}
}

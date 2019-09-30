package fr.raksrinana.editsign;

import net.minecraft.block.BlockState;
import net.minecraft.block.StandingSignBlock;
import net.minecraft.block.WallSignBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SignItem;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

@Mod.EventBusSubscriber(modid = EditSign.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class ForgeEventSubscriber {
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event){
		if (event.getPlayer().isSneaking() && !isHoldingEditor(event.getPlayer())) {
			BlockPos pos = event.getPos();
			BlockState state = event.getWorld().getBlockState(pos);
			
			if (state.getBlock() instanceof StandingSignBlock || state.getBlock() instanceof WallSignBlock) {
				PlayerEntity player = event.getPlayer();
				TileEntity tileentity = event.getWorld().getTileEntity(pos);
				
				if (tileentity instanceof SignTileEntity) {
					SignTileEntity sign = (SignTileEntity) tileentity;
					sign.setPlayer(player);
					ObfuscationReflectionHelper.setPrivateValue(SignTileEntity.class, sign, true, "isEditable");
					player.openSignEditor(sign);
				}
			}
		}
	}
	
	private static boolean isHoldingEditor(PlayerEntity player) {
		for (ItemStack stack : player.getHeldEquipment()) {
			if (stack.getItem() instanceof SignItem) {
				return true;
			}
		}
		return false;
	}
}

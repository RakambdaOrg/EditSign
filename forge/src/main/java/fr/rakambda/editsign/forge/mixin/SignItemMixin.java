package fr.rakambda.editsign.forge.mixin;

import fr.rakambda.editsign.forge.EditSign;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SignItem.class)
public abstract class SignItemMixin extends BlockItem{
	public SignItemMixin(Block block, Properties properties){
		super(block, properties);
	}
	
	@Inject(method = "updateCustomBlockEntityTag", at = @At(value = "HEAD"), cancellable = true)
	public void updateCustomBlockEntityTag(BlockPos blockPos, Level level, Player player, ItemStack itemStack, BlockState blockState, CallbackInfoReturnable<Boolean> callback){
		if(!EditSign.getMod().getConfiguration().isOpenGuiOnPlace()){
			callback.setReturnValue(super.updateCustomBlockEntityTag(blockPos, level, player, itemStack, blockState));
		}
	}
}

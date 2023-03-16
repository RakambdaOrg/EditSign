package fr.rakambda.editsign.forge.common.wrapper;

import fr.rakambda.editsign.common.wrapper.IItem;
import fr.rakambda.editsign.common.wrapper.IItemStack;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class ItemStackWrapper implements IItemStack{
	@NotNull
	@Getter
	private final ItemStack raw;
	
	@Override
	@NotNull
	public IItem getItem(){
		return new ItemWrapper(raw.getItem());
	}
	
	@Override
	public boolean isGlowInk(){
		return raw.is(Items.GLOW_INK_SAC);
	}
	
	@Override
	public boolean isInkSac(){
		return raw.is(Items.INK_SAC);
	}
}

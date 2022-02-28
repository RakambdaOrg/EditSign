package fr.raksrinana.editsign.fabric.common.wrapper;

import fr.raksrinana.editsign.common.wrapper.IItem;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class ItemWrapper implements IItem{
	@NotNull
	@Getter
	private final Item raw;
	
	@Override
	public boolean isAir(){
		return Items.AIR.equals(raw);
	}
	
	@Override
	public boolean equals(Object obj){
		if(!(obj instanceof IItem item)){
			return false;
		}
		return raw.equals(item.getRaw());
	}
	
	@Override
	public int hashCode(){
		return raw.hashCode();
	}
}

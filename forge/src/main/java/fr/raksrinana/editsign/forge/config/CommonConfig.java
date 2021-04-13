package fr.raksrinana.editsign.forge.config;

import net.minecraft.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import java.util.Collection;
import static fr.raksrinana.editsign.forge.EditSignUtils.getAsItems;

public class CommonConfig{
	private static final String[] DESC_REQUIRED_ITEM_ID = {
			"Required item to edit signs"
	};
	private final ForgeConfigSpec.ConfigValue<String> requiredItemId;
	
	public CommonConfig(ForgeConfigSpec.Builder builder){
		builder.comment("Edit Sign configuration");
		requiredItemId = builder.comment(DESC_REQUIRED_ITEM_ID).define("required_item_id", "");
	}
	
	public void setRequiredItemId(String s){
		requiredItemId.set(s);
	}
	
	public String getRequiredItemStr(){
		return requiredItemId.get();
	}
	
	public Collection<Item> getRequiredItem(){
		return getAsItems(requiredItemId.get());
	}
}

package fr.raksrinana.editsign.config;

import me.shedaniel.clothconfig2.forge.api.ConfigBuilder;
import me.shedaniel.clothconfig2.forge.api.ConfigCategory;
import me.shedaniel.clothconfig2.forge.gui.entries.StringListEntry;
import net.minecraft.item.Item;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeConfigSpec;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import static fr.raksrinana.editsign.EditSignUtils.getAsItems;
import static fr.raksrinana.editsign.config.Config.getMinecraftItemIdCellError;

public class CommonConfig{
	private static final String[] DESC_REQUIRED_ITEM_ID = {
			"Required item to edit signs"
	};
	private final ForgeConfigSpec.ConfigValue<String> requiredItemId;
	
	public CommonConfig(ForgeConfigSpec.Builder builder){
		builder.comment("Edit Sign configuration");
		requiredItemId = builder.comment(DESC_REQUIRED_ITEM_ID).define("required_item_id", "");
	}
	
	@OnlyIn(Dist.CLIENT)
	public void fillConfigScreen(ConfigBuilder builder){
		StringListEntry reverseSneakingEntry = builder.entryBuilder()
				.startStrField(new TranslationTextComponent(getFieldName("requiredItemId")), requiredItemId.get())
				.setDefaultValue("")
				.setTooltip(getTooltips("requiredItemId", 4))
				.setSaveConsumer(requiredItemId::set)
				.setErrorSupplier(getMinecraftItemIdCellError())
				.build();
		
		ConfigCategory general = builder.getOrCreateCategory(new TranslationTextComponent("text.autoconfig.editsign.category.default"));
		general.addEntry(reverseSneakingEntry);
	}
	
	private String getFieldName(String fieldName){
		return "text.autoconfig.editsign.option." + fieldName;
	}
	
	private ITextComponent[] getTooltips(String fieldName, int count){
		String tooltipKey = getFieldName(fieldName) + ".@Tooltip";
		List<String> keys = new LinkedList<>();
		if(count <= 1){
			keys.add(tooltipKey);
		}
		else{
			for(int i = 0; i < count; i++){
				keys.add(tooltipKey + "[" + i + "]");
			}
		}
		return keys.stream()
				.map(TranslationTextComponent::new)
				.toArray(ITextComponent[]::new);
	}
	
	public Collection<Item> getRequiredItem(){
		return getAsItems(requiredItemId.get());
	}
}

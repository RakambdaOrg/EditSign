package fr.raksrinana.editsign.fabric.config;

import fr.raksrinana.editsign.fabric.config.validator.ItemId;
import fr.raksrinana.editsign.fabric.config.validator.Validators;
import lombok.Getter;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.Tooltip;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;
import net.minecraft.world.item.Item;
import java.util.Collection;
import static fr.raksrinana.editsign.fabric.EditSignUtils.getAsItems;

@Config(name = "editsign")
public class Configuration implements ConfigData{
	@Tooltip(count = 4)
	@Comment("Required item to edit signs")
	@ItemId(allowEmpty = true)
	@Getter
	public String requiredItemId = "";
	
	public static Configuration register(){
		return AutoConfig.register(Configuration.class, JanksonConfigSerializer::new).getConfig();
	}
	
	@Override
	public void validatePostLoad() throws ValidationException{
		Validators.runValidators(Configuration.class, this, "general");
	}
	
	public Collection<Item> getRequiredItem(){
		return getAsItems(requiredItemId);
	}
}

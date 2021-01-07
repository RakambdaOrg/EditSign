package fr.raksrinana.editsign.config.cloth;

import fr.raksrinana.editsign.config.CommonConfig;
import fr.raksrinana.editsign.config.Config;
import me.shedaniel.clothconfig2.forge.api.ConfigBuilder;
import me.shedaniel.clothconfig2.forge.api.ConfigCategory;
import me.shedaniel.clothconfig2.forge.gui.entries.StringListEntry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Pattern;

public class ClothConfigHook{
	private static final Pattern MINECRAFT_ID_PATTERN = Pattern.compile("#?[a-z0-9_.-]+:[a-z0-9/._-]+");
	
	public static Function<String, Optional<ITextComponent>> getMinecraftItemIdCellError(){
		return value -> {
			boolean valid;
			if(value == null || value.isEmpty()){
				valid = true;
			}
			else{
				valid = MINECRAFT_ID_PATTERN.matcher(value).matches();
			}
			
			if(!valid){
				return Optional.of(new TranslationTextComponent("text.autoconfig.editsign.error.invalidItemResourceLocation"));
			}
			return Optional.empty();
		};
	}
	
	public void load(){
		ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.CONFIGGUIFACTORY, () -> (mc, parent) -> {
			ConfigBuilder builder = ConfigBuilder.create()
					.setParentScreen(parent)
					.setTitle(new StringTextComponent("EditSign"));
			
			fillConfigScreen(builder);
			
			return builder.build();
		});
	}
	
	@OnlyIn(Dist.CLIENT)
	public void fillConfigScreen(ConfigBuilder builder){
		CommonConfig config = Config.COMMON;
		
		StringListEntry reverseSneakingEntry = builder.entryBuilder()
				.startStrField(new TranslationTextComponent(getFieldName("requiredItemId")), config.getRequiredItemStr())
				.setDefaultValue("")
				.setTooltip(getTooltips("requiredItemId", 4))
				.setSaveConsumer(config::setRequiredItemId)
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
}

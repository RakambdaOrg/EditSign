package fr.raksrinana.editsign.fabric.cloth;

import fr.raksrinana.editsign.common.EditSignCommon;
import fr.raksrinana.editsign.common.config.Configuration;
import fr.raksrinana.editsign.common.config.cloth.ClothHookBase;
import fr.raksrinana.editsign.common.wrapper.IComponent;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.Optional;
import java.util.function.Function;

public class ClothConfigHook extends ClothHookBase{
	public ClothConfigHook(@NotNull EditSignCommon mod){
		super(mod);
	}
	
	@NotNull
	public Function<Screen, Screen> load(){
		return (screen) -> {
			var builder = ConfigBuilder.create()
					.setParentScreen(screen)
					.setTitle(new TextComponent("FallingTree"));
			
			var configuration = getMod().getConfiguration();
			builder.setSavingRunnable(configuration::onUpdate);
			
			fillConfigScreen(builder, configuration);
			
			return builder.build();
		};
	}
	
	@Environment(EnvType.CLIENT)
	public void fillConfigScreen(@NotNull ConfigBuilder builder, @NotNull Configuration config){
		var requiredItemEntry = builder.entryBuilder()
				.startStrField(new TranslatableComponent(getFieldName(null, "requiredItemId")), config.getRequiredItemId())
				.setDefaultValue("")
				.setTooltip(getTooltips(null, "requiredItemId", 2))
				.setSaveConsumer(config::setRequiredItemId)
				.setErrorSupplier(map(getMinecraftItemIdCellError()))
				.build();
		
		var general = builder.getOrCreateCategory(new TranslatableComponent("text.autoconfig.fallingtree.category.default"));
		general.addEntry(requiredItemEntry);
	}
	
	@NotNull
	private Function<String, Optional<Component>> map(@NotNull Function<String, Optional<IComponent>> fct){
		return str -> fct.apply(str).map(IComponent::getRaw).map(Component.class::cast);
	}
	
	@NotNull
	protected Component[] getTooltips(@Nullable String category, @NotNull String fieldName, int count){
		return getTooltipsInternal(category, fieldName, count)
				.map(IComponent::getRaw)
				.map(Component.class::cast)
				.toArray(Component[]::new);
	}
}

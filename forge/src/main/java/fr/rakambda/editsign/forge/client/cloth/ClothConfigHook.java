package fr.rakambda.editsign.forge.client.cloth;

import fr.rakambda.editsign.common.EditSignCommon;
import fr.rakambda.editsign.common.config.Configuration;
import fr.rakambda.editsign.common.config.cloth.ClothHookBase;
import fr.rakambda.editsign.common.wrapper.IComponent;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.ModLoadingContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.Optional;
import java.util.function.Function;

public class ClothConfigHook extends ClothHookBase{
	public ClothConfigHook(@NotNull EditSignCommon mod){
		super(mod);
	}
	
	public void load(){
		ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory((minecraft, screen) -> {
			var builder = ConfigBuilder.create()
					.setParentScreen(screen)
					.setTitle(Component.literal("EditSign"));
			
			var configuration = getMod().getConfiguration();
			builder.setSavingRunnable(configuration::onUpdate);
			
			fillConfigScreen(builder, configuration);
			
			return builder.build();
		}));
	}
	
	@OnlyIn(Dist.CLIENT)
	public void fillConfigScreen(@NotNull ConfigBuilder builder, @NotNull Configuration config){
		var requiredItemEntry = builder.entryBuilder()
				.startStrField(translatable(getFieldName(null, "requiredItemId")), config.getRequiredItemId())
				.setDefaultValue("")
				.setTooltip(getTooltips(null, "requiredItemId", 4))
				.setSaveConsumer(config::setRequiredItemId)
				.setErrorSupplier(map(getMinecraftItemIdCellError()))
				.build();
		var openGuiOnPlaceEntry = builder.entryBuilder()
				.startBooleanToggle(translatable(getFieldName(null, "openGuiOnPlace")), config.isOpenGuiOnPlace())
				.setDefaultValue(true)
				.setTooltip(getTooltips(null, "openGuiOnPlace", 3))
				.setSaveConsumer(config::setOpenGuiOnPlace)
				.build();
		
		var general = builder.getOrCreateCategory(translatable("text.autoconfig.editsign.category.default"));
		general.addEntry(requiredItemEntry);
		general.addEntry(openGuiOnPlaceEntry);
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
	
	@NotNull
	private Component translatable(@NotNull String key){
		return (Component) getMod().translate(key).getRaw();
	}
}

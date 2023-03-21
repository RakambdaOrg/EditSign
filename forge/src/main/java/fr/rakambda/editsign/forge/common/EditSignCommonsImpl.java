package fr.rakambda.editsign.forge.common;

import fr.rakambda.editsign.common.EditSignCommon;
import fr.rakambda.editsign.common.wrapper.IComponent;
import fr.rakambda.editsign.common.wrapper.IItem;
import fr.rakambda.editsign.forge.common.wrapper.ComponentWrapper;
import fr.rakambda.editsign.forge.common.wrapper.ItemWrapper;
import fr.rakambda.editsign.forge.event.RightClickBlockListener;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.network.NetworkConstants;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.NotNull;
import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.stream.Stream;
import static java.util.stream.Stream.empty;

public class EditSignCommonsImpl extends EditSignCommon{
	@Override
	@NotNull
	public IComponent translate(@NotNull String key, Object... objects){
		return new ComponentWrapper(Component.translatable(key, objects));
	}
	
	@Nonnull
	public Stream<IItem> getItem(@NotNull String name){
		try{
			var isTag = name.startsWith("#");
			if(isTag){
				name = name.substring(1);
			}
			var resourceLocation = new ResourceLocation(name);
			if(isTag){
				var tag = TagKey.create(Registries.ITEM, resourceLocation);
				return getRegistryTagContent(ForgeRegistries.ITEMS, tag).map(ItemWrapper::new);
			}
			return getRegistryElement(ForgeRegistries.ITEMS, resourceLocation).stream().map(ItemWrapper::new);
		}
		catch(Exception e){
			return empty();
		}
	}
	
	@NotNull
	private static <T> Optional<T> getRegistryElement(@NotNull IForgeRegistry<T> registryKey, @NotNull ResourceLocation identifier){
		return registryKey.getHolder(identifier).map(Holder::value);
	}
	
	@NotNull
	private static <T> Stream<T> getRegistryTagContent(@NotNull IForgeRegistry<T> registry, @NotNull TagKey<T> tag){
		return registry.tags().getTag(tag).stream();
	}
	
	public void registerForge(@NotNull IEventBus eventBus){
		ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> new IExtensionPoint.DisplayTest(() -> NetworkConstants.IGNORESERVERONLY, (a, b) -> true));
		
		eventBus.register(new RightClickBlockListener(this));
	}
}

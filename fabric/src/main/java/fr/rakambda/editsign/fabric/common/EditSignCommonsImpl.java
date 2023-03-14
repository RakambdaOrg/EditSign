package fr.rakambda.editsign.fabric.common;

import fr.rakambda.editsign.common.EditSignCommon;
import fr.rakambda.editsign.common.wrapper.IComponent;
import fr.rakambda.editsign.common.wrapper.IItem;
import fr.rakambda.editsign.fabric.common.wrapper.ComponentWrapper;
import fr.rakambda.editsign.fabric.common.wrapper.ItemWrapper;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import org.jetbrains.annotations.NotNull;
import java.util.Optional;
import java.util.stream.Stream;
import static java.util.stream.Stream.empty;

public class EditSignCommonsImpl extends EditSignCommon{
	@Override
	@NotNull
	public IComponent translate(@NotNull String key, Object... objects){
		return new ComponentWrapper(MutableComponent.create(new TranslatableContents(key, null, objects)));
	}
	
	@Override
	@NotNull
	public Stream<IItem> getItem(@NotNull String name){
		try{
			var isTag = name.startsWith("#");
			if(isTag){
				name = name.substring(1);
			}
			var identifier = new ResourceLocation(name);
			if(isTag){
				var tag = TagKey.create(Registries.ITEM, identifier);
				return getRegistryTagContent(BuiltInRegistries.ITEM, tag).map(ItemWrapper::new);
			}
			return getRegistryElement(BuiltInRegistries.ITEM, identifier).stream().map(ItemWrapper::new);
		}
		catch(Exception e){
			return empty();
		}
	}
	
	@NotNull
	private <T> Optional<T> getRegistryElement(Registry<T> registryKey, ResourceLocation identifier){
		return registryKey.getOptional(identifier);
	}
	
	@NotNull
	private <T> Stream<T> getRegistryTagContent(@NotNull Registry<T> registry, @NotNull TagKey<T> tag){
		return registry.getTag(tag).stream()
				.flatMap(a -> a.stream().map(Holder::value));
	}
}

package fr.rakambda.editsign.forge;

import fr.rakambda.editsign.forge.config.Config;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.NotNull;
import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.Stream.empty;

public class EditSignUtils{
	public static boolean canPlayerEdit(Player player, ItemStack itemStack){
		return player.mayBuild() && !player.isCrouching() && !isHoldingDye(itemStack) && hasRightItem(itemStack);
	}
	
	private static boolean isHoldingDye(ItemStack itemStack){
		return itemStack.getItem() instanceof DyeItem;
	}
	
	private static boolean hasRightItem(ItemStack itemStack){
		var requiredItem = Config.COMMON.getRequiredItem();
		if(requiredItem.isEmpty()){
			return true;
		}
		
		var playerItem = itemStack.getItem();
		return requiredItem.stream().anyMatch(item -> item.equals(playerItem));
	}
	
	public static Set<Item> getAsItems(String name){
		return Stream.of(name)
				.filter(Objects::nonNull)
				.filter(val -> !val.isEmpty())
				.flatMap(EditSignUtils::getItem)
				.filter(Objects::nonNull)
				.collect(toSet());
	}
	
	@Nonnull
	public static Stream<Item> getItem(String name){
		try{
			var isTag = name.startsWith("#");
			if(isTag){
				name = name.substring(1);
			}
			var resourceLocation = new ResourceLocation(name);
			if(isTag){
				var tag = TagKey.create(Registries.ITEM, resourceLocation);
				return getRegistryTagContent(ForgeRegistries.ITEMS, tag);
			}
			return getRegistryElement(ForgeRegistries.ITEMS, resourceLocation).stream();
		}
		catch(Exception e){
			return empty();
		}
	}
	
	@NotNull
	private static <T> Optional<T> getRegistryElement(IForgeRegistry<T> registryKey, ResourceLocation identifier){
		return registryKey.getHolder(identifier).map(Holder::value);
	}
	
	@NotNull
	private static <T> Stream<T> getRegistryTagContent(@NotNull IForgeRegistry<T> registry, @NotNull TagKey<T> tag){
		return registry.tags().getTag(tag).stream();
	}
}

package fr.raksrinana.editsign.forge;

import fr.raksrinana.editsign.forge.config.Config;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.Stream.empty;
import static net.minecraftforge.registries.ForgeRegistries.ITEMS;

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
				var tag = TagKey.create(Registry.ITEM_REGISTRY, resourceLocation);
				return getRegistryTagContent(Registry.ITEM, tag);
			}
			return Stream.of(ITEMS.getValue(resourceLocation));
		}
		catch(Exception e){
			return empty();
		}
	}
	
	@NotNull
	private static <T> Stream<T> getRegistryTagContent(@NotNull Registry<T> registry, @NotNull TagKey<T> tag){
		return registry.getTag(tag).stream()
				.flatMap(a -> a.stream().map(Holder::value));
	}
}

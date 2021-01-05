package fr.raksrinana.editsign;

import fr.raksrinana.editsign.config.Config;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.Stream.empty;
import static net.minecraftforge.registries.ForgeRegistries.ITEMS;

public class EditSignUtils{
	public static boolean canPlayerEdit(PlayerEntity playerEntity, ItemStack itemStack){
		Collection<Item> requiredItem = Config.COMMON.getRequiredItem();
		if(!requiredItem.isEmpty()){
			Item playerItem = itemStack.getItem();
			if(requiredItem.stream().noneMatch(item -> item.equals(playerItem))){
				return false;
			}
		}
		return playerEntity.abilities.allowEdit;
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
			boolean isTag = name.startsWith("#");
			if(isTag){
				name = name.substring(1);
			}
			ResourceLocation resourceLocation = new ResourceLocation(name);
			if(isTag){
				return Optional.ofNullable(ItemTags.getCollection().get(resourceLocation))
						.map(ITag::getAllElements)
						.map(Collection::stream)
						.orElse(empty());
			}
			return Stream.of(ITEMS.getValue(resourceLocation));
		}
		catch(Exception e){
			return empty();
		}
	}
}

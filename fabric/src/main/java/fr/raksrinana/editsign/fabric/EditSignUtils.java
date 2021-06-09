package fr.raksrinana.editsign.fabric;

import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.Stream.empty;

public class EditSignUtils{
	public static boolean canPlayerEdit(Player playerEntity){
		var requiredItem = EditSign.config.getRequiredItem();
		if(!requiredItem.isEmpty()){
			var playerItem = playerEntity.getItemInHand(playerEntity.getUsedItemHand()).getItem();
			if(requiredItem.stream().noneMatch(item -> item.equals(playerItem))){
				return false;
			}
		}
		return playerEntity.mayBuild();
	}
	
	public static Set<Item> getAsItems(String name){
		return Stream.of(name)
				.filter(Objects::nonNull)
				.filter(val -> !val.isEmpty())
				.flatMap(EditSignUtils::getItem)
				.filter(Objects::nonNull)
				.collect(toSet());
	}
	
	public static Stream<Item> getItem(String name){
		try{
			var isTag = name.startsWith("#");
			if(isTag){
				name = name.substring(1);
			}
			var identifier = new ResourceLocation(name);
			if(isTag){
				return TagRegistry.item(identifier).getValues().stream();
			}
			return Stream.of(Registry.ITEM.get(identifier));
		}
		catch(Exception e){
			return empty();
		}
	}
}

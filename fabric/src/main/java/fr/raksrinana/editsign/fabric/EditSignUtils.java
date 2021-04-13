package fr.raksrinana.editsign.fabric;

import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.Stream.empty;
import static net.minecraft.util.registry.Registry.ITEM;

public class EditSignUtils{
	public static boolean canPlayerEdit(PlayerEntity playerEntity){
		Collection<Item> requiredItem = EditSign.config.getRequiredItem();
		if(!requiredItem.isEmpty()){
			Item playerItem = playerEntity.getStackInHand(playerEntity.getActiveHand()).getItem();
			if(requiredItem.stream().noneMatch(item -> item.equals(playerItem))){
				return false;
			}
		}
		return playerEntity.abilities.allowModifyWorld;
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
			boolean isTag = name.startsWith("#");
			if(isTag){
				name = name.substring(1);
			}
			Identifier identifier = new Identifier(name);
			if(isTag){
				return TagRegistry.item(identifier).values().stream();
			}
			return Stream.of(ITEM.get(identifier));
		}
		catch(Exception e){
			return empty();
		}
	}
}

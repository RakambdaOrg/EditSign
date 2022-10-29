package fr.rakambda.editsign.common;

import fr.rakambda.editsign.common.wrapper.IComponent;
import fr.rakambda.editsign.common.wrapper.IHand;
import fr.rakambda.editsign.common.wrapper.IItem;
import fr.rakambda.editsign.common.wrapper.IPlayer;
import fr.rakambda.editsign.common.config.Configuration;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import static java.util.stream.Collectors.toSet;

@RequiredArgsConstructor
@Getter
public abstract class EditSignCommon{
	private final Configuration configuration;
	
	public EditSignCommon(){
		configuration = Configuration.read();
	}
	
	@NotNull
	public abstract IComponent translate(@NotNull String key, Object... objects);
	
	public boolean canPlayerEdit(@NotNull IPlayer player, @NotNull IHand hand){
		var requiredItem = configuration.getRequiredItem(this);
		if(!requiredItem.isEmpty()){
			var playerItem = player.getHandItem(hand).getItem();
			if(requiredItem.stream().noneMatch(item -> item.equals(playerItem))){
				return false;
			}
		}
		return player.mayBuild();
	}
	
	public Set<IItem> getAsItems(String name){
		return Optional.ofNullable(name).stream()
				.filter(Objects::nonNull)
				.filter(val -> !val.isEmpty())
				.flatMap(this::getItem)
				.filter(Objects::nonNull)
				.filter(item -> !item.isAir())
				.collect(toSet());
	}
	
	@NotNull
	public abstract Stream<IItem> getItem(@NotNull String name);
}

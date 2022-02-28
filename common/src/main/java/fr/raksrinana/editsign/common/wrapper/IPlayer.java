package fr.raksrinana.editsign.common.wrapper;

import org.jetbrains.annotations.NotNull;

public interface IPlayer extends IWrapper{
	boolean mayBuild();
	
	@NotNull
	IItemStack getHandItem(@NotNull IHand hand);
}

package fr.raksrinana.editsign.common.wrapper;

import org.jetbrains.annotations.NotNull;

public interface IItemStack extends IWrapper{
	@NotNull
	IItem getItem();
}

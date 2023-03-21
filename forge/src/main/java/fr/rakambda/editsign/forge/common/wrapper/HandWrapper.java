package fr.rakambda.editsign.forge.common.wrapper;

import fr.rakambda.editsign.common.wrapper.IHand;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.world.InteractionHand;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class HandWrapper implements IHand{
	@NotNull
	@Getter
	private final InteractionHand raw;
}

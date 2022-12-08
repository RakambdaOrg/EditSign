package fr.rakambda.editsign.fabric;

import fr.rakambda.editsign.fabric.common.EditSignCommonsImpl;
import lombok.Getter;
import net.fabricmc.api.ModInitializer;

public class EditSign implements ModInitializer{
	@Getter
	private static final EditSignCommonsImpl mod = new EditSignCommonsImpl();
	
	@Override
	public void onInitialize(){
	}
}

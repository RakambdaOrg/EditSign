package fr.raksrinana.editsign;

import fr.raksrinana.editsign.config.Configuration;
import net.fabricmc.api.ModInitializer;

public class EditSign implements ModInitializer{
	public static Configuration config;
	
	@Override
	public void onInitialize(){
		config = Configuration.register();
	}
}

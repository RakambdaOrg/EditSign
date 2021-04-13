package fr.raksrinana.editsign.fabric;

import fr.raksrinana.editsign.fabric.config.Configuration;
import net.fabricmc.api.ModInitializer;

public class EditSign implements ModInitializer{
	public static Configuration config;
	
	@Override
	public void onInitialize(){
		config = Configuration.register();
	}
}

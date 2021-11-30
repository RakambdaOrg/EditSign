package fr.raksrinana.editsign.fabric;

import fr.raksrinana.editsign.fabric.config.Configuration;
import fr.raksrinana.editsign.fabric.config.validator.Validators;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import static java.util.stream.Collectors.toList;

public class EditSign implements ModInitializer{
	public static Configuration config;
	
	@Override
	public void onInitialize(){
		config = Configuration.register();
		if(FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT){
			registerGui();
		}
	}
	
	@Environment(EnvType.CLIENT)
	private static void registerGui(){
		var registry = AutoConfig.getGuiRegistry(Configuration.class);
		
		//noinspection unchecked
		Validators.RUNNERS.forEach(runner -> registry.registerAnnotationTransformer((guis, i13n, field, config, defaults, guiProvider) -> guis.stream()
				.peek(gui -> gui.setErrorSupplier(() -> runner.apply(gui.getValue(), field)))
				.collect(toList()), runner.getAnnotationClass()));
	}
}

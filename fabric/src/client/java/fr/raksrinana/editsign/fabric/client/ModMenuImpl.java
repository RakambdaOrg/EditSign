package fr.raksrinana.editsign.fabric.client;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import fr.raksrinana.editsign.common.EditSignCommon;
import fr.raksrinana.editsign.fabric.EditSign;
import fr.raksrinana.editsign.fabric.client.cloth.ClothConfigHook;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.lang.reflect.InvocationTargetException;

public class ModMenuImpl implements ModMenuApi{
	private static final Logger log = LogManager.getLogger(ModMenuImpl.class);
	
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory(){
		if(FabricLoader.getInstance().isModLoaded("cloth-config")){
			return (screen) -> {
				try{
					return Class.forName("fr.raksrinana.editsign.fabric.client.cloth.ClothConfigHook")
							.asSubclass(ClothConfigHook.class)
							.getConstructor(EditSignCommon.class)
							.newInstance(EditSign.getMod())
							.load()
							.apply(screen);
				}
				catch(ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e){
					log.error("Failed to hook into ClothConfig", e);
				}
				return null;
			};
		}
		return screen -> null;
	}
}

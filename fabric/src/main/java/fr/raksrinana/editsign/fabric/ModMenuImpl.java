package fr.raksrinana.editsign.fabric;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import fr.raksrinana.editsign.common.EditSignCommon;
import fr.raksrinana.editsign.fabric.cloth.ClothConfigHook;
import lombok.extern.log4j.Log4j2;
import net.fabricmc.loader.api.FabricLoader;
import java.lang.reflect.InvocationTargetException;

@Log4j2
public class ModMenuImpl implements ModMenuApi{
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory(){
		if(FabricLoader.getInstance().isModLoaded("cloth-config")){
			return (screen) -> {
				try{
					return Class.forName("fr.raksrinana.editsign.fabric.cloth.ClothConfigHook")
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

package fr.raksrinana.editsign.forge;

import fr.raksrinana.editsign.forge.config.Config;
import fr.raksrinana.editsign.forge.config.cloth.ClothConfigHook;
import net.minecraftforge.fml.IExtensionPoint.DisplayTest;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.network.NetworkConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.lang.reflect.InvocationTargetException;

@Mod(EditSign.MOD_ID)
public class EditSign{
	public static final String MOD_ID = "editsign";
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
	
	public EditSign() throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException{
		ModLoadingContext.get().registerExtensionPoint(DisplayTest.class, () -> new DisplayTest(() -> NetworkConstants.IGNORESERVERONLY, (a, b) -> true));
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_SPEC);
		
		if(ModList.get().isLoaded("cloth_config"))
		{
			Class.forName("fr.raksrinana.editsign.forge.config.cloth.ClothConfigHook")
					.asSubclass(ClothConfigHook.class)
					.getConstructor()
					.newInstance()
					.load();
		}
	}
}

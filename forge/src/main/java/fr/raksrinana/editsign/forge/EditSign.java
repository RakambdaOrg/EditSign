package fr.raksrinana.editsign.forge;

import fr.raksrinana.editsign.forge.config.Config;
import fr.raksrinana.editsign.forge.config.cloth.ClothConfigHook;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.network.FMLNetworkConstants;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(EditSign.MOD_ID)
public class EditSign{
	public static final String MOD_ID = "editsign";
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
	
	public EditSign() throws ClassNotFoundException, IllegalAccessException, InstantiationException{
		ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.DISPLAYTEST, () -> Pair.of(() -> FMLNetworkConstants.IGNORESERVERONLY, (a, b) -> true));
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_SPEC);
		
		if(ModList.get().isLoaded("cloth-config"))
		{
			Class.forName("fr.raksrinana.editsign.forge.config.cloth.ClothConfigHook")
					.asSubclass(ClothConfigHook.class)
					.newInstance()
					.load();
		}
	}
}

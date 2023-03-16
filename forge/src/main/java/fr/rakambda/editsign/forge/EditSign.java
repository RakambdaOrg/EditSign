package fr.rakambda.editsign.forge;

import fr.rakambda.editsign.common.EditSignCommon;
import fr.rakambda.editsign.forge.common.EditSignCommonsImpl;
import fr.rakambda.editsign.forge.config.Config;
import fr.rakambda.editsign.forge.config.cloth.ClothConfigHook;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import net.minecraftforge.fml.IExtensionPoint.DisplayTest;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.network.NetworkConstants;
import java.lang.reflect.InvocationTargetException;

@Log4j2
@Mod(EditSign.MOD_ID)
public class EditSign{
	public static final String MOD_ID = "editsign";
	@Getter
	private static final EditSignCommonsImpl mod = new EditSignCommonsImpl();
	
	public EditSign(){
		ModLoadingContext.get().registerExtensionPoint(DisplayTest.class, () -> new DisplayTest(() -> NetworkConstants.IGNORESERVERONLY, (a, b) -> true));
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_SPEC);
		
		if(ModList.get().isLoaded("cloth_config")){
			try{
				Class.forName("fr.rakambda.fallingtree.forge.client.cloth.ClothConfigHook")
						.asSubclass(ClothConfigHook.class)
						.getConstructor(EditSignCommon.class)
						.newInstance(mod)
						.load();
			}
			catch(ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e){
				log.error("Failed to hook into ClothConfig", e);
			}
		}
	}
}

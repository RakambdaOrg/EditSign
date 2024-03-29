package fr.rakambda.editsign.forge;

import fr.rakambda.editsign.common.EditSignCommon;
import fr.rakambda.editsign.forge.common.EditSignCommonsImpl;
import fr.rakambda.editsign.forge.client.cloth.ClothConfigHook;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import java.lang.reflect.InvocationTargetException;

@Log4j2
@Mod(EditSign.MOD_ID)
public class EditSign{
	public static final String MOD_ID = "editsign";
	@Getter
	private static final EditSignCommonsImpl mod = new EditSignCommonsImpl();
	
	public EditSign(){
		if(ModList.get().isLoaded("cloth_config")){
			try{
				Class.forName("fr.rakambda.editsign.forge.client.cloth.ClothConfigHook")
						.asSubclass(ClothConfigHook.class)
						.getConstructor(EditSignCommon.class)
						.newInstance(mod)
						.load();
			}
			catch(ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e){
				log.error("Failed to hook into ClothConfig", e);
			}
		}
		
		mod.registerForge(MinecraftForge.EVENT_BUS);
	}
}

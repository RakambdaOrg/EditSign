package fr.raksrinana.editsign;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Optional;

@Mod(EditSign.MOD_ID)
public class EditSign{
	public static final String MOD_ID = "edit_sign";
	public static final String MOD_NAME = "Edit Sign";
	public static final String VERSION = "2.0.0";
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
	
	public EditSign(){
	}
	
	public static String getVersion(){
		Optional<? extends ModContainer> o = ModList.get().getModContainerById(MOD_ID);
		if(o.isPresent()){
			return o.get().getModInfo().getVersion().toString();
		}
		return "NONE";
	}
	
	public static boolean isDevBuild(){
		return "NONE".equals(getVersion());
	}
	
	public static ResourceLocation getId(String path){
		return new ResourceLocation(MOD_ID, path);
	}
}

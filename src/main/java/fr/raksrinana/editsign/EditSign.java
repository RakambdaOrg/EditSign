package fr.raksrinana.editsign;

import fr.raksrinana.editsign.config.Config;
import me.shedaniel.clothconfig2.forge.api.ConfigBuilder;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.ExtensionPoint;
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
	
	public EditSign(){
		ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.DISPLAYTEST, () -> Pair.of(() -> FMLNetworkConstants.IGNORESERVERONLY, (a, b) -> true));
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_SPEC);
		ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.CONFIGGUIFACTORY, () -> (mc, parent) -> {
			ConfigBuilder builder = ConfigBuilder.create()
					.setParentScreen(parent)
					.setTitle(new StringTextComponent("EditSign"));
			
			Config.COMMON.fillConfigScreen(builder);
			
			return builder.build();
		});
	}
}

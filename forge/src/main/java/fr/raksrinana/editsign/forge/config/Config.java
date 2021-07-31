package fr.raksrinana.editsign.forge.config;

import fr.raksrinana.editsign.forge.EditSign;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EditSign.MOD_ID)
public class Config{
	public static final CommonConfig COMMON;
	public static final ForgeConfigSpec COMMON_SPEC;
	
	static{
		var commonPair = new ForgeConfigSpec.Builder().configure(CommonConfig::new);
		COMMON = commonPair.getLeft();
		COMMON_SPEC = commonPair.getRight();
	}
}

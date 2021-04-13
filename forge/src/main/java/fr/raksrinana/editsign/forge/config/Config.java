package fr.raksrinana.editsign.forge.config;

import fr.raksrinana.editsign.forge.EditSign;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.tuple.Pair;

@Mod.EventBusSubscriber(modid = EditSign.MOD_ID)
public class Config{
	public static final CommonConfig COMMON;
	public static final ForgeConfigSpec COMMON_SPEC;
	
	static{
		Pair<CommonConfig, ForgeConfigSpec> commonPair = new ForgeConfigSpec.Builder().configure(CommonConfig::new);
		COMMON = commonPair.getLeft();
		COMMON_SPEC = commonPair.getRight();
	}
}

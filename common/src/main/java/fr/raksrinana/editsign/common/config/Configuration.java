package fr.raksrinana.editsign.common.config;

import com.google.gson.annotations.Expose;
import fr.raksrinana.editsign.common.EditSignCommon;
import fr.raksrinana.editsign.common.wrapper.IItem;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Data
@Log4j2
public class Configuration{
	@Expose
	@Getter
	public String requiredItemId = "";
	
	public Collection<IItem> getRequiredItem(@NotNull EditSignCommon mod){
		return mod.getAsItems(getRequiredItemId());
	}
	
	public static Configuration read() throws RuntimeException{
		var path = getConfigPath();
		try{
			return ConfigLoader.loadConfig(new Configuration(), Configuration.class, path);
		}
		catch(IOException e){
			log.error("Failed to get FallingTree configuration from {}, using default", path, e);
			return new Configuration();
		}
	}
	
	public void onUpdate(){
		var path = getConfigPath();
		try{
			ConfigLoader.saveConfig(this, path);
		}
		catch(IOException e){
			log.error("Failed to saved FallingTree configuration to {}", path, e);
		}
	}
	
	private static Path getConfigPath(){
		return Paths.get(".").resolve("config").resolve("editsign.json");
	}
}

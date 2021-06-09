package fr.raksrinana.editsign.fabric.config.validator;

import me.shedaniel.autoconfig.ConfigData;
import java.util.Collections;
import java.util.List;

public class Validators{
	public static final ItemIdRunner ITEM_ID_RUNNER = new ItemIdRunner();
	public static final List<ValidatorRunner<?>> RUNNERS = Collections.singletonList(ITEM_ID_RUNNER);
	
	private Validators(){}
	
	public static <T> void runValidators(Class<T> categoryClass, T category, String categoryName) throws ConfigData.ValidationException{
		try{
			for(var field : categoryClass.getDeclaredFields()){
				for(var validator : RUNNERS){
					if(!validator.validateIfAnnotated(field, category)){
						throw new ConfigData.ValidationException("EditSign config field " + categoryName + "." + field.getName() + " is invalid");
					}
				}
			}
		}
		catch(ReflectiveOperationException | RuntimeException e){
			throw new ConfigData.ValidationException(e);
		}
	}
}

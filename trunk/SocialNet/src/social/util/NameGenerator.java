package social.util;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public class NameGenerator {
	
		
	public static String generateNewPersonName() throws IOException{
			Random random = new Random();
			Names name = Names.getInstance();
			List<String>names = name.getFirstNames();
			return names.get(random.nextInt(names.size()-1));
			
		
	}
		
}

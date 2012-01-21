package social.util;

public class NameGenerator {
	
	//TODO do poprawy jakis plik czy cos!

	public static String generateNewPersonName(){
		
		return "Ala ma kota" + (System.nanoTime() %1000000);
		
	}
	
	public static String generateNewIdeaName(){
		return "Komunizm jest zły " + (System.nanoTime() %1000000);
	}
	
}

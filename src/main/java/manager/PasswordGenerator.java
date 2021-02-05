package manager;
import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;
          
public class PasswordGenerator {

    private static final String[] letters = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
    private static final String[] numbers = {"1","2","3","4","5","6","7","8","9","0"};
    private static final String[] symbols = {"!","@","#","$","%","^","&","*","(",")","-","_","=","+","{","}","[","]","|","\\",";",":","\"","'",",",".","<",">","?","/"};


    private String randomChar(ArrayList<String> list) {
	Random generator = new Random();
	boolean isUpperCase = generator.nextBoolean();
	if(isUpperCase) {
	    return list.get(generator.nextInt(list.size())).toUpperCase();
	}
	return list.get(generator.nextInt(list.size()));
    }

    public String generatePassword(boolean hasNumbers, boolean hasSymbols, int passwordLength) {
	ArrayList<String> characters = new ArrayList<>();
	Collections.addAll(characters,letters);
	String password = "";
	if(hasNumbers) {
	    Collections.addAll(characters,numbers);
	}
	if(hasSymbols) {
	    Collections.addAll(characters,symbols);
	}
	for(int i=0; i<passwordLength; i++) {
	    password += randomChar(characters);
	}
	return password;
    }

}

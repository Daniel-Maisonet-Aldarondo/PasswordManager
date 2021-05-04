package util;

public class Cypher {

    public static String cypher(String text) {
        char[] characters = text.toCharArray();
        String newText = "";
        for(char c : characters) {
            c += 5;
            newText += c;
        }
        return newText;
    }

    public static String decrypt(String text) {
        char[] characters = text.toCharArray();
        String newText = "";
        for(char c: characters) {
            c -= 5;
            newText += c;
        }
        return newText;
    }

    
}

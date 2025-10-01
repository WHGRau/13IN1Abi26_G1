package gui;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Beschreiben Sie hier die Klasse Helper.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public abstract class Helper
{
    public static int tryParseInt(String input) {
        try {
            return Integer.parseInt(input);
        }
        catch(Exception ex) {
            System.out.println("Kann '" + input + "' nicht zu Integer parsen!");
            return -2;
        }
    }
    
    public static boolean tryParseBool(String input) {
        switch(input) {
            case "0":
                return false;
            case "1":
                return true;
            default:
                throw new IllegalArgumentException("'" + input + "' kann nicht zu boolean geparst werden.");
        }
    }
}
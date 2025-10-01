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
     // SHA-3 256, source: https://mojoauth.com/hashing/sha3-256-in-java/
    public static String toSha256(String input) {
        try {
            // Create a SHA3-256 MessageDigest instance
            MessageDigest digest = MessageDigest.getInstance("SHA3-256");
            
            // Perform hashing
            byte[] hashBytes = digest.digest(input.getBytes());
            
            // Convert bytes to hex format
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            
            // Print the resulting hash
            //System.out.println("SHA3-256 Hash: " + hexString.toString());
            
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        
        return "0"; // Konnte kein hash ermitteln, leeres Hash 0 als Fehlerwert
    }
    
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
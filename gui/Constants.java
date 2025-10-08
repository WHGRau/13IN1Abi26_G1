package gui;


/**
 * Beinhält statische Konstanten wie Zeichenlimits
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public abstract class Constants
{
    // Limits
    // Allgemein
    public static int dateMaxLength = 10; // 2025-10-06
    public static int dateTimeMaxLength = 19; // 2025-10-06 19:51:43
    public static int fullDateTimeMaxLength = 23; // 2025-10-06 19:51:43.000
    
    // User
    public static int benutzernameMaxLength = 20;
    public static int passwortMaxLength = 99;
    public static int nameMaxLength = 20;
    public static int vornameMaxLength = 20;
    
    // Standort
    public static int ortMaxLength = 50;
    public static int straßeMaxLength = 50;
    
    // Auto
    public static int markeMaxLength = 20;
    public static int modellMaxLength = 45;
    public static int kategorieMaxLength = 20;
    public static int kennzeichenMaxLength = 12;
}
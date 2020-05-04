package helpers;

public class StringManipulation {
    public static String convertStringToUnderscores(String s) {
        // Replaces all underscores to underscore and space '_ '
        // Extra space is for readability purposes and to distinguish different underscores
        return s.replaceAll("[_]", "_ ");
    }

    public static String charArrayToString(char[] arr) {
        String s = "";
        for (char c : arr) {
            s += c;
        }
        return s;
    }

    public static char[] parseCommaSeparatedValue(String s) {
        s = s.replaceAll(",", "").replaceAll("\\s+", ""); //reference: https://programming.guide/java/remove-trailing-comma-from-comma-separated-string.html
        char[] parsedArray = new char[s.length()];
        for (int pos = 0; pos < s.length(); pos++) {
            parsedArray[pos] = s.charAt(pos); //adds characters in s to indexes in parsedArray
        }
        return parsedArray;
    }
}

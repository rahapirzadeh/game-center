package helpers;

/**
* This program contains the methods for
*     converting string to underscores, chars to strings, and letters seperated by commas.
*/
public class StringManipulation {

  /**
  * Replaces all underscores to underscore and space '_ '
  * Extra space is for readability purposes and to distinguish different underscores.
  * @param s
  *        the inputed player choice
  */
  public static String convertStringToUnderscores(String s) {
    return s.replaceAll("[_]", "_ ");
  }

  /**
  * method to return a string when user inputs.
  * @param arr
  *        array that turns into string
  */
  public static String charArrayToString(char[] arr) {
    String s = "";
    for (char c : arr) {
      s += c;
    }
    return s;
  }

  /**
  * method seperates letters from user input when seperated with commas.
  * @param s
  *        string that is seperated by commas 
  */
  public static char[] parseCommaSeparatedValue(String s) {
    s = s.replaceAll(",", "").replaceAll("\\s+", ""); //reference: https://programming.guide/java/remove-trailing-comma-from-comma-separated-string.html
    char[] parsedArray = new char[s.length()];
    for (int pos = 0; pos < s.length(); pos++) {
      parsedArray[pos] = s.charAt(pos); //adds characters in s to indexes in parsedArray
    }
    return parsedArray;
  }
}

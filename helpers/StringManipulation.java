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
  *        the inputted player choice
  * @return {@code s} with all letters converted to underscores
  */
  public static String convertStringToUnderscores(String s) {
    return s.replaceAll("[_]", "_ ");
  }

  /**
  * method to return a string when user inputs.
  * @param arr
  *        array that turns into string
  * @return string generated from parsing character array
  */
  public static String charArrayToString(char[] arr) {
    String s = "";
    for (char c : arr) {
      s += c;
    }
    return s;
  }

  /**
  * method separates letters from user input when separated with commas.
  * @param s
  *        string that is separated by commas
   * @return  the character array generated from parsing {@code s}
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

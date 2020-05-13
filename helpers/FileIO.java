package helpers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
* This program reads from a file.
*/
public class FileIO {

  /**
  * Method to read from a file.
  * @param fileName
  *        the file name
  *     If file is not found, then FileNotFoundException
  */
  public static void readFromFile(String fileName) {
    String line = "";
    try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
      while ((line = br.readLine()) != null) {
        System.out.println(line);
      }
      br.close();
    } catch (FileNotFoundException e) {
      System.out.println("File not found; an error occurred.");
      e.printStackTrace();
    } catch (IOException e) {
      System.out.println("IOException occurred.");
      e.printStackTrace();
    }
  }

  /**
  * Method to get a line from a file.
  *
  * <p>@param fileName
  *        the file name
  * @param lineIndex
  *        the line number
  */
  public static String getLineFromFile(String fileName, int lineIndex) {
    String line = "";
    String data = "";
    try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
      int lineCounter = 0;
      while ((line = br.readLine()) != null) {
        if (lineCounter == lineIndex) {
          data = line;
          return data;
        }
        lineCounter++;
      }
      br.close();
    } catch (FileNotFoundException e) {
      System.out.println("File not found; an error occurred.");
      e.printStackTrace();
    } catch (IOException e) {
      System.out.println("IOException occurred.");
      e.printStackTrace();
    }
    return data;
  }
}

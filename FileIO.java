import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileIO {
  public static void readFromFile(String fileName) {
    String line = "";
    try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
      while ((line = br.readLine())!= null) {
        System.out.println(line);
      }
      br.close();
    } catch (FileNotFoundException e) {
      System.out.println("File not found; an error occurred.");
      e.printStackTrace();
    } catch (IOException e) {
      System.out.println("IOException occured.");
      e.printStackTrace();
    }
  }

  public static String getLineFromFile (String fileName, int lineIndex) {
    String line = "";
    String data = "";
    try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
      int lineCounter = 0;
      while ((line = br.readLine()) != null) {
        if(lineCounter == lineIndex) {
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
      System.out.println("IOException occured.");
      e.printStackTrace();
    }
    return data;
  }
}

import java.io.File;
import java.io.FileNotFoundException;
import org.junit.*;

public class MarkovTest {
   int k = 2;
   int length = 2;
   String source = "/Users/johncook/Google Drive/COMP 2210/Assignment 5/LM3.txt";
   File sourceFile = new File(source);
   String resultName = "MonkeyOutput";

   @Test public void nextCharTest() throws FileNotFoundException {
      //LanguageModeler langMod = new LanguageModeler(length, sourceFile);
      //langMod.fillTable();
      //langMod.nextChar("b,");
   }
   
   @Test public void probabilityTest() throws FileNotFoundException {
      LanguageModeler langMod = new LanguageModeler(length, sourceFile);
      //langMod.fillTable();
      String result = "";
      int i = 0;
      while (i < 20) {
         char one = langMod.nextChar("ab");
         i++;
         result += Character.toString(one);
      }
      System.out.print(result);
   }
	
}

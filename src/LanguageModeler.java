import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

/**
 * Creates an order K Markov model for a text sample.
 *
 */
public class LanguageModeler {
   int length;
   String words;
   HashMap<String, String> table = new HashMap<String, String>(17);
   char[] ca;
	
    // create an order K model for text
   public LanguageModeler(int K, File text) throws FileNotFoundException {
      length = K;
      words = fileToString(text);
      ca = words.toCharArray();
      // load the hash table here
      fillTable();
   }
   
    // create an order K model for text
   public LanguageModeler(int K, String text) {
      length = K;
      words = text;
      ca = words.toCharArray();
      // load the hash table here
      fillTable();
   }
   
    // return the first K characters of the sample text
   public String firstSeed() {
      String result = words.substring(0, length);
      return result;
   }
   
    // return K consecutive characters from the sample text,
    // selected uniformly at random
   public String randomSeed() {
      Random random = new Random();
      int start = random.nextInt(words.length() - length);
      String result = words.substring(start, start + length);
      return result;
   }
   
    // return a character that follows seed in the sample text,
    // selected according to the probability distribution of all
    // characters that follow seed in the sample text
   public char nextChar(String seed) {
      String resultStr = (String) table.get(seed);
   //      if (resultStr == null) {
   //         return ' ';
   //      }
   //      else {
      Random rand = new Random();
      char result = resultStr.charAt(rand.nextInt(resultStr.length()));
      return result;
   }
//   }
   
   // reads file to string
   private String fileToString(File input) throws FileNotFoundException {
      BufferedReader br = new BufferedReader(new FileReader(input));
      Scanner scan = new Scanner(br);
      String result = "";
      while (scan.hasNext()) {
         result += scan.nextLine();
      }
      scan.close();
      return result;
   }
   
   // fill hashtable with word combinations of k length
   public void fillTable() {
      int i = 0;
      while (i < (words.length() - length)) {
         String result = "";
         for (int j = 0; j < length; j++) {
            result += ca[i];
            i++;
         }
         String nextChar = Character.toString(ca[i]);
         if (table.containsKey(result)) {
            table.put(result, table.get(result) + nextChar);
         }
         else {
            table.put(result, nextChar);
         }
         i -= (length - 1);
      }
   }
   
   // gets total length of text
   public int textLength() {
      return words.length();
   }
 
}
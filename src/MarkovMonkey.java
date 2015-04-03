import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Collection;


/**
 * MarkovMonkey.java. Produces output text from an order K Markov model
 * of provided sample text.
 */
public class MarkovMonkey {
 
   public static void main(String[] args) throws IOException {
      String[] input = args;
      if (input.length != 4) {   // close if command line arguments are not correct
         System.out.println("Incorrect command line input. Terminating...");
         System.out.close();
         System.exit(1);
      }
      int k = Integer.parseInt(input[0]);
      int length = Integer.parseInt(input[1]);
      String source = input[2];
      File sourceFile = new File(source);
      String resultName = input[3];
      LanguageModeler langMod = new LanguageModeler(k, sourceFile);
      if (k < 0 || length < 0) {   // close if k or length is negative
         System.out.print("K or length less than zero. Terminating...");
         System.out.close();
         System.exit(1);
      }
      if (!(fileExists(sourceFile, k, langMod.words))) {   // close if source file does not exist or is shorter than k
         System.out.print("File does not exist or is not long enough. Terminating...");
         System.out.close();
         System.exit(1);
      }
      //LanguageModeler langMod = new LanguageModeler(k, sourceFile);
      Collection coll = langMod.table.values();   // for debug
      String seed = langMod.randomSeed();
      String result = seed;
   // do Markov levels here
      try {
         while (result.length() < length) {
            while (!(langMod.table.containsKey(seed))) {
               seed = langMod.randomSeed();
               //result += seed;
            }
            String temp = Character.toString(langMod.nextChar(seed));
            result += temp;
            seed += temp;    		 
            seed = seed.substring(1, seed.length());
         }
      }
      catch (NullPointerException e) {
         System.out.print("stop");
      }
      // create result string VVVVV
      createFile(resultName, result);
   }
   
   /**
    * Determines whether source file exists.
    * 
    * @param file is source of file in command line args
    * @param k is k in command line args
    * @return true if file exists and is longer than k
    */
   public static boolean fileExists(File file, int k, String source) {
      int i = 0;
      if (file.exists()) {
         i++;
         //return true;
      }
      if (source.length() > k) {
         i++;
      }
      if (i == 2) {
         return true;
      }
      else {
         return false;
      }
   }
   
   /**
    * Creates file and writes result.
    * 
    * @param fileName is resultName from command line args
    * @param result is the result from main
    * @throws IOException
    */
   public static void createFile(String fileName, String result) throws IOException {
      Writer writer = null;
      try {
         writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("outputFile.txt"), "utf-8"));
         writer.write(result);
      } 
      catch (IOException ex) {
        // report
      } 
      finally {
         try {writer.close();} 
         catch (Exception ex) {}
      }
   }
}
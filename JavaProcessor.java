import java.io.*;

public class JavaProcessor {
    public static void main(String[] args) {
        File file = new File(args[0]);
        BufferedReader reader = null;
        PrintWriter writer = null;

        try {
            writer = new PrintWriter("Monitor.java", "UTF-8");
            reader = new BufferedReader(new FileReader(file));
            String text = null;

            while ((text = reader.readLine()) != null) {
//need to cycle through until monitor class
//then add in additional stuff into the function
//to satisfy monitor functionality
                System.out.println(text);
                writer.println(text);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
            }
        }
    }
}
import java.io.*;
import java.util.regex.*;

public class Log_Parser {
    public static void main(String[] args) {
        String inputFile = "log.txt";
        String outputFile = "output.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             PrintWriter writer = new PrintWriter(new FileWriter(outputFile))) {

            // Write table header
            // writer.println("Phone Number\tCDAC Valid\tCDYNE Valid\tCDYNE Total Time\tCDAC Total Time");
            writer.println("Phone Number,CDAC Valid,CDYNE Valid,CDYNE Total Time,CDAC Total Time");

            String line;
            String phoneNumber = "";
            String cdacValid = "";
            String cdyneValid = "";
            String cdacTime = "";
            String cdyneTime = "";

            while ((line = reader.readLine()) != null) {
                // Extract phone number
                Matcher phoneMatcher = Pattern.compile("Using CDYNE service for phone number validation (\\d+)").matcher(line);
                if (phoneMatcher.find()) {
                    phoneNumber = phoneMatcher.group(1);
                }

                // Extract CDAC validity
                Matcher cdacMatcher = Pattern.compile("CDAC Gateway Response, is phoneNumber valid : (\\w+)").matcher(line);
                if (cdacMatcher.find()) {
                    cdacValid = cdacMatcher.group(1);
                }

                // Extract CDYNE validity
                Matcher cdyneMatcher = Pattern.compile("CDYNE Gateway Response, is phoneNumber valid : (\\w+)").matcher(line);
                if (cdyneMatcher.find()) {
                    cdyneValid = cdyneMatcher.group(1);
            
                }

                Matcher cdyneTimeMatcher = Pattern.compile("CDYNE total time taken : (\\w+)").matcher(line);
                if(cdyneTimeMatcher.find()) {
                    cdyneTime = cdyneTimeMatcher.group(1);
                }

                Matcher cdacTimeMatcher = Pattern.compile("CDAC total time taken : (\\w+)").matcher(line);
                if(cdacTimeMatcher.find()) {
                    cdacTime = cdacTimeMatcher.group(1);
                }

                

                // If we have all three pieces of information, write to output and reset
                if (!phoneNumber.isEmpty() && !cdacValid.isEmpty() && !cdyneValid.isEmpty()) {
                    // writer.println(phoneNumber + "\t\t" + cdacValid + "\t\t" + cdyneValid + "\t\t\t" + cdyneTime +"s"+ "\t\t\t\t" + cdacTime +"s");
                    writer.println(phoneNumber + "," + cdacValid +  "," + cdyneValid +  "," + cdyneTime +"ms"+  "," + cdacTime +"ms");
                    phoneNumber = "";
                    cdacValid = "";
                    cdyneValid = "";
                    cdyneTime = "";
                    cdacTime = "";
                }
            }

            System.out.println("Parsing complete. Results written to " + outputFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
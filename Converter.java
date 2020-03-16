/* The Converter Program
 *------------------------   @author Ruohan Dang Mar 2020
 * Keeps the exchange rates from one currency into another
 * and enables currency purchases to be estimated.
 * the use of hash tables.
*/
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Hashtable;
import java.util.Scanner;

public class Converter {
    Hashtable <String, Double> rateTable = new Hashtable<String, Double>();

    Converter(){
        welcome();
        readIn();
        transactions();
    }

    private void welcome() {
        System.out.println("==================\nCurrency Converter\n==================" +
                "\nDescription: convert X amount of Currency A to Currency B. " +
                "\n\nPlease enter the amount: ");
    }

    private void readIn() {
        String contents = "";
        Scanner reader = null;
        try {
            reader = new Scanner(new FileReader("src/forex.csv"));
        }catch (FileNotFoundException e) {
            System.out.println("The file doesn't exist.");
        }
        reader.nextLine();
        while(reader.hasNextLine()) {
            contents += reader.nextLine()+"\n";
        }
        String line[] = contents.split("\n");      // split the whole string by lines

        for (int i =0; i < line.length;i++) {
            String items[] = line[i].split(",");  // split a line by items
            String symbol = (String) items[0];           // select the symbol as the key
            String last = items[2];                      // select the rate as the value
            double rate = Double.parseDouble(last);
            rateTable.put(symbol, rate);                 // store keys and values in hashtable
        } // for
    } // readIn

    private void transactions() {
        double amount = 0;
        do {
            try {
                // get the amount of first currency
                Scanner in1 = new Scanner(System.in);
                String input1 = in1.nextLine();
                amount = Double.parseDouble(input1);
            } catch (Exception e) {
                // if the number is not correct
                System.out.println("Something went wrong, please enter the amount again: ");
                continue; // go again
            }
            break;
        } while (true);

        // prompt user to input the first currency and get it
        System.out.println("Please enter the currency A: ");
        Scanner in2 = new Scanner(System.in);
        String from = in2.nextLine();
        from = from.toUpperCase();

        // prompt user to input the second currency and get it
        System.out.println("Please enter the currency B: ");
        Scanner in3 = new Scanner(System.in);
        String to = in3.nextLine();
        to = to.toUpperCase();

        // check if inputs are in the hashtable or not
        if (!rateTable.containsKey(from)&rateTable.containsKey(to)) {
            System.out.println("Sorry, country "+ from +"is not in table.\n");
        } else if (!rateTable.containsKey(to) & rateTable.containsKey(from)){
            System.out.println("Sorry, country "+ to +"is not in table.\n");;
        } else if (!rateTable.containsKey(from)&!rateTable.containsKey(to)){
            System.out.println("Sorry, both countries are not in table.\n");;
        } else {
            // calculate the second currency purchases
            double rate1 = rateTable.get(from);
            double rate2 = rateTable.get(to);
            double amount1 = amount / rate1 * rate2;
            // round the
            amount = (double) Math.round(amount*100)/100;
            amount1 = (double) Math.round(amount1*100)/100;

            // output the final information
            System.out.println("$" + amount + " " + from + "= $" + amount1 + " " + to);
        }
    } // transactions

    public static void main(String[] args) {
        Converter data = new Converter();
    }
} // class Converter

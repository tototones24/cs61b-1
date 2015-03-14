/* Starter code for NgordnetUI (part 7 of the project). Rename this file and 
   remove this comment. */

package ngordnet;
import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.In;

/** Provides a simple user interface for exploring WordNet and NGram data.
 *  @author [yournamehere mcjones]
 */
public class NgordnetUI {
    public static void main(String[] args) {
        In in = new In("./ngordnet/ngordnetui.config");
        System.out.println("Reading ngordnetui.config...");

        String wordFile = in.readString();
        String countFile = in.readString();
        String synsetFile = in.readString();
        String hyponymFile = in.readString();
        System.out.println("\nBased on ngordnetui.config, using the following: "
                + wordFile + ", " + countFile + ", " + synsetFile +
                ", and " + hyponymFile + ".");

        NGramMap map = new NGramMap(wordFile, countFile);
        WordNet wMap = new WordNet(synsetFile, hyponymFile);
        int startDate = 0;
        int endDate = Integer.MAX_VALUE;
        while (true) {
            System.out.print("> ");
            String line = StdIn.readLine();
            String[] rawTokens = line.split(" ");
            String command = rawTokens[0];
            String[] tokens = new String[rawTokens.length - 1];
            System.arraycopy(rawTokens, 1, tokens, 0, rawTokens.length - 1);
            switch (command) {
                case "quit": 
                    return;
                case "help":
                    System.out.println((new In("./ngordnet/help.txt")).readAll());
                    break;  
                case "range": 
                    if (tokens.length != 2) {
                        continue;
                    }
                    startDate = Integer.parseInt(tokens[0]); 
                    endDate = Integer.parseInt(tokens[1]);
                    break;
                case "count":
                    if (tokens.length != 2) {
                        continue;
                    }
                    System.out.println(map.countInYear(tokens[0], Integer.parseInt(tokens[1])));
                    break;
                case "hyponyms":
                    if (tokens.length != 1) {
                        continue;
                    }
                    System.out.println(wMap.hyponyms(tokens[0]));
                    break;
                case "history":
                    Plotter.plotAllWords(map, tokens, startDate, endDate);
                    break;
                case "hypohist":
                    Plotter.plotCategoryWeights(map, wMap, tokens, startDate, endDate);
                    break;
                case "wordlength":
                    WordLengthProcessor w = new WordLengthProcessor();
                    Plotter.plotProcessedHistory(map, startDate, endDate, w);
                    break;
                case "zipf":
                    if (tokens.length != 1) {
                        continue;
                    }
                    Plotter.plotZipfsLaw(map, Integer.parseInt(tokens[0]));
                    break;
                default:
                    System.out.println("Invalid command.");  
                    break;
            }
        }

    }
} 

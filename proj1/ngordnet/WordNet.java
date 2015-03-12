package ngordnet;
import edu.princeton.cs.introcs.In;
import ngordnet.GraphHelper;
import edu.princeton.cs.algs4.Digraph;
import java.util.Set;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.TreeMap;

public class WordNet {
    private TreeMap<String, Integer> wordMapping;
    private ArrayList<String> words;
    private Digraph graph;
    public WordNet(String synsetFilename, String hyponymFilename){
        wordMapping = new TreeMap<String, Integer>();
        words = new ArrayList<String>();
        In synset = new In(synsetFilename);
        In hyponym = new In(hyponymFilename);
        int i = 0;
        while (synset.hasNextLine()){
            String[] line = synset.readLine().split(",");
            wordMapping.put(line[1], i);
            words.add(line[1]);
            i++;
        }
        graph = new Digraph(words.size());
        while (hyponym.hasNextLine()){
            String[] line = hyponym.readLine().split(",");
            int edge = Integer.parseInt(line[0]);
            for (i=1; i<line.length; i++){
                graph.addEdge(edge, Integer.parseInt(line[i]));
            }
        }
        synset.close();
        hyponym.close();
    }

    public Set<String> hyponyms(String word){
        TreeSet<Integer> vertex = new TreeSet<Integer>();
        Set<String> relatedWords = new TreeSet<String>();
        if (!wordMapping.containsKey(word)){
            return relatedWords;
        }
        vertex.add(wordMapping.get(word));

        Set<Integer> verticies = GraphHelper.descendants(graph, vertex);
        for (Integer i : verticies){
            relatedWords.add(words.get(i));
        }
        return relatedWords;
    }

    public boolean isNoun(String noun){
        return wordMapping.containsKey(noun);
    }

    public Set<String> nouns(){
        return wordMapping.keySet();
    }

    public static void main(String[] args){
        WordNet w = new WordNet("./wordnet/synsets11.txt", "./wordnet/hyponyms11.txt");
        System.out.print(w.hyponyms("animal"));
    }

}

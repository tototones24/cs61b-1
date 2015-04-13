import java.util.*;
import java.sql.Timestamp;
import java.io.*;
public class Commit implements Serializable {
    public Commit previous;
    public HashSet<String> files;
    public String message;
    public Timestamp created;
    public int id;

}

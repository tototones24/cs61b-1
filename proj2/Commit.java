import java.util.*;
import java.sql.Timestamp;
public class Commit {
    public Commit previous;
    public HashSet<String> files;
    public String message;
    public Timestamp created;
    public int id;

}

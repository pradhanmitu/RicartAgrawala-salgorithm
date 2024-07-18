package ricart;

import java.time.LocalTime;
import java.util.ArrayList;

public class Node{
    int id;
    LocalTime timestamp;
    boolean isCandidate;
    ArrayList<Boolean> replylist;
    ArrayList<Request> requestlist;

    public Node(int id){
        this.id=id;
        this.timestamp = LocalTime.now();
        this.isCandidate = false;
        replylist = new ArrayList<Boolean>();
        requestlist = new ArrayList<Request>();
    }
}
package ricart;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.lang.Math;
import java.time.LocalTime;

public class Main {
    public static ArrayList<Node> Nodelist;

    private static boolean All_Request_Resolved() {
        int count = 0;
        // count the total number of pending requests
        for (int i = 0; i < Nodelist.size(); i++) {
            count += Nodelist.get(i).requestlist.size();
        }
        if (count == 0) {
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        // take the no of nodes as input and create an arraylist of Node type objects
        int counter = 0;
        Nodelist = Input_processing.input();
        while (true) {
            counter += 1;
            System.out.println("\n\nround " + counter + "\n\n");
            // randomly make nodes candidate and set their isCandidate to true, store their
            // timestamp
            for (int i = 0; i < Nodelist.size(); i++) {
                try {
                    Thread.sleep(1);
                } catch (Exception e) {
                    // TODO: handle exception
                }
                int cointoss = (int) (Math.random() * 2 + 1);
                if (cointoss == 1) {
                    Nodelist.get(i).isCandidate = true;
                    Nodelist.get(i).timestamp = LocalTime.now();
                    System.out.println(
                            "node " + i + " sends request at time stamp " + Nodelist.get(i).timestamp.toString());
                }
            }
            // send request to all other nodes
            for (int i = 0; i < Nodelist.size(); i++) {
                if (Nodelist.get(i).isCandidate) {
                    // sends request to every other node
                    for (int j = 0; j < Nodelist.size(); j++) {
                        if (j == i)
                            continue;
                        Nodelist.get(j).requestlist.add(new Request(i, Nodelist.get(i).timestamp));
                    }
                }
            }

            while (!All_Request_Resolved()) {
                // resolve request if possible
                for (int i = 0; i < Nodelist.size(); i++) {
                    Handle_requests(i);
                }
                // valid node goes to critical section
                for (int i = 0; i < Nodelist.size(); i++) {
                    if (Nodelist.get(i).replylist.size() == Nodelist.size() - 1) {
                        enter_cs(i);
                    }
                }
            }
            Scanner sc = new Scanner(System.in);
            System.out.print("press 1 to continue/ else to exit: ");
            int inpt = sc.nextInt();
            if (inpt == 1) {

            } else {
                break;
            }
        }
    }

    private static void enter_cs(int i) {
        System.out.println("----------");
        for (Node j : Nodelist) {
            if (j.id == i) {
                System.out.println("Node " + i + " is in Critical Section\n");
            } else if (j.isCandidate == true) {
                System.out.println("Node " + j.id + " is waiting for Critical Section\n");
            } else if (j.isCandidate == false) {
                System.out.println("Node " + j.id + " is idle\n");
            }
        }


        //Critical section
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
          
        }

        
        Nodelist.get(i).isCandidate = false;
        Nodelist.get(i).replylist.clear();
    }

    private static void Handle_requests(int i) {
        if (!Nodelist.get(i).isCandidate) {
            Iterator<Request> iter = Nodelist.get(i).requestlist.iterator();
            while (iter.hasNext()) {
                Request n = iter.next();
                if (n.id == i) {
                    continue;
                }
                Nodelist.get(n.id).replylist.add(true);
                iter.remove();
            }
        } else if (Nodelist.get(i).isCandidate) {
            Iterator<Request> iter = Nodelist.get(i).requestlist.iterator();
            while (iter.hasNext()) {
                Request n = iter.next();
                if (n.id == i) {
                    continue;
                }
                if (Nodelist.get(i).timestamp.compareTo(n.timestamp) > 0) {
                    Nodelist.get(n.id).replylist.add(true);
                    iter.remove();
                }
            }
        }
    }
}

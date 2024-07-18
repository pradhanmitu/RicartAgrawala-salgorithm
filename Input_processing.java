package ricart;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Input_processing {
    static ArrayList<Node> input(){
        ArrayList<Node> array = new ArrayList<Node>();    
        try {
            File f = new File("C:\\Users\\Mitu Pradhan\\Desktop\\operating system\\ricart\\src\\ricart\\input.txt");
            try (Scanner sc1 = new Scanner(f)) {
                while(sc1.hasNextLine() == true){
                    String s = sc1.nextLine();
                    try {
                        int n = Integer.parseInt(s);
                        for(int i=0;i<n;i++){
                            array.add(new Node(i));
                        }
                    } catch (Exception e) {}
                }
            }
        } catch (Exception e) {System.out.println(e);}
        return array;
    } 
}
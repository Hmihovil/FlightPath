/*
Scott Spinali
Shaydon Bodemar

5 - 6 - 2019
 */

public class Node {
    private String name;
    private int cost;
    private Node next;

    public Node(String n, int c){
        name = n;
        cost = c;
        next = null;
    }//parameterized constructor for the method

    public Node getNext(){
        return next;
    }//getter method for iterating to the next node

    public void setNext(Node nextNode){
        next = nextNode;
    }//setter method for referencing the next node

    public int getCost(){
        return cost;
    }//getter method for the cost value

    public String getName(){
        return name;
    }//getter method for the name value
}

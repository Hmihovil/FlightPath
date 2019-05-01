public class Node {
    private String name;
    private int cost;
    private Node next;

    public Node(String n, int c){
        name = n;
        cost = c;
        next = null;
    }

    public Node getNext(){
        return next;
    }

    public void setNext(Node nextNode){
        next = nextNode;
    }

    public int getCost(){
        return cost;
    }

    public String getName(){
        return name;
    }
}

/*
Scott Spinali
Shaydon Bodemar

5 - 6 - 2019
 */

public class LinkedList {
    private Node head;
    private int numItems;

    public LinkedList(){
        head = null;
        numItems = 0;
    }//no-argument constructor to initialize the LinkedList

    public int listLength(){
        return numItems;
    }

    public void append(String name, int cost){
        Node newNode = new Node(name,cost);
        if(head==null){
            head = newNode;
        }
        else{
            Node cur = head;
            while(cur.getNext()!=null){
                cur = cur.getNext();
            }
            cur.setNext(newNode);
        }
        numItems++;
    }//adds a node to the LinkedList

    private Node locate(int position)
    {
        Node cur = head;
        while (position > 1) {
            cur = cur.getNext();
            position--;
        }
        return cur;
    }//returns the node at an index

    public String nameAt(int position) throws IndexOutOfBoundsException
    {
        if (position >= 1 && position <= numItems){
            Node cur = locate(position);
            return cur.getName();
        }
        else{
            throw new IndexOutOfBoundsException("Error.  Attempted to retrieve at an invalid index in RetrieveCodeAt()");
        }
    }//finds the name at a certain index in the LinkedList

    public int costOfDest(String dest){
        Node cur = head;
        if(dest.equalsIgnoreCase(cur.getName())){
            return 0;
        }
        for(int i = 2; i <= numItems+1; i++){
            if(dest.equalsIgnoreCase(cur.getName())){
                return cur.getCost();
            }
            cur = cur.getNext();
        }
        return Integer.MAX_VALUE;
    }//determines the cost to a particular node (dest) from the source
}
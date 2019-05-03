public class LinkedList {
    private Node head;
    private int numItems;

    public LinkedList(){
        head = null;
        numItems = 0;
    }

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
    }

    private Node locate(int position)
    {
        Node cur = head;
        while (position > 1)
        {
            cur = cur.getNext();
            position--;
        }
        return cur;
    }

    public int costAt(int position) throws IndexOutOfBoundsException
    {
        if (position >= 1 && position <= numItems)
        {
            Node cur = locate(position);
            return cur.getCost();
        }
        else
        {
            throw new IndexOutOfBoundsException("Error.  Attempted to retrieve at an invalid index in RetrieveCodeAt()");
        }
    }//retrieves the value of a code at a particular index in the list

    public String nameAt(int position) throws IndexOutOfBoundsException
    {
        if (position >= 1 && position <= numItems)
        {
            Node cur = locate(position);
            return cur.getName();
        }
        else
        {
            throw new IndexOutOfBoundsException("Error.  Attempted to retrieve at an invalid index in RetrieveCodeAt()");
        }
    }

    public int costOfDest(String dest){
        Node cur = head;
        if(dest.equalsIgnoreCase(cur.getName())){
            return 0;
        }
        //may or may not need to be numItems+1 int the for loop parameters
        for(int i = 2; i <= numItems+1; i++){
            if(dest.equalsIgnoreCase(cur.getName())){
                return cur.getCost();
            }
            cur = cur.getNext();
        }
        return Integer.MAX_VALUE;
    }
}

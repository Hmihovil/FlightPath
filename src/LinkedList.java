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
        Node curr = head;
        while (position > 1)
        {
            curr = curr.getNext();
            position--;
        }
        return curr;
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
}

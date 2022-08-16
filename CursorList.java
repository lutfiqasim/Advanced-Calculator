public class CursorList<T extends Comparable<T>> {
	private Node<T>[] cursorArray;

	public CursorList(int capacit) {
		cursorArray = new Node[capacit];
		initialization();
	}

	private int initialization() {
		for (int i = 0; i < cursorArray.length; i++)
			cursorArray[i] = new Node<>(null, i + 1);

		cursorArray[cursorArray.length - 1] = new Node<>(null, 0);
		return 0;
	}

	// takes an element from a free list to use it
	private int malloc() {
		int p = cursorArray[0].getNext();
		cursorArray[0].setNext(cursorArray[p].getNext());
		return p;
	}

	// frees an index and return it to free list
	private void free(int p) {
		// makes Node p empty and points to the zeros next in a free list
		cursorArray[p] = new Node<T>(null, cursorArray[0].getNext());
		// zeros next becomes the new freed Node of p
		cursorArray[0].setNext(p);
	}

	// Checks if a specified list is null or not(created or not)
	public boolean isNull(int l) {
		return cursorArray[l].getData() == null;
	}

	// Checks if a given list is empty
	public boolean isEmpty(int l) {
		return cursorArray[l].getNext() == 0;
	}

	// checks if a given Node is the last Node
	public boolean isLast(int p) {
		return cursorArray[p].getNext() == 0;
	}

	// creating a new Linked list
	public int createList() {
		// allocating one free node using malloc function
		int l = malloc();
		if (l == 0)
			System.out.println("Error: Out of space!!");
		else
			// makes next of new Node to 0 to show where it ends
			cursorArray[l] = new Node("-", 0);
		return l;
	}

	// add a node at head at specified linked list
	public boolean insertAtHead(T data, int l) {
		if (isNull(l)) // list not created
			return false;

		int p = malloc();
		if (p != 0) {
			cursorArray[p] = new Node<T>(data, cursorArray[l].getNext());
			cursorArray[l].setNext(p);
		} else 
			return false;
		
		return true;
	}

	public Comparable<T> getFirst(int l) {
		if (isNull(l) || isEmpty(l))
			return null;
		return cursorArray[cursorArray[l].getNext()].getData();
	}

	public void clear(int l) {
		while (!isNull(l) && !isEmpty(l)) {
			free(l);
			l = cursorArray[l].getNext();
		}
	}

	public Comparable<T> deleteFirst(int l) {
		if (isNull(l) || isEmpty(l))
			return null;
		int deleteThis = cursorArray[l].getNext();
		Node<T> toDel = cursorArray[deleteThis];
		cursorArray[l].setNext(toDel.getNext());
		free(deleteThis);
		return toDel.getData();
	}
}
//LIFO: last in first out
public class CursorStack<T extends Comparable<T>> implements StackInterface<T> {
	private CursorList<T> cStack = new CursorList<>(1000);
	private int l = cStack.createList();

	@Override
	public void push(T data) {
//		if (!cStack.insertAtHead(data, l))
//			System.err.println("StackOverFlow");
		cStack.insertAtHead(data, l);
	}

	@Override
	public Comparable<T> pop() {
		return cStack.deleteFirst(l);

	}

	@Override
	public T peek() {
		return ((T) cStack.getFirst(l));
	}

	@Override
	public boolean isEmpty() {
		return cStack.isEmpty(l);
	}

	@Override
	public void clear() {
		int s = l;
		while (!cStack.isLast(s)) {
			cStack.deleteFirst(s);
		}
	}

}
public interface StackInterface<T extends Comparable<T>> {
	public void push(T data);

	public Comparable<T> pop();

	public T peek();

	public boolean isEmpty();

	public abstract void clear();

}

/**
 * A binary min heap implementation with time complexity ???
 * @author Xu Yan
 *
 * @param <DataType>
 */
public class Heap<DataType extends Comparable<DataType>>{
	private DataType[] data; // An array is used to represent the heap(complete binary tree with heap properties)
	private int N; // The number of elements in the heap
	
	public Heap(int capacity) {
		this.data = (DataType[]) new Object[capacity + 1];
		this.N = 0;
	}
	
	public boolean isEmpty() {
		return this.N == 0;
	}
	
	public int size() {
		return this.N;
	}
	
	public void insert(DataType element) {
		if (this.N == this.data.length - 1) {
			doubleSize();
		}
		this.N ++;
		this.data[this.N] = element;
		swim(this.N);
	}
	
	public DataType deleteMin() throws Exception {
		if (this.isEmpty()) {
			throw new Exception("Trying to delete min from an empty heap");
		}
		DataType minElement = this.data[1];
		this.swap(1, this.N);
		this.data[this.N] = null;
		this.N --;
		sink(1);
		
		return minElement;
	}
	
	/**
	 * Double the capacity of heap
	 */
	private void doubleSize() {
		DataType[] new_data = (DataType[]) new Object[this.data.length * 2];
		for (int i = 0; i < this.data.length; i++) {
			new_data[i] = this.data[i];
		}
		this.data = new_data;
	}
	
	/**
	 * Repeatedly comparing the element at current position (starts with N) with its parent element.
	 * Exchange current element and its parent element if current element is smaller than its parent element
	 * 
	 * @param position the position to start swimming up
	 */
	private void swim(int position) {
		int current_position = position;
		while (current_position > 1) {
			int parent_position = current_position / 2;
			if (this.data[parent_position].compareTo(this.data[current_position]) < 0) {
				swap(parent_position, current_position);
				current_position = parent_position;
			} else {
				break;
			}
		}
	}
	
	/**
	 * Repeated comparing the element at current position (starts with 1) with its children elements.
	 * If current element is smallest, min binary heap is satisfied.
	 * Otherwise, swap current element with the larger child element and go on repeating the same process.  
	 * 
	 * @param position the position to start sinking down
	 */
	private void sink(int position) {
		int current_position = position;
		while (current_position < this.N) {
			int child_position = 2 * current_position;
			if (child_position < this.N && this.data[child_position].compareTo(this.data[child_position + 1]) < 0) {
				child_position ++;
			}
			if (this.data[current_position].compareTo(this.data[child_position]) > 0) {
				swap(current_position, child_position);
				current_position = child_position;
			} else {
				break;
			}
		}
	}
	
	private void swap(int position1, int position2) {
		DataType temp = this.data[position1];
		this.data[position1] = this.data[position2];
		this.data[position2] = temp;
	}
}

package assignment9;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Permutations<E> implements Iterator<ArrayList<E>> {
	private int length;
	private List<E> pool;
	public ArrayList<ArrayList<E>> results = new ArrayList<ArrayList<E>>();
	private ArrayList<Integer> indices;
	private ArrayList<E> nextResult;
	private ArrayList<Integer> cycles;
	private int index;

	public static void main(String[] args) throws Exception {
		Deck deck = new Deck();
		Permutations<Card> perms = new Permutations<Card>(deck.asList(), 4);

		while (perms.hasNext()) {
			System.out.println(perms.next());
		}
	}

	public Permutations(List<E> pool, int length) throws Exception {
		this.pool = pool;
		this.length = length;
		this.index = length - 1;

		if (length > pool.size()) {
			throw new Exception("Not enough items in the pool");
		}

		initialResult();
	}

	public boolean hasNext() {
		return (nextResult != null);
	}

	public ArrayList<E> next() {
		ArrayList<E> result = nextResult;
		nextPermutation();
		return result;
	}

	public void remove() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	private void initialResult() {
		indices = new ArrayList<Integer>();
		for (int ix = 0; ix < pool.size(); ix++) {
			indices.add(ix);
		}

		cycles = new ArrayList<Integer>();
		for (int jx = pool.size(); jx > (pool.size() - length); jx--) {
			cycles.add(jx);
		}

		nextResult = new ArrayList<E>();
		for (int kx : indices.subList(0, length)) {
			nextResult.add(pool.get(kx));
		}
	}

	private void nextPermutation() {
		trueloop: 
		while (true) {
			while (index >= 0) {
				cycles.set(index, cycles.get(index) - 1);
				if (cycles.get(index) == 0) {
					ArrayList<Integer> newIndices = new ArrayList<Integer>();
					newIndices.addAll(indices.subList(0, index));
					newIndices.addAll(indices.subList(index + 1, indices.size()));
					newIndices.addAll(indices.subList(index, index + 1));
					indices = newIndices;
					cycles.set(index, pool.size() - index);
					index--;
				} else {
					int search = indices.size() - cycles.get(index);
					int temp = indices.get(search);

					indices.set(search, indices.get(index));
					indices.set(index, temp);

					ArrayList<E> subResult = new ArrayList<E>();
					for (int num : indices.subList(0, length)) {
						subResult.add(pool.get(num));
					}
					nextResult = subResult;
					index = length - 1;
					break trueloop;
				}
			}
			nextResult = null;
		}
	}
}
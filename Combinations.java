package assignment9;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Combinations<E> implements Iterator<ArrayList<E>> {
    private List<E> pool;
    private int length;
    private ArrayList<E> nextResult;
    private ArrayList<Integer> indices;
    
    
    public static void main(String[] args) throws Exception {
		Deck deck = new Deck();
		Combinations<Card> combos = new Combinations<Card>(deck.asList(), 4);
		
		for (int gen=0; gen <= 5; gen++) {
			System.out.println(combos.next());
		}
	}
    

	public Combinations(List<E> pool, int length) throws Exception {
		this.pool = pool;
		this.length = length;
		
        if (length > pool.size()) {
            throw new Exception("Not enough items in the pool");
        }
        
        initialResult();
	}
	
	
	public boolean hasNext () {
		return (nextResult != null);
	}
	
	
	public ArrayList<E> next () {
		ArrayList<E> result = nextResult;
		nextCombination();
		return result;
	}
	

	public void remove() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	
	private void initialResult() {
        indices = new ArrayList<Integer>();
        nextResult = new ArrayList<E>();
        for (int ix=0; ix < length; ix++) {
        	indices.add(ix);
            nextResult.add(pool.get(ix));
        }
	}

	
    private void nextCombination() {
        // To be used in following trueloop.
        int index = 0;
        boolean flag = false;
        
        trueloop:
        while (true) {
            flag = false;
            for (int jx=length - 1; jx >= 0; jx--) {
                if (indices.get(jx) != jx + pool.size() - length) {
                    flag = true;
                    index = jx;  // Need it preserved for outside for-loop
                    break;
                }
            }

            if (!flag) {
            	nextResult = null;
                break trueloop;
            }
            
            indices.set(index, indices.get(index) + 1);
            
            for (int kx=index + 1; kx < length; kx++) {
            	indices.set(kx, indices.get(kx - 1) + 1);
            }
            
            ArrayList<E> subResult = new ArrayList<E>();
            for (int num : indices) {
            	subResult.add(pool.get(num));
            }
            nextResult = subResult;
            break;
        }
    }
}
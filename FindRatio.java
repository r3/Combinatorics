package assignment9;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

public class FindRatio {
	private Deck deck = new Deck();
	private Combinations<Card> combo;
	private Permutations<Card> perms;
	private int totalPossible = 0;
	private int successCount = 0;
	private ScriptEngine engine;
	private ArrayList<String> expressions = new ArrayList<String>();
	
	
	private static final int points = 24; 
	
	
    public static void main(String[] args) throws Exception {
    	FindRatio findRatio = new FindRatio();  // Who else loves this idiom?
    	System.out.println(findRatio.getSuccessCount() / 
    			findRatio.getTotalPossible());
    	System.out.println("Done!");
	}
    
    public FindRatio () throws Exception {
    	ScriptEngineManager mgr = new ScriptEngineManager();
    	engine = mgr.getEngineByName("JavaScript");
    	
    	/* Here's where I expect the "freezing" issue to be.
    	 * Generating and retaining such a long list as `expressions`
    	 * requires a good deal of memory, but I don't want to have to
    	 * generate it every time I iterate over it.
    	 */
    	
    	// This line allows me to do permutations with replacements
    	String[] operations = new String[] {"+", "+", "+", "-", "-", "-",
    			"*", "*", "*", "/", "/", "/"};  // Sorry for the hack
    	Permutations<String> operationPermutations = 
    			new Permutations<String>(Arrays.asList(operations), 3); 

    	// Construct strings like "%s + %s + %s + %s" for interpolation
        while (operationPermutations.hasNext()) {
        	StringBuilder sb = new StringBuilder();
        	for (String operation : operationPermutations.next()) {
	        	sb.append("%s");
	        	sb.append(operation);
        	}
        	sb.append("%s");
        	expressions.add(sb.toString());
        }
        
        // Light the fuse and run.
    	computeRatio();
    }
    
    
    /*
     * We need to be able to eval expressions from strings so that I don't
     * have to type everything out in if/then clauses and can instead iterate
     * over a list of string expressions and use interpolation to fill in the
     * values and evaluate them. JavaScript provides this, so we're using that
     * engine from the SDL as opposed to importing some third-party library.
     */
    private double eval (String expression) {
    	try {
			return (double) engine.eval(expression);
		} catch (ScriptException e) {
			System.out.println("An expression failed to be evaluated: " + expression);
			return 0.0;
		}
    }
    
    
    /* 
     * Computes the combinations of possible four card hands given a
     * standard playing card deck.
     * 
     * Using these combinations, it find each permutation of that hand and
     * attempts to score each ordering of that hand using an ugly algorithm
     * 
     * If the score of a permutation is 24, the iteration on that combination
     * will cease, the successCount shall be incremented, and we will begin
     * scoring permutations of the next combination.
     * 
     * I herd you liked combinatorics, 
     * so I put some combinatorics in your combinatorics.
     */
    private void computeRatio() throws Exception {
    	combo = new Combinations<Card>(deck.asList(), 4);
    	while (combo.hasNext()) {
    		totalPossible++;
    		perms = new Permutations<Card>(combo.next(), 4);
    		while (perms.hasNext()) {
    			if (score(perms.next())) {
    				successCount++;
    			}
    		}
    	}
    	
    }
    
    /*
     * Uses a standard library JS engine for its eval function as Java
     * lacks one. This allows me to use string interpolation to create
     * expressions. I can score each expression in a list and return
     * true if any evaluate to 24, or return false if 24 cannot be created.
     */
    private boolean score(List<Card> cards) {
    	for (String expression : expressions) {
    		String formatted = String.format(expression, cards.get(1),
    				cards.get(2), cards.get(3), cards.get(4));
    		if (eval(formatted) == points) {
    			return true;
    		}
    	}
    	return false;
    }

    /* 
     * Ge'cha some getters
     */
	public int getTotalPossible() {
		return totalPossible;
	}

	
	public int getSuccessCount() {
		return successCount;
	}
}
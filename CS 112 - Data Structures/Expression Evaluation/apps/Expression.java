package apps;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

import structures.Stack;


public class Expression {

	/**
	 * Expression to be evaluated
	 */
	String expr;                
    
	/**
	 * Scalar symbols in the expression 
	 */
	ArrayList<ScalarSymbol> scalars;   
	
	/**
	 * Array symbols in the expression
	 */
	ArrayList<ArraySymbol> arrays;
    
	/**
	 * Positions of opening brackets
	 */
	ArrayList<Integer> openingBracketIndex; 
    
	/**
	 * Positions of closing brackets
	 */
	ArrayList<Integer> closingBracketIndex; 

    /**
     * String containing all delimiters (characters other than variables and constants), 
     * to be used with StringTokenizer
     */
    public static final String delims = " \t*+-/()[]";
    
    /**
     * Initializes this Expression object with an input expression. Sets all other
     * fields to null.
     * 
     * @param expr Expression
     */
    public Expression(String expr) {
        this.expr = expr;
        scalars = null;
        arrays = null;
        openingBracketIndex = null;
        closingBracketIndex = null;
    }

    /**
     * Matches parentheses and square brackets. Populates the openingBracketIndex and
     * closingBracketIndex array lists in such a way that closingBracketIndex[i] is
     * the position of the bracket in the expression that closes an opening bracket
     * at position openingBracketIndex[i]. For example, if the expression is:
     * <pre>
     *    (a+(b-c))*(d+A[4])  (a+(b-c))*(d+A(4))
     * </pre>
     * then the method would return true, and the array lists would be set to:
     * <pre>
     *    openingBracketIndex: [0 3 10 14]
     *    closingBracketIndex: [8 7 17 16]
     * </pe>
     * 
     * See the FAQ in project description for more details.
     * 
     * @return True if brackets are matched correctly, false if not
     */
    public boolean isLegallyMatched() {
    	// COMPLETE THIS METHOD
    	if (!expr.contains("("))
    		return true;
    	
    	openingBracketIndex= new ArrayList<Integer>();
    	closingBracketIndex= new ArrayList<Integer>();
       
    	try{
    	for (int i=0; i<expr.length(); i++){
    		if (expr.charAt(i)=='('){
    			openingBracketIndex.add(i);
    			int k=i;//++;
    			while (expr.charAt(k)!=')'){
    				k++;
    				if (expr.charAt(k)=='('){
    					while (expr.charAt(k)!=')')
    						k++;k++;
    				}
    				else if (expr.charAt(k)=='['){
    					while (expr.charAt(k)!=']')
    						k++;k++;
    				}
    			}
    			closingBracketIndex.add(k);
    		}
    		else if (expr.charAt(i)=='['){
    			openingBracketIndex.add(i);
    			int k=i;//++;
    			while (expr.charAt(k)!=']'){
    				k++;
    				if (expr.charAt(k)=='('){
    					while (expr.charAt(k)!=')')
    						k++;k++;
    				}
    				else if (expr.charAt(k)=='['){
    					while (expr.charAt(k)!=']')
    						k++;k++;
    				}
    			}
    			closingBracketIndex.add(k);
    		}
    	}
    	}catch(StringIndexOutOfBoundsException e){return false;}
    		  
    	//System.out.println(openingBracketIndex.toString());
    	//System.out.println(closingBracketIndex.toString());
    	
    	if (openingBracketIndex.size()==closingBracketIndex.size())
    	return true;
    	else return false;
    }

    /**
     * Populates the scalars and arrays lists with symbols for scalar and array
     * variables in the expression. For every variable, a SINGLE symbol is created and stored,
     * even if it appears more than once in the expression.
     * At this time, values for all variables are set to
     * zero - they will be loaded from a file in the loadSymbolValues method.
     */
    public void buildSymbols() {
    	scalars=new ArrayList<ScalarSymbol>(); arrays=new ArrayList<ArraySymbol>(); 
    	StringTokenizer st = new StringTokenizer(expr,delims);
    	while(st.hasMoreTokens()){
    		String text=st.nextToken();
    		int lastPos=expr.indexOf(text)+text.length();
    		
    		if (lastPos>=expr.length()){
    			if(!scalars.contains(new ScalarSymbol(text))&&Character.isLetter(text.charAt(0)))
    			scalars.add(new ScalarSymbol(text));}
    		else if (expr.charAt(lastPos)=='['){
    			if(!arrays.contains(new ArraySymbol(text))&&Character.isLetter(text.charAt(0)))
    			arrays.add(new ArraySymbol(text));}
    		else {
    			if(!scalars.contains(new ScalarSymbol(text))&&Character.isLetter(text.charAt(0)))
    			scalars.add(new ScalarSymbol(text));}
    	}
    	//System.out.println(scalars.toString());
    	//System.out.println(arrays.toString());
    }
    
    /**
     * Loads values for symbols in the expression
     * 
     * @param sc Scanner for values input
     * @throws IOException If there is a problem with the input 
     */
    public void loadSymbolValues(Scanner sc) 
    throws IOException {
        while (sc.hasNextLine()) {
            StringTokenizer st = new StringTokenizer(sc.nextLine().trim());
            int numTokens = st.countTokens();
            String sym = st.nextToken();
            ScalarSymbol ssymbol = new ScalarSymbol(sym);
            ArraySymbol asymbol = new ArraySymbol(sym);
            int ssi = scalars.indexOf(ssymbol);
            int asi = arrays.indexOf(asymbol);
            if (ssi == -1 && asi == -1) {
            	continue;
            }
            int num = Integer.parseInt(st.nextToken());
            if (numTokens == 2) { // scalar symbol
                scalars.get(ssi).value = num;
            } else { // array symbol
            	asymbol = arrays.get(asi);
            	asymbol.values = new int[num];
                // following are (index,val) pairs
                while (st.hasMoreTokens()) {
                    String tok = st.nextToken();
                    StringTokenizer stt = new StringTokenizer(tok," (,)");
                    int index = Integer.parseInt(stt.nextToken());
                    int val = Integer.parseInt(stt.nextToken());
                    asymbol.values[index] = val;              
                }
            }
        }
    }
    
    /**
     * Evaluates the expression, using RECURSION to evaluate subexpressions and to evaluate array 
     * subscript expressions.
     * 
     * @return Result of evaluation
     */
    public float evaluate() {
    	for (int i = 0; i < this.scalars.size(); i++) 
    		expr = expr.replace(this.scalars.get(i).name, "" + this.scalars.get(i).value);
		
    	String regex = "(?<=op)|(?=op)".replace("op", "[-+*/()]");
    	String[] PF=expr.split(regex);
    	Stack<String> eval = new Stack<String>();
    	/*
    	 * New opening and closing brackets
    	 */
    	ArrayList<Integer> OBI= new ArrayList<Integer>();
    	ArrayList<Integer> CBI= new ArrayList<Integer>();
       
    	for (int i=0; i<PF.length; i++){
    		if (PF[i].equals("(")){
    			OBI.add(i);
    			int k=i;//++;
    			while (!PF[k].equals(")")){
    				k++;
    				if (PF[k].equals("(")){
    					while (!PF[i].equals(")"))
    						k++;k++;
    				}
    				else if (PF[k].equals("[")){
    					while (!PF[i].equals("]"))
    						k++;k++;
    				}
    			}
    			CBI.add(k);
    		}
    		else if (PF[i].equals("[")){
    			OBI.add(i);
    			int k=i;//++;
    			while (!PF[k].equals("]")){
    				k++;
    				if (PF[k].equals("(")){
    					while (!PF[k].equals(")"))
    						k++;k++;
    				}
    				else if (PF[k].equals("[")){
    					while (!PF[i].equals("]"))
    						k++;k++;
    				}
    			}
    			CBI.add(k);
    		}
    	}
    	System.out.println(OBI.toString());
    	System.out.println(CBI.toString());
    	
    	/*
    	 * Evaluate 
    	 */
    	for(int i=0;i<PF.length;i++)
        	eval.push(PF[i]);
    	return Float.parseFloat(String.valueOf(exprEval(eval)));    	
    }
    public String exprEval(Stack<String> OLD){  
    	ArrayList<String> reverser=new ArrayList<String>();
    	/*
    	 * * and /
    	 */
    	while(!OLD.isEmpty())
    		reverser.add(OLD.pop());
    	for(int i=0; i<reverser.size(); i++)
    		OLD.push(reverser.get(i));
    	Stack<String> s=new Stack<String>();
    	s.push(OLD.pop());
    	
    	while(OLD.size()>1){
    		float num1=Float.parseFloat(s.pop());
    		String op=OLD.pop();
    		float num2=Float.parseFloat(OLD.pop());
    	    		
    		if (op.equals("+")||op.equals("-")){
    			s.push(String.valueOf(num1));
    			s.push(op);
    			s.push(String.valueOf(num2));
    		}
    		else if (op.equals("*"))
    			s.push(String.valueOf(num1*num2));
    		else if (op.equals("/"))
    			s.push(String.valueOf(num1/num2));
    	}
    	/*
    	 * + and -
    	 */
    	reverser=new ArrayList<String>();
    	if (s.size()<2)return s.pop();
    	while(!s.isEmpty())
    		reverser.add(s.pop());
    	OLD=new Stack<String>();
    	for(int i=0; i<reverser.size(); i++){
    		OLD.push(reverser.get(i));}
    	s=new Stack<String>();
    	s.push(OLD.pop());
    	
    	while(OLD.size()>1){
    		float num1=Float.parseFloat(s.pop());
    		String op=OLD.pop();
    		float num2=Float.parseFloat(OLD.pop());
    		
    		if (op.equals("-"))
    			s.push(String.valueOf(num1-num2));
    		else if (op.equals("+"))
    			s.push(String.valueOf(num1+num2));
    	}
    	
    	String a=s.pop();
    	System.out.println(a);
    	return a;
    }
    /**
     * Utility method, prints the symbols in the scalars list
     */
    public void printScalars() {
        for (ScalarSymbol ss: scalars) {
            System.out.println(ss);
        }
    }
    
    /**
     * Utility method, prints the symbols in the arrays list
     */
    public void printArrays() {
    	for (ArraySymbol as: arrays) {
    		System.out.println(as);
    	}
    }

}

package ba.unsa.etf.rpr;

/**
 * Main class that will run the program and allow to enter expression
 */
public class App {
    /**
     * Writes out the result of the mathematical expression found in <code>args[0]</code>
     * @param args holds arguments passed from the console
     */
    public static void main(String[] args){
        try {
            if(args.length == 0){
                System.out.println("Please enter an expression!");
                System.exit(-1);
            }
            else if(args.length > 1){
                System.out.println("Unnecessary arguments, enter only the expression as one string!");
                System.exit(-2);
            }
            System.out.println("= " + ExpressionEvaluator.evaluate(args[0]));
        } catch(RuntimeException e) {
            System.out.println("exception " + e.getMessage());
        }
    }
}

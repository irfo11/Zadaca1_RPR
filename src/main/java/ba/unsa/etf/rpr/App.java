package ba.unsa.etf.rpr;

public class App {
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

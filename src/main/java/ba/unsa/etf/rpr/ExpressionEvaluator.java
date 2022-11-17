package ba.unsa.etf.rpr;

import java.util.Stack;

/**
 * The class <code>ExpressionEvaluator</code> contains a method for evaluating simple mathematical expressions
 */
public class ExpressionEvaluator {
    /**
     * Returns the result of a mathematical expression as a <code>Double</code>
     * @param expression Mathematical expression to be evaluated
     * @return result of the expression
     * @throws RuntimeException if the expression is not mathematically valid
     */
    public static Double evaluate(String expression) {
        Stack<String> operators = new Stack<>();
        Stack<Double> values = new Stack<>();
        for(String symbol: expression.split(" ")){
            if(symbol.equals("(")) operators.push(symbol);
            else if(symbol.equals("+")) operators.push(symbol);
            else if(symbol.equals("-")) operators.push(symbol);
            else if(symbol.equals("*")) operators.push(symbol);
            else if(symbol.equals("/")) operators.push(symbol);
            else if(symbol.equals("sqrt")) operators.push(symbol);
            else if(symbol.equals(")")){
                String operator = operators.pop();
                double value = values.pop();
                if (operator.equals("(")){
                    if(!operators.empty() && operators.peek().equals("sqrt")){
                        operator = operators.pop(); //sqrt
                        value = Math.sqrt(value);
                    }
                }
                else if (operator.equals("+")) value = values.pop() + value;
                else if (operator.equals("-")) value = values.pop() - value;
                else if (operator.equals("*")) value = values.pop() * value;
                else if (operator.equals("/")) value = values.pop() / value;
                values.push(value);
                //before every operator (not sqrt) should always be a (
                if(!operator.equals("(") && !operator.equals("sqrt")) operators.pop();
            }
            else{
                values.push(Double.parseDouble(symbol));
            }
        }
        if(!operators.empty() || values.size() > 1) throw new RuntimeException("Bad expression");
        return values.pop();
    }
}

package ba.unsa.etf.rpr;

import java.util.Stack;

/**
 * The class <code>ExpressionEvaluator</code> contains a method for evaluating simple mathematical expressions
 */
public class ExpressionEvaluator {
    /**
     * Symbols that can be found inside expression
     */
    private enum Symbol { NOTHING, OPEN_BRACKET, CLOSED_BRACKET, NUMBER, OPERATOR, SQRT }
    /**
     * Returns the result of a mathematical expression as a <code>Double</code>
     * @param expression Mathematical expression to be evaluated
     * @return result of the expression
     * @throws RuntimeException if the expression is not mathematically valid
     */
    public static Double evaluate(String expression) {
        Stack<String> operators = new Stack<>();
        Stack<Double> values = new Stack<>();
        Symbol previousSymbol = Symbol.NOTHING;
        int openParenthesis = 0;
        for(String symbol: expression.split(" ")){
            if(symbol.equals("(")) {
                if(!previousSymbol.equals(Symbol.NOTHING) &&
                   !previousSymbol.equals(Symbol.SQRT) &&
                   !previousSymbol.equals(Symbol.OPERATOR) &&
                   !previousSymbol.equals(Symbol.OPEN_BRACKET)) throw new RuntimeException("1");
                previousSymbol = Symbol.OPEN_BRACKET;
                openParenthesis++;
            }
            else if (symbol.equals(")")) {
                if(!previousSymbol.equals(Symbol.NUMBER) &&
                        !previousSymbol.equals(Symbol.CLOSED_BRACKET)) throw new RuntimeException("3");
                previousSymbol = Symbol.CLOSED_BRACKET;
                double value = values.pop();
                if(!operators.empty()) { //for situation when (x)
                    String operator = operators.pop();
                    if (operator.equals("+")) value = values.pop() + value;
                    else if (operator.equals("-")) value = values.pop() - value;
                    else if (operator.equals("*")) value = values.pop() * value;
                    else if (operator.equals("/")) value = values.pop() / value;
                    else if (operator.equals("sqrt")) value = Math.sqrt(value);
                }
                values.push(value);
                openParenthesis--;
            }
            else if(previousSymbol.equals(Symbol.NUMBER) ||
                    previousSymbol.equals(Symbol.CLOSED_BRACKET)) {
                if (symbol.equals("+")) operators.push(symbol);
                else if (symbol.equals("-")) operators.push(symbol);
                else if (symbol.equals("*")) operators.push(symbol);
                else if (symbol.equals("/")) operators.push(symbol);
                else throw new RuntimeException("2"); //symbol was not an operator
                if(operators.size() > openParenthesis) throw new RuntimeException("7");
                previousSymbol = Symbol.OPERATOR;
            }
            else if(symbol.equals("sqrt")) {
                if(!previousSymbol.equals(Symbol.OPEN_BRACKET) &&
                   !previousSymbol.equals(Symbol.OPERATOR)) throw new RuntimeException("5");
                operators.push(symbol);
                previousSymbol = Symbol.SQRT;
            }//
            else{
                if(!previousSymbol.equals(Symbol.OPEN_BRACKET) &&
                   !previousSymbol.equals(Symbol.OPERATOR)) throw new RuntimeException("4");
                values.push(Double.parseDouble(symbol)); //this throws runtimeException as well if not number
                previousSymbol = Symbol.NUMBER;

            }
        }
        return values.pop();
    }
}

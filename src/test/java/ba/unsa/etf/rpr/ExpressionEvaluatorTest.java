package ba.unsa.etf.rpr;

import org.junit.jupiter.api.Test;

import static ba.unsa.etf.rpr.ExpressionEvaluator.evaluate;
import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests possible inputs to the <code>ExpressionEvaluator.evaluate(String)</code> method
 */
class ExpressionEvaluatorTest {
    /**
     * Test a lot of possible valid combinations
     */
    @Test
    void validExpressionsTest() {
        assertEquals(1.0, evaluate("1")); //if its one number its allowed
        assertEquals(2.0, evaluate("sqrt ( 4 )")); //sqrt is treated like one number
        assertEquals(2.0, evaluate("sqrt ( ( 2 + 2 ) )"));
        assertEquals(2.0, evaluate("sqrt ( ( sqrt ( 4 ) + sqrt ( 4 ) ) )"));
        assertEquals(3.0, evaluate("( 1 + 2 )"));
        assertEquals(9.0, evaluate("( ( 1 + 2 ) * 3 )"));
        assertEquals(-4.0, evaluate("( 1 - ( 2 + 3 ) )"));
        assertEquals(3.0, evaluate("( ( ( 1 * 3 ) + 3 ) / 2 )"));
        assertEquals(0.0, evaluate("( 1 + ( 3 - ( 2 * 2 ) ) )"));
        assertEquals(-3.0, evaluate("( 5 + ( ( ( ( 2 - 2 ) - 1 ) - 3 ) * 2 ) )"));
        assertEquals(1.0, evaluate("( 1 )"));
        assertEquals(2.0, evaluate("( ( 2 ) )"));
        assertEquals(3.0, evaluate("( ( ( 3 ) ) )"));
        assertEquals(4.0, evaluate("( ( 1 ) + 3 )"));
        assertEquals(6.0, evaluate("( ( 1 ) + ( 2 + 3 ) )"));
        assertEquals(6.0, evaluate("( ( 2 + 3 ) + ( 1 ) )"));
        assertEquals(3.0, evaluate("( ( 1 ) + ( 2 ) )"));
        assertEquals(2.0, evaluate("( sqrt ( 4 ) )"));
        assertEquals(2.0, evaluate("( ( sqrt ( 4 ) ) )"));
        assertEquals(2.0, evaluate("( sqrt ( ( 2 + 2 ) ) )"));
        assertEquals(2.0, evaluate("( sqrt ( ( 4 ) ) )"));
        assertEquals(4.0, evaluate("( 1 + sqrt ( ( 4 + 5 ) ) )"));
        assertEquals(4.0, evaluate("( sqrt ( ( 4 + 5 ) ) + 1 )"));
        assertEquals(2.0, evaluate("( sqrt ( sqrt ( 16 ) ) )"));
        assertEquals(4.0, evaluate("( sqrt ( 4 ) + sqrt ( 4 ) )"));
        assertEquals(2.0, evaluate("( sqrt ( ( 2 + 2 ) ) )"));
        assertEquals(2.0, evaluate("( sqrt ( ( 2 + ( 1 + 1 ) ) ) )"));
        assertEquals(3.0, evaluate("( sqrt ( ( ( 2 + 2 ) + 5 ) ) )"));
        assertEquals(4.0, evaluate("( sqrt ( ( 4 + 5 ) ) + 1 )"));
        assertEquals(4.0, evaluate("( 1 + sqrt ( ( 4 + 5 ) ) )"));
        assertEquals(4.0, evaluate("( 1 + sqrt ( ( ( 4 + 5 ) ) ) )"));
    }

    /**
     * Tests long expressions
     */
    @Test
    void LongExpressionTest() { // if these fail RIP
        assertEquals(6.0, evaluate("( ( 5 + ( 2 - ( 1 + 4 ) ) ) * ( 15 / ( 9 - ( 2 + 2 ) ) ) )"));
        assertEquals(5.0, evaluate("( ( 4 + ( sqrt ( ( 3 + ( 4 + 2 ) ) ) ) ) - sqrt ( sqrt ( 16 ) ) )"));
        assertEquals(7.0, evaluate("( 1 + ( 3 * ( 6 - ( 2 + sqrt ( 4 ) ) ) ) )"));
        assertEquals(3.0, evaluate("( ( ( ( ( 5 + 1 ) - 2 ) * 3 ) - 3 ) / 3 )"));
    }

    /**
     * Tests if <code>ExpressionEvaluator.evaluate(String)</code> will throw <code>RuntimeException</code>
     * when an open bracket is not closed
     */
    @Test
    void OpenParenthesisNotClosedTest() {
        assertThrows(RuntimeException.class, () -> evaluate("( 1 + 2 ("));
        assertThrows(RuntimeException.class, () -> evaluate("( 1 ("));
        assertThrows(RuntimeException.class, () -> evaluate("( ( 1 ) + ( 2 + 3 ( )"));
        assertThrows(RuntimeException.class, () -> evaluate("( ( ( 2 + 3 ) + ("));
        assertThrows(RuntimeException.class, () -> evaluate("( sqrt ( 5 ("));
        assertThrows(RuntimeException.class, () -> evaluate("( 1 + ( ( 3 + 1 ) + ( ("));
        assertThrows(RuntimeException.class, () -> evaluate("( ( 1 + ( ("));
    }

    /**
     * Tests if <code>ExpressionEvaluator.evaluate(String)</code> will throw <code>RuntimeException</code>
     * when there is an expression inside sqrt() that should be inside parenthesis
     */
    @Test
    void NoParenthesisInsideSqrtTest() {
        assertThrows(RuntimeException.class, () -> evaluate("( sqrt ( 2 + 2 ) )"));
        assertThrows(RuntimeException.class, () -> evaluate("( sqrt ( 2 + ( 1 + 1 ) ) )"));
        assertThrows(RuntimeException.class, () -> evaluate("( sqrt ( ( 2 + 2 ) + 5 ) )"));
        assertThrows(RuntimeException.class, () -> evaluate("( sqrt ( 4 + 5 ) + 1 )"));
        assertThrows(RuntimeException.class, () -> evaluate("( 1 + sqrt ( 4 + 5 ) )"));
        assertThrows(RuntimeException.class, () -> evaluate("sqrt ( 2 + 2 )"));
        assertThrows(RuntimeException.class, () -> evaluate("sqrt ( sqrt ( 4 ) + sqrt ( 4 ) )"));
    }

    /**
     * Tests if <code>ExpressionEvaluator.evaluate(String)</code> will throw <code>RuntimeException</code>
     * when there is an operator after an operator
     */
    @Test
    void AfterOperatorOperatorTest() {
        assertThrows(RuntimeException.class, () -> evaluate( "( 1 + + 3 )"));
        assertThrows(RuntimeException.class, () -> evaluate("( 1 + ( 1 - - 3 ) )"));
        assertThrows(RuntimeException.class, () -> evaluate("( ( + + ) + 3"));
        assertThrows(RuntimeException.class, () -> evaluate("( sqrt ( 3 * + 4 ) )"));
        assertThrows(RuntimeException.class, () -> evaluate("( 1 + + sqrt ( 3 ) )"));
    }

    /**
     * Tests if <code>ExpressionEvaluator.evaluate(String)</code> will throw <code>RuntimeException</code>
     * when there are multiple operations inside one parenthesis
     */
    @Test
    void MultipleOperationsInsideOneParenthesisTest() {
        assertThrows(RuntimeException.class, () -> evaluate("( 1 + 2 * 3 )"));
        assertThrows(RuntimeException.class, () -> evaluate("( ( 1 ) + 2 + 3 )"));
        assertThrows(RuntimeException.class, () -> evaluate("( 1 + ( 2 ) + 3 )"));
        assertThrows(RuntimeException.class, () -> evaluate("( 1 + 2 + ( 3 ) )"));
        assertThrows(RuntimeException.class, () -> evaluate("( ( 1 ) + ( 2 ) + ( 3 ) )"));
        assertThrows(RuntimeException.class, () -> evaluate("( 1 + ( 2 - 4 / 4 ) )"));
        assertThrows(RuntimeException.class, () -> evaluate("( 1 + ( 1 + 2 ) - ( 5 + 3 ) )"));
        assertThrows(RuntimeException.class, () -> evaluate("( ( 2 + 2 ) + 1 + 1"));
        assertThrows(RuntimeException.class, () -> evaluate("( sqrt ( ( 1 + 2 + 3 ) ) )"));
        assertThrows(RuntimeException.class, () -> evaluate("( 1 + sqrt ( ( 1 + 3 + 5 ) )"));
    }

    /**
     * Tests if <code>ExpressionEvaluator.evaluate(String)</code> will throw <code>RuntimeException</code>
     * when the expression contains unnecessary whitespaces
     */
    @Test
    void UnnecessaryWhitespacesTest() {
        assertThrows(RuntimeException.class, () -> evaluate(" ( 1 + 2 )"));
        assertThrows(RuntimeException.class, () -> evaluate(" ( 1 + 2 ) "));
        assertThrows(RuntimeException.class, () -> evaluate("( 1  + 2 )"));
        assertThrows(RuntimeException.class, () -> evaluate("(  1 +    3 )"));
    }

    /**
     * Tests if <code>ExpressionEvaluator.evaluate(String)</code> will throw <code>RuntimeException</code>
     * when the expression has a format not tested by tests before this one
     */
    @Test
    void MiscellaneousBadFormatTest(){
        assertThrows(RuntimeException.class, () -> evaluate("1 + 2"));
        assertThrows(RuntimeException.class, () -> evaluate("1 - 2 )"));
        assertThrows(RuntimeException.class, () -> evaluate("( ( 1 )"));
        assertThrows(RuntimeException.class, () -> evaluate("( 1 * 3 ) )"));
        assertThrows(RuntimeException.class, () -> evaluate("(3+1)"));
        assertThrows(RuntimeException.class, () -> evaluate("sqrt ( 4 ) )"));
        assertThrows(RuntimeException.class, () -> evaluate("( sqrt ( 4 )"));
        assertThrows(RuntimeException.class, () -> evaluate(""));
        assertThrows(RuntimeException.class, () -> evaluate("("));
        assertThrows(RuntimeException.class, () -> evaluate("( )"));
        assertThrows(RuntimeException.class, () -> evaluate(")"));
        assertThrows(RuntimeException.class, () -> evaluate("( ( ) )"));
        assertThrows(RuntimeException.class, () -> evaluate("( 1 + s )"));
        assertThrows(RuntimeException.class, () -> evaluate("( ( 1 + 2 )"));
        assertThrows(RuntimeException.class, () -> evaluate("( 1 + )"));
        assertThrows(RuntimeException.class, () -> evaluate("( 1 + ( 1 + ) )"));
    }

}
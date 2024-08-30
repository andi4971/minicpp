import org.azauner.ast.generator.exception.SemanticException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class FactTest: SemanticTest() {

    @Test
    fun testArrayInvalidIndexType() {
        assertThrows<SemanticException> {
            testCodeInMain(
                """
                    bool *arr = nullptr;
                    arr = new bool[5];
                    arr[true] = false; 
               """.trimIndent()
            )
        }
    }
    @Test
    fun testArrayValidIndexType() {
        assertDoesNotThrow {
            testCodeInMain(
                """
                    bool *arr = nullptr;
                    arr = new bool[5];
                    arr[3] = false;
               """.trimIndent()
            )
        }
    }

    @Test
    fun testIntIncrease() {
        assertDoesNotThrow {
            testCodeInMain(
                """
                    int test = 5;
                    test++;
                    test--;
                    --test;
                    ++test;
               """.trimIndent()
            )
        }
    }
    @Test
    fun testIntIncreaseInvalidType() {
        assertThrows<SemanticException> {
            testCodeInMain(
                """
                    bool test = true;
                    test++;
               """.trimIndent()
            )
        }
    }

    @Test
    fun testIntIncreaseInvalidTypeFunc() {
        assertThrows<SemanticException> {
            testCodeInMain(
                """
                    bool test = true;
                    ++main();
               """.trimIndent()
            )
        }
    }

    @Test
    fun varNotFound() {
        assertThrows<SemanticException> {
            testCodeInMain(
                """
                  int test = 5;
                  int test2;
                  test2 = test + test3;
                
            """.trimIndent()
            )
        }
    }
}

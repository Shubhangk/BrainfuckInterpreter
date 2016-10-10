import java.util.Scanner;
import java.util.Stack;

/**
 * Created by shubhangkulkarni on 10/10/16.
 */
public class BrainFuck {

    private int length; // stores the length of the expression
    private static final int MEMORY_LENGTH = 1000; // hardcoded number of memory cells. Should typically be atleast 30,000
    private String expression; // Stores the Brainfuck input expression
    private String output; // Stores the output
    private int memptr; // pointer to the current cell of memory
    private int parser; // points to the character in "expression" which is currently being parsed(interpreted)
    private Stack<Integer> braces; // For conditional statements. Holds the indices of the opening braces
    private byte[] cells; // memory allocated to the program
    public boolean flag; // true if user input is required for the given program. For UI purposes only

    public BrainFuck(String expression){
        this.length = expression.length();
        this.cells = new byte[MEMORY_LENGTH];
        this.expression = new String(expression);
        this.output = "";
        this.memptr = 0;
        this.braces = new Stack<>();
        this.flag = false;
    }

    /*
    * @description - checks whether the passed expression is properly balenced
    *              - Only checks '[' and ']'. Ignores all other braces
    * @return - true if expression is balanced, false otherwise
    * */
    public boolean isBalanced(){
        int open_braces = 0;
        for (int i = 0; i < this.length; i++) {
            if (expression.charAt(i) == ',') flag = true; // Added bonus in this method...
            if (expression.charAt(i) == '[')
                open_braces++;
            else if (expression.charAt(i) == ']')
                open_braces--;

            if (open_braces < 0) break; // takes care of case "...]...[..."
        }
        return open_braces == 0;
    }

    /*
    * @description - goes through and parses the input program character by character
    * */
    public void execute(){
        for (parser = 0; parser < length; parser++)
            parse(expression.charAt(parser));
    }

    /*
    * @param - char c: character to be parsed(interpreted)
    * @description - Maps the 8 instructional characters of Brainfuck to their corresponding operations
    *         Memory Traversal -
    *              < : go back a cell,
    *              > : move to the next cell
    *         I/O
    *              . : Output Ascii of value at current cell pointed to by memory pointer
    *              , : Take user input (decimal ascii value) and store in current cell pointed to by memory pointer
    *         Memory Manipulation
    *              + : increment value at current cell pointed to by memory pointer, by 1
    *              - : decrement value at current cell pointed to by memory pointer, by 1
    *
    * Note - this method manipulates the this.output variable
    * */
    private void parse(char c) {
        switch (c){
            // Memory Traversal
            case '<':
                memptr = memptr == 0 ? MEMORY_LENGTH - 1 : memptr - 1;
                break;
            case '>':
                memptr = memptr == MEMORY_LENGTH - 1 ? 0 : memptr + 1;
                break;
            // I/O operations
            case '.':
                output += (char) cells[memptr];
                break;
            case ',':
                System.out.print(">");
                Scanner scan = new Scanner(System.in);
                cells[memptr] = scan.nextByte();
                break;
            // Memory Alteration Operations
            case '+':
                cells[memptr]++;
                break;
            case '-':
                cells[memptr]--;
                break;

            // Conditional Branching
            case '[':
                braces.push(parser);
                break;
            case ']':
                if (cells[memptr] != 0)
                    parser = braces.peek();
                else
                    braces.pop();
                break;
            default:
                // Brainfuck Compiler ignores everything else...
                break;
        }
    }
    public String getOutput(){
        return this.output;
    }
}

import java.util.Scanner;

/**
 * Created by shubhangkulkarni on 10/10/16.
 */
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String expression = new String();
        System.out.println("\nHello! You've booted up Shubhang's Brainfuck Interpreter ^_^\nEnter your program below. " +
                "Tell the Interpreter that your Program has finished by " +
                "closing off the Curly Brace\n");
        System.out.println("#Start\n{");


        while(sc.hasNextLine()){
            expression += sc.nextLine();
            if (expression.charAt(expression.length()-1) == '}') break;
        }
        System.out.println("#end");
        BrainFuck brainFuck = new BrainFuck(expression);
        if (!brainFuck.isBalanced()){
            System.out.println("Invalid input - Unbalanced Expression");
            System.exit(1); // crash gracefully
        }
        if (brainFuck.flag)
            System.out.println("User Input Console:");
        brainFuck.execute();
        System.out.println("---Program Output---\n"+brainFuck.getOutput());
    }
}

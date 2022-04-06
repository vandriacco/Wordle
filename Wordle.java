import java.io.*;
import java.util.*;

public class Wordle {
    
    public static void main(String[] args) {
        String answer = readLineFromFile("wordle-answers-alphabetical.txt", randInt(0, 2314));
        Scanner input = new Scanner(System.in);
        
        System.out.println("Welcome to wordle! You have 6 tries to guess the word.");
        boolean won = false;

        for(int i = 0; i < 6; i++) {
            System.out.println("Enter your guess for a 5-letter word.");
            String guess = input.nextLine();
            guess = guess.toLowerCase();
            validateGuess(guess, input);
            // if(guess.length() != 5) {
            //     System.out.println("Please only enter 5 letter words.");
            //     i--;
            //     continue;
            // }
            //if(isLineInFile("wordle-allowed-guesses.txt", guess) == -1 || isLineInFile(wordle-answers-alphabetical.txt", guess) == -1) {
            //    System.out.println("That isn't a word. Please only enter real words.");
            //    i--;
            //    continue;
            //}
            String output = testGuess(guess, answer);
            System.out.println(output);
            if(output.equals("#####")) {
                System.out.println("You win! You took " + i +" turns to guess the word " + answer + ".");
                won = true;
                break;
            }
        }
        if(!won) {
            System.out.println("You lose! The word was " + answer + "." );
        }
    }
    //correct letter, correct space: #; correct letter, incorrect space: ?; incorrect letter: X
    public static String testGuess(String guess, String answer) {
        String output = "";

        //adds each character of answer as an element of an arrayList
        ArrayList<String> answerChar = new ArrayList<String>();
        for(int i = 0; i < 5; i++) {
            answerChar.add(answer.substring(i, i + 1));
        }

        for(int i = 0; i < 5; i++) {
            //checks if arrayList contains each character of guess
            if(answerChar.contains(guess.substring(i, i + 1)) ) {
                //decides if guess is in correct location
                if(guess.substring(i, i + 1).equals(answer.substring(i, i + 1))) {
                    output += "#";
                }
                else {
                    output += "?";
                }
                //removes correct guess character from answer arrayList
                //this is done to prevent checking the same letter of answer multiple times
                answerChar.remove(answerChar.indexOf(guess.substring(i, i + 1)));
            }
            else {
                output += "X";
            }
        }
        return output;
    }

    public static String readLineFromFile(String filePath, int n) {
        String output = "";
        BufferedReader input;
        try {
            input = new BufferedReader( new FileReader(filePath));
            for(int i = 0; i < n; i++) {
                input.readLine();
            }
            output = input.readLine();
        } 
        catch (IOException i) {
        }
        if(output == null) {
            return "";
        }
        return output;
    }

    public static boolean isLineInFile(String filePath, String target) {
        boolean output = false;
        String input = "";
        BufferedReader buffy;
        try {
            buffy = new BufferedReader( new FileReader(filePath));
            while(input != null) {
                input = buffy.readLine();
                if(input == null) {
                    return false;
                }
                else if(input.equals(target)) {
                    return true;
                }
            }
        } 
        catch (IOException i) {
        }
        return output;
    }

    public static void validateGuess(String guess, Scanner input) {
        String output = guess;

        while(output.length() != 5) {
            System.out.println("[ERROR] You can only enter 5-letter words.");
            output = input.nextLine();
        }

        while(!isLineInFile("wordle-allowed-guesses.txt", output) && !isLineInFile("wordle-answers-alphabetical.txt", output)) {
            System.out.println("[ERROR] You can only enter existing words.");
            output = input.nextLine();
        }
    }

    public static int randInt(int min, int max) {
        return (int)(Math.random() * (max - min + 1) + min);
    }

    
}

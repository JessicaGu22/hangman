import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game {
    private Player player1;
    private Player player2;
    private Phrase phrase;
    private Board board;
    private Player currentPlayer;
    private boolean isSolved;

    public Game(String p1Name, String p2Name, String phraseText) {
        player1 = new Player(p1Name);
        player2 = new Player(p2Name);
        phrase = new Phrase(phraseText);
        board = new Board(phrase);
        currentPlayer = player1;
        isSolved = false;
    }

    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Phrase Solver!");
        while (!isSolved) {
            board.displayPhrase();
            System.out.println(currentPlayer.getName() + "'s turn. Your score: " + currentPlayer.getScore());
            System.out.println("Enter '1' to guess a letter or '2' to solve the puzzle:");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.println("Enter a letter:");
                char letter = scanner.nextLine().charAt(0);
                if (phrase.revealLetter(letter)) {
                    int points = new Random().nextInt(10) + 1;
                    currentPlayer.addScore(points);
                    System.out.println("Correct! You earned " + points + " points.");
                } else {
                    System.out.println("Wrong guess! Turn over.");
                    switchTurn();
                }
            } else if (choice == 2) {
                System.out.println("Enter your full phrase guess:");
                String attempt = scanner.nextLine();
                if (phrase.checkSolution(attempt)) {
                    System.out.println("Congratulations " + currentPlayer.getName() + "! You solved the puzzle.");
                    isSolved = true;
                } else {
                    System.out.println("Incorrect solution. Turn over.");
                    switchTurn();
                }
            }
        }
        declareWinner();
        scanner.close();
    }

    private void switchTurn() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }

    private void declareWinner() {
        Player winner = (player1.getScore() > player2.getScore()) ? player1 : player2;
        System.out.println("The winner is " + winner.getName() + " with " + winner.getScore() + " points!");
    }
}

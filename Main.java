import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Player 1 Name: ");
        String p1 = scanner.nextLine();

        System.out.print("Enter Player 2 Name: ");
        String p2 = scanner.nextLine();

        System.out.print("Enter the phrase to guess (hidden from players): ");
        String phrase = scanner.nextLine().toLowerCase();

        Game game = new Game(p1, p2, phrase);
        game.startGame();

        scanner.close();
    }
}

class Game {
    private String player1;
    private String player2;
    private String phrase;
    private StringBuilder guessedPhrase;
    private int player1Score;
    private int player2Score;
    private int turn; // 1 for player1, 2 for player2
    private Scanner scanner;

    public Game(String p1, String p2, String phrase) {
        this.player1 = p1;
        this.player2 = p2;
        this.phrase = phrase;
        this.guessedPhrase = new StringBuilder(phrase.replaceAll("[a-zA-Z]", "_"));
        this.player1Score = 0;
        this.player2Score = 0;
        this.turn = 1;
        this.scanner = new Scanner(System.in);
    }

    public void startGame() {
        System.out.println("\nGame starts now!");
        while (!isGameOver()) {
            displayGameStatus();
            playTurn();
            switchTurn();
        }
        displayWinner();
    }

    private void displayGameStatus() {
        System.out.println("\nCurrent Phrase: " + guessedPhrase);
        System.out.println(player1 + " Score: " + player1Score + " | " + player2 + " Score: " + player2Score);
        System.out.println("It's " + (turn == 1 ? player1 : player2) + "'s turn!");
    }

    private void playTurn() {
        System.out.println("Choose an option: ");
        System.out.println("1 - Guess a letter");
        System.out.println("2 - Solve the phrase");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (choice == 1) {
            System.out.print("Enter a letter: ");
            char guess = scanner.nextLine().toLowerCase().charAt(0);
            processLetterGuess(guess);
        } else if (choice == 2) {
            System.out.print("Enter the full phrase: ");
            String guess = scanner.nextLine().toLowerCase();
            processPhraseGuess(guess);
        } else {
            System.out.println("Invalid choice, try again.");
        }
    }

    private void processLetterGuess(char guess) {
        if (phrase.contains(String.valueOf(guess))) {
            System.out.println("Correct guess!");
            updateGuessedPhrase(guess);
            updateScore();
        } else {
            System.out.println("Incorrect guess, turn lost!");
        }
    }

    private void updateGuessedPhrase(char guess) {
        for (int i = 0; i < phrase.length(); i++) {
            if (phrase.charAt(i) == guess) {
                guessedPhrase.setCharAt(i, guess);
            }
        }
    }

    private void updateScore() {
        if (turn == 1) {
            player1Score += 10;
        } else {
            player2Score += 10;
        }
    }

    private void processPhraseGuess(String guess) {
        if (guess.equals(phrase)) {
            guessedPhrase = new StringBuilder(phrase);
        } else {
            System.out.println("Incorrect phrase! Turn lost.");
        }
    }

    private void switchTurn() {
        turn = (turn == 1) ? 2 : 1;
    }

    private boolean isGameOver() {
        return guessedPhrase.toString().equals(phrase);
    }

    private void displayWinner() {
        System.out.println("\nGame Over! The phrase was: " + phrase);
        if (player1Score > player2Score) {
            System.out.println(player1 + " wins with " + player1Score + " points!");
        } else if (player2Score > player1Score) {
            System.out.println(player2 + " wins with " + player2Score + " points!");
        } else {
            System.out.println("It's a tie!");
        }
    }
}

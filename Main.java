import java.util.Scanner;

class Player {
    private String name;
    private int score;

    public Player(String name) {
        this.name = name;
        this.score = 0;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void addPoints(int points) {
        score += points;
    }
}

class Game {
    private Player player1, player2, currentPlayer;
    private String phrase;
    private StringBuilder hiddenPhrase;
    private boolean gameOver;
    private Scanner scanner;

    public Game(String p1, String p2, String phrase) {
        this.player1 = new Player(p1);
        this.player2 = new Player(p2);
        this.currentPlayer = player1;
        this.phrase = phrase.toLowerCase();
        this.hiddenPhrase = new StringBuilder(phrase.replaceAll("[a-zA-Z]", "_"));
        this.gameOver = false;
        this.scanner = new Scanner(System.in);
    }

    public void startGame() {
        System.out.println("\nGame starts now!");
        while (!gameOver) {
            displayGameState();
            playTurn();
            switchPlayer();
        }
    }

    private void displayGameState() {
        System.out.println("\nCurrent Phrase: " + hiddenPhrase);
        System.out.println(player1.getName() + " Score: " + player1.getScore() + " | " +
                player2.getName() + " Score: " + player2.getScore());
        System.out.println("It's " + currentPlayer.getName() + "'s turn!");
    }

    private void playTurn() {
        String choice;
        do {
            System.out.println("Choose an option: ");
            System.out.println("1 - Guess a letter");
            System.out.println("2 - Solve the phrase");

            choice = scanner.nextLine().trim();
            if (!choice.equals("1") && !choice.equals("2")) {
                System.out.println("Invalid choice, please enter 1 or 2.");
            }
        } while (!choice.equals("1") && !choice.equals("2"));

        if (choice.equals("1")) {
            guessLetter();
        } else {
            solvePhrase();
        }
    }

    private void guessLetter() {
        System.out.print("Enter a letter: ");
        String guessInput = scanner.nextLine().toLowerCase().trim();

        if (guessInput.length() != 1 || !Character.isLetter(guessInput.charAt(0))) {
            System.out.println("Invalid input. Please enter a single letter.");
            return;
        }

        char guess = guessInput.charAt(0);

        boolean correct = false;
        for (int i = 0; i < phrase.length(); i++) {
            if (phrase.charAt(i) == guess && hiddenPhrase.charAt(i) == '_') {
                hiddenPhrase.setCharAt(i, guess);
                correct = true;
            }
        }

        if (correct) {
            System.out.println("Correct!");
            currentPlayer.addPoints(10);
            if (hiddenPhrase.toString().equals(phrase)) {
                gameOver = true;
                System.out.println("Congratulations! " + currentPlayer.getName() + " wins with "
                        + currentPlayer.getScore() + " points!");
            }
        } else {
            System.out.println("Incorrect guess.");
        }
    }

    private void solvePhrase() {
        System.out.print("Enter your solution: ");
        String guess = scanner.nextLine().trim().toLowerCase();

        if (guess.equals(phrase)) {
            System.out.println("Correct! " + currentPlayer.getName() + " wins!");
            currentPlayer.addPoints(50);
            gameOver = true;
        } else {
            System.out.println("Incorrect solution.");
        }
    }

    private void switchPlayer() {
        if (!gameOver) {
            currentPlayer = (currentPlayer == player1) ? player2 : player1;
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Player 1 Name: ");
        String p1 = scanner.nextLine();

        System.out.print("Enter Player 2 Name: ");
        String p2 = scanner.nextLine();

        System.out.println("Choose an option:");
        System.out.println("1. Enter a custom phrase");
        System.out.println("2. Use a random word from the wordlist");
        System.out.print("Enter the number of your choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        String phrase = "";

        if (choice == 1) {
            System.out.print("Enter the phrase to guess (hidden from players): ");
            phrase = scanner.nextLine();
        } else if (choice == 2) {
            phrase = WordProvider.getWord();

            if (phrase == null) {
                System.out.println("No words available in the wordlist!");
                return;
            }
        } else {
            System.out.println("Invalid choice! Exiting the game.");
            return;
        }
        Game game = new Game(p1, p2, phrase);
        game.startGame();

        scanner.close();
    }
}

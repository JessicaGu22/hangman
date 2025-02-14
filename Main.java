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
        System.out.println("Choose an option: ");
        System.out.println("1 - Guess a letter");
        System.out.println("2 - Solve the phrase");

        String choice = scanner.nextLine().trim();

        if (choice.equals("1")) {
            guessLetter();
        } else if (choice.equals("2")) {
            solvePhrase();
        } else {
            System.out.println("Invalid choice, please enter 1 or 2.");
        }
    }

    private void guessLetter() {
        System.out.print("Enter a letter: ");
        char guess = scanner.next().toLowerCase().charAt(0);
        scanner.nextLine(); // Consume newline

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

        System.out.print("Enter the phrase to guess (hidden from players): ");
        String phrase = scanner.nextLine();

        Game game = new Game(p1, p2, phrase);
        game.startGame();

        scanner.close();
    }
}

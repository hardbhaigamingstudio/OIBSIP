import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.ArrayList;

public class NumberGuessingGameGUI extends JFrame {

    private JTextField guessField;
    private JLabel messageLabel;
    private JLabel attemptsLabel;
    private JLabel roundLabel;

    private JButton guessButton;
    private JButton playAgainButton;

    private int secretNumber;
    private int attempts;
    private int maxAttempts;
    private int round = 1;
    private int maxNumber;

    private ArrayList<String> scoreHistory = new ArrayList<>();

    public NumberGuessingGameGUI() {

        setTitle("Number Guessing Game");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(7, 1));

        roundLabel = new JLabel("Round 1", SwingConstants.CENTER);
        add(roundLabel);

        messageLabel = new JLabel("Choose difficulty to start", SwingConstants.CENTER);
        add(messageLabel);

        guessField = new JTextField();
        add(guessField);

        guessButton = new JButton("Guess");
        add(guessButton);

        attemptsLabel = new JLabel("Attempts: 0", SwingConstants.CENTER);
        add(attemptsLabel);

        playAgainButton = new JButton("Play Again");
        add(playAgainButton);

        JButton scoreButton = new JButton("Show Score Summary");
        add(scoreButton);

        startNewRound();

        guessButton.addActionListener(e -> checkGuess());

        playAgainButton.addActionListener(e -> {
            round++;
            startNewRound();
        });

        scoreButton.addActionListener(e -> showScores());

        setVisible(true);
    }

    private void startNewRound() {

        String[] options = {
                "Easy (1-50, 10 Attempts)",
                "Medium (1-100, 7 Attempts)",
                "Hard (1-200, 5 Attempts)"
        };

        int choice = JOptionPane.showOptionDialog(
                this,
                "Select Difficulty",
                "Difficulty",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[1]
        );

        switch (choice) {
            case 0:
                maxNumber = 50;
                maxAttempts = 10;
                break;

            case 2:
                maxNumber = 200;
                maxAttempts = 5;
                break;

            default:
                maxNumber = 100;
                maxAttempts = 7;
        }

        secretNumber = new Random().nextInt(maxNumber) + 1;

        attempts = 0;

        roundLabel.setText("Round " + round);
        messageLabel.setText("Guess a number between 1 and " + maxNumber);
        attemptsLabel.setText("Attempts: 0 / " + maxAttempts);

        guessField.setText("");
        guessButton.setEnabled(true);
    }

    private void checkGuess() {

        try {

            int guess = Integer.parseInt(guessField.getText());

            attempts++;

            if (guess > secretNumber) {
                messageLabel.setText("Too High!");
            }
            else if (guess < secretNumber) {
                messageLabel.setText("Too Low!");
            }
            else {

                messageLabel.setText("Correct!");

                scoreHistory.add(
                        "Round " + round +
                        " - Guessed in " +
                        attempts + " attempts"
                );

                JOptionPane.showMessageDialog(
                        this,
                        "Correct! You guessed the number in "
                                + attempts + " attempts."
                );

                guessButton.setEnabled(false);
                return;
            }

            attemptsLabel.setText(
                    "Attempts: " + attempts +
                    " / " + maxAttempts
            );

            if (attempts >= maxAttempts) {

                JOptionPane.showMessageDialog(
                        this,
                        "You Lost!\nThe number was: " + secretNumber
                );

                scoreHistory.add(
                        "Round " + round +
                        " - Lost"
                );

                guessButton.setEnabled(false);
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid number."
            );
        }
    }

    private void showScores() {

        StringBuilder scores = new StringBuilder();

        if (scoreHistory.isEmpty()) {
            scores.append("No rounds played yet.");
        } else {
            for (String score : scoreHistory) {
                scores.append(score).append("\n");
            }
        }

        JOptionPane.showMessageDialog(
                this,
                scores.toString(),
                "Score Summary",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                NumberGuessingGameGUI::new
        );
    }
}
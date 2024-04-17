package main.start;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import java.awt.*;
import java.awt.event.*;

public class hardLevels10 implements ActionListener {
    private JTextField answerField1, answerField2, answerField3, answerField4, answerField5;
    private int currentLevel = 30;
    private JLabel timerLabel;
    private Timer timer; 
    private int secondsLeft = 30;

    private JLabel hintImageLabel; 
    private int hintClickCount = 0;

    private String[] hintMessages = {
        "S",
        "U"
    };

    private int hintMessageIndex = 0;
    
    public hardLevels10() {
        openGameWindow();
    }

    private void openGameWindow() {
        JFrame gameFrame = new JFrame("Picture Who?");
        gameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        gameFrame.addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            if (timer !=  null){
                timer.stop();
            }     
            }
        }
        );

        gameFrame.setSize(1920, 1200);
        gameFrame.setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(94,69,128));
        gameFrame.getContentPane().add(mainPanel);

        JPanel imagePanel = new JPanel(new GridLayout(1,1,20,20));
        imagePanel.setBackground(new Color(94,69,128));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        mainPanel.add(imagePanel, BorderLayout.CENTER);

        ImageIcon imageIcon1 = new ImageIcon("main\\img\\30.png");
        Image image1 = imageIcon1.getImage().getScaledInstance(500,  550, Image.SCALE_SMOOTH);
        ImageIcon scaledImageIcon1 = new ImageIcon(image1);
        JLabel imageLabel1 = new JLabel(scaledImageIcon1);
        imagePanel.add(imageLabel1);

        answerField1 = createSingleLetterTextField(gameFrame, answerField2);
        answerField2 = createSingleLetterTextField(gameFrame, answerField3);
        answerField3 = createSingleLetterTextField(gameFrame, answerField4);
        answerField4 = createSingleLetterTextField(gameFrame, answerField5);
        answerField5 = createSingleLetterTextField(gameFrame, null);

        JPanel answerPanel = new JPanel(new GridLayout(1, 4, 20, 20));
        answerPanel.setBackground(new Color(94, 69, 128));
        answerPanel.setBorder(BorderFactory.createEmptyBorder(20, 450, 10, 450));
        mainPanel.add(answerPanel, BorderLayout.SOUTH);

        answerPanel.add(answerField1);
        answerPanel.add(answerField2);
        answerPanel.add(answerField3);
        answerPanel.add(answerField4);
        answerPanel.add(answerField5);

        Border timeBorder = BorderFactory.createEmptyBorder(0, 430, 0, 100); 
        timerLabel = new JLabel("Time Left: " + secondsLeft);
        timerLabel.setHorizontalAlignment(SwingConstants.LEFT);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        timerLabel.setForeground(Color.WHITE);
        timerLabel.setBorder(timeBorder);

        timerLabel.setPreferredSize(new Dimension(10, 20));
        mainPanel.add(timerLabel, BorderLayout.NORTH);

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                secondsLeft--;
                if (secondsLeft >= 0) {
                    timerLabel.setText("Time Left: " + secondsLeft);
                } else {
                    timer.stop();
                    int choice = JOptionPane.showConfirmDialog(gameFrame, "Uh-oh! Time's up! What's your next move? Do you want to restart the level?", "Restart Level", JOptionPane.YES_NO_OPTION);
                    if (choice == JOptionPane.YES_OPTION) {
                        restartLevel();
                    } else {
                        gameFrame.dispose();
                        // this will return to main screen 
                        App mainScreen  = new App();
                        mainScreen.setVisible(true);
                    }
                }
            }
        });

        timer.start();
        addBackPanel(gameFrame);
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setVisible(true);
        addHintPanel(gameFrame);


    }

        private JTextField createSingleLetterTextField(JFrame gameFrame, JTextField nextField) {
            JTextField textField = new JTextField(3);
            textField.setHorizontalAlignment(JTextField.CENTER);
            textField.setFont(new Font("Arial", Font.BOLD, 30));
            textField.setForeground(new Color(94, 69, 128));
            textField.setBackground(new Color(211, 211, 211));
            Border lineBorder = BorderFactory.createLineBorder(new Color(94, 69, 128), 5, true);
            Border shadowBorder = BorderFactory.createLineBorder(new Color(0, 0, 0, 50), 10);
            Border compoundBorder = new CompoundBorder(lineBorder, shadowBorder);
            Dimension preferredSize = new Dimension(0, 50); // Adjust width and height as needed the box of the word
            textField.setPreferredSize(preferredSize);
        
        
            textField.setBorder(compoundBorder);
            Border border = BorderFactory.createLineBorder(new Color(0,0,0), 2, true);
            textField.setBorder(border);
            textField.addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    char inputChar = e.getKeyChar();
                    if (Character.isLetter(inputChar) && textField.getText().length() == 0) {
                        if (nextField != null) {
                            nextField.requestFocusInWindow();
                        }
                    }
                }
            });
        
            textField.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    checkAnswers(gameFrame);
                }
            });
        
            ((AbstractDocument) textField.getDocument()).setDocumentFilter(new DocumentFilter() {
                @Override
                public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                    String newText = textField.getText(0, offset) + text.toUpperCase() + textField.getText(offset + length, textField.getDocument().getLength() - offset - length);
                    if (newText.length() <= 1) {
                        super.replace(fb, offset, length, text.toUpperCase(), attrs);
                        if (nextField != null && text.matches("[a-zA-Z]")) {
                            nextField.requestFocusInWindow();
                        }
                    }
                }
            });
            
            return textField;
        }    

            // Method to check player answers and handle game progression based on correctness 
// Method to check player answers and handle game progression based on correctness 
private void checkAnswers(JFrame gameFrame) {
    String enteredAnswer1 = answerField1.getText().trim().toLowerCase();
    String enteredAnswer2 = answerField2.getText().trim().toLowerCase();
    String enteredAnswer3 = answerField3.getText().trim().toLowerCase();
    String enteredAnswer4 = answerField4.getText().trim().toLowerCase();
    String enteredAnswer5 = answerField5.getText().trim().toLowerCase();

    String correctAnswer1 = "s";
    String correctAnswer2 = "u";
    String correctAnswer3 = "s";
    String correctAnswer4 = "h";
    String correctAnswer5 = "i";

    if (enteredAnswer1.equals(correctAnswer1) &&
            enteredAnswer2.equals(correctAnswer2) &&
            enteredAnswer3.equals(correctAnswer3) &&
            enteredAnswer4.equals(correctAnswer4) &&
            enteredAnswer5.equals(correctAnswer5)) {
        // Stop the timer
        timer.stop();
        // Show a message indicating correct answer
        String message = ("<html><div style='text-align: center; margin-left: 60px; margin-right: 60px;'>" +
        "<span style='font-family: Paytone One; font-size: 20px; color: #443C3C;'>SUSHI!</span><br>" +
        "<span style='font-size: 27px; color: #5E4580;'>Brilliant</span></div><br><br>" +
        "<div style='font-family: Paytone One; font-size: 12px; margin-left: 60px; margin-right: 60px; text-align: center;'>" +
        "Congratulations! You've completed all the hard levels!<br>" +
        "You're a champ! Ready for the next challenge? Click 'Next' to choose your difficulty level and embark on your next adventure.<br>" +
        "You can always come back to the hard mode, easy mode, or moderate mode. Keep the fun going!</div></html>");
        // Create a custom option pane
        JOptionPane optionPane = new JOptionPane(message, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
        // Set the button text to "NEXT"
        optionPane.setOptions(new Object[]{"NEXT"});
        // Create a dialog with the custom option pane
        JDialog dialog = optionPane.createDialog(gameFrame, "Next Level Unlocked!");
        // Show the dialog
        dialog.setVisible(true);
        // Close the current game window
        gameFrame.dispose();
        // Open the next level
        openNextLevel();
    } else {
        JOptionPane.showMessageDialog(gameFrame, "Incorrect!");
    }
    // Reset the answer fields
    answerField1.setText("");
    answerField2.setText("");
    answerField3.setText("");
    answerField4.setText("");
    answerField5.setText("");
    // Set focus to the first answer field
    answerField1.requestFocusInWindow();
}
    
private void openDifficultyWindow() {

    // first, we create the new window (JFrame)
    JFrame difficultyFrame = new JFrame("Select Difficulty");

    // its sized and designed appropriately 
    difficultyFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    difficultyFrame.setSize(1920, 1200);
    difficultyFrame.setResizable(false);
    
    // a panel is created to hold the difficulty level buttons
    JPanel difficultyPanel = new JPanel(new GridLayout(1, 0));
    difficultyPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

    
       // Here we start creating the buttons for different difficulty levels ("Easy", "Moderate", "Hard")
    // Buttons for different difficulty levels
    JButton easyButton = new JButton("Easy");
    easyButton.setFont(new Font("segoe ui black", Font.BOLD, 35));
    easyButton.setForeground(Color.decode("#5E4580"));
    easyButton.setBackground(Color.WHITE);

    JButton moderateButton = new JButton("Moderate");
    moderateButton.setFont(new Font("segoe ui black", Font.BOLD, 35));
    moderateButton.setForeground(Color.decode("#5E4580"));
    moderateButton.setBackground(Color.WHITE);

    JButton hardButton = new JButton("Hard");
    hardButton.setFont(new Font("segoe ui black", Font.BOLD, 35));
    hardButton.setForeground(Color.decode("#5E4580"));
    hardButton.setBackground(Color.WHITE);


    // Each button is assigned an ActionListener - this is the code that runs when the button is clicked.
    easyButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            // Dispose the current window
            difficultyFrame.dispose();
            // Open the Easy level
            new easyLevels();
        }
    });
    
    moderateButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            // Dispose the current window
            difficultyFrame.dispose();
            // Open the Moderate level
            new moderateLevels();
        }
    });
    
    hardButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            // Dispose the current window
            difficultyFrame.dispose();
            // Open the Hard level
            new hardLevels();
        }
    });
    
    // Now we're adding all the buttons to the panel
    difficultyPanel.add(easyButton);
    difficultyPanel.add(moderateButton);
    difficultyPanel.add(hardButton);

    // The panel is then added to the new window (JFrame)
    difficultyFrame.add(difficultyPanel);

       // The JFrame position is set and finally, we make it visible to the user.
    difficultyFrame.setLocationRelativeTo(null);
    difficultyFrame.setVisible(true);
}


    // Method to open the next level of the game
// Method to open the next level of the game
private void openNextLevel() {
    currentLevel++;
    openDifficultyWindow(); // Open the difficulty selection window
}    


    // Method to restart the current level of the game
    private void restartLevel() {
        new hardLevels10();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        openGameWindow();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new hardLevels10();
            }
        });
    }

    private void addBackPanel(JFrame gameFrame) {
        ImageIcon backIcon = new ImageIcon("main\\img\\red.png");
        Image backImage = backIcon.getImage().getScaledInstance(130, 60, Image.SCALE_SMOOTH);    
        ImageIcon scaledIcon = new ImageIcon(backImage);
        JLabel backImageLabel = new JLabel(scaledIcon);
        backImageLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backImageLabel.setToolTipText("Click to go back");
        backImageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int choice = JOptionPane.showConfirmDialog(gameFrame, "Are you sure you want to go back to the main screen?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    // Dispose the current frame
                    gameFrame.dispose();
                    // Create a new instance of the main screen frame
                    App mainScreen = new App();
                    mainScreen.setVisible(true);
                }
            }
        });

        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Align the back button to the right
        backPanel.setBackground(new Color(94, 69, 128));
        backPanel.add(backImageLabel);
        gameFrame.getContentPane().add(backPanel, BorderLayout.NORTH); // Align the backPanel to the top of the gameFrame;
    }
    
    
    private void addHintPanel(JFrame gameFrame) {
        ImageIcon hintIcon = new ImageIcon("main\\img\\not.png");
        Image hintImage = hintIcon.getImage().getScaledInstance(300, 150, Image.SCALE_SMOOTH);
        ImageIcon scaledHintIcon = new ImageIcon(hintImage);
        hintImageLabel = new JLabel(scaledHintIcon);
        hintImageLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        hintImageLabel.setToolTipText("Click for hint");
        hintImageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (hintClickCount < hintMessages.length) {
                    timer.stop();
                    int choice = JOptionPane.showConfirmDialog(gameFrame, "Do you want to use a hint?", "Hint System", JOptionPane.YES_NO_OPTION);
                    if (choice == JOptionPane.YES_OPTION) {
                        String hintMessage;
                        if (hintClickCount == 0) {
                            hintMessage = "Need a hint? Use your wisdom wisely! You have 2 hint trials remaining. Each hint deducts 5 seconds from your remaining time. Choose and think wisely to uncover the hidden word!";
                        } else {
                            hintMessage = "Need a hint? Use your wisdom wisely! You have 1 hint trial remaining. Each hint deducts 5 seconds from your remaining time. Choose and think wisely to uncover the hidden word!";
                        }
                        JOptionPane.showMessageDialog(gameFrame, hintMessage);
                        JOptionPane.showMessageDialog(gameFrame, "Hint Letters: " + hintMessages[hintMessageIndex].charAt(0));
                        hintClickCount++;
                        if (hintClickCount == hintMessages.length) {
                            hintImageLabel.setEnabled(false);
                            hintImageLabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                        }
                        // Deduct 5 seconds for each hint click
                        secondsLeft = Math.max(0, secondsLeft - 5);
                        timerLabel.setText("Time Left: " + secondsLeft);
                        hintMessageIndex = (hintMessageIndex + 1) % hintMessages.length;
                    }
                    timer.start();
                } else {
                    JOptionPane.showMessageDialog(gameFrame, "Uh-oh! You have no more hint trials.");
                }
            }
        });
    

        JPanel hintPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        hintPanel.setBackground(new Color(94, 69, 128));
        hintPanel.add(hintImageLabel);
        gameFrame.getContentPane().add(hintPanel, BorderLayout.SOUTH);
    }
}



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

public class moderateLevels9 implements ActionListener {

    // Text fields for user input
    private JTextField answerField1, answerField2, answerField3, answerField4, answerField5, answerField6, answerField7;
    private int currentLevel = 19;
    // Label to display remaining time 
    private JLabel timerLabel;
// Timer for the game countdown and managing time-related events
    private Timer timer;
    // Seconds left for each level
    private int secondsLeft = 25; 


// Hint system variables and methods for providing hints to players
    private JLabel hintImageLabel;
    // Counter for hint usage  
    private int hintClickCount = 0; 
    
    
    // Array of hint messages
    private String[] hintMessages = {
        "L",
        "A" };

     // Index for cycling through hint messages
    private int hintMessageIndex = 0;

    // Constructor to initialize game 
    public moderateLevels9() {
        openGameWindow(); // Initialize the game window 
    }

    // Method to create and display the game window 
    private void openGameWindow() {
        JFrame gameFrame = new JFrame("Picture Who");
        gameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        gameFrame.addWindowListener(new WindowAdapter() {
            @Override 
            public void windowClosing(WindowEvent e) {
                // stop the timer when closing the windows
                if (timer != null){
                    timer.stop();
                }
            }
        });

        gameFrame.setSize(1920, 1200);
        gameFrame.setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(94, 69, 128));
        gameFrame.getContentPane().add(mainPanel); // The main pane to frame 

        JPanel imagePanel = new JPanel(new GridLayout(1, 1, 20, 20));
        imagePanel.setBackground(new Color(94, 69, 128));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        mainPanel.add(imagePanel, BorderLayout.CENTER);

        
        // load and display image 
        //the setup PictureWho-main\\img\\
        ImageIcon imageIcon1 = new ImageIcon("main\\img\\19.png");
        Image image1 = imageIcon1.getImage().getScaledInstance(500,  550, Image.SCALE_SMOOTH);
        ImageIcon scaledImageIcon1 = new ImageIcon(image1);
        JLabel imageLabel1 = new JLabel(scaledImageIcon1);
        imagePanel.add(imageLabel1);

        // Create text fields for answer 
        answerField1 = createSingleLetterTextField(gameFrame, answerField2);
        answerField2 = createSingleLetterTextField(gameFrame, answerField3);
        answerField3 = createSingleLetterTextField(gameFrame, answerField4);
        answerField4 = createSingleLetterTextField(gameFrame, answerField5);
        answerField5 = createSingleLetterTextField(gameFrame, answerField6);
        answerField6 = createSingleLetterTextField(gameFrame, null);
        
        // Panel to hold answer text fields 
        JPanel answerPanel = new JPanel(new GridLayout(1, 4, 20, 20));
        answerPanel.setBackground(new Color(94, 69, 128));
        answerPanel.setBorder(BorderFactory.createEmptyBorder(20, 350, 10, 350));
        mainPanel.add(answerPanel, BorderLayout.SOUTH);
        
        answerPanel.add(answerField1);
        answerPanel.add(answerField2);
        answerPanel.add(answerField3);
        answerPanel.add(answerField4);
        answerPanel.add(answerField5);
        answerPanel.add(answerField6);
        
        // Label to display remaining time 
        Border timeBorder = BorderFactory.createEmptyBorder(0, 430, 0, 100); 
        timerLabel = new JLabel("Time Left: " + secondsLeft);
        timerLabel.setHorizontalAlignment(SwingConstants.LEFT);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        timerLabel.setForeground(Color.WHITE);
        timerLabel.setBorder(timeBorder);
        
        // Set the size of the timer label explicitly
        timerLabel.setPreferredSize(new Dimension(10, 20));
        
        mainPanel.add(timerLabel, BorderLayout.NORTH);

        // timer to count donwn the time 
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

        addBackPanel(gameFrame); // Add back button panel here

        gameFrame.setLocationRelativeTo(null);
        gameFrame.setVisible(true);
        // add the hint panel 
        addHintPanel(gameFrame);
    }

    // Method to create a single-letter input text field with specific styling and behavior
    private JTextField createSingleLetterTextField(JFrame gameFrame, JTextField nextField) {
        // Create text field for single letter input
        JTextField textField = new JTextField(4);
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
    private void checkAnswers(JFrame gameFrame) {
        String enteredAnswer1 = answerField1.getText().trim().toLowerCase();
        String enteredAnswer2 = answerField2.getText().trim().toLowerCase();
        String enteredAnswer3 = answerField3.getText().trim().toLowerCase();
        String enteredAnswer4 = answerField4.getText().trim().toLowerCase();
        String enteredAnswer5 = answerField5.getText().trim().toLowerCase();
        String enteredAnswer6 = answerField6.getText().trim().toLowerCase();

    
        String correctAnswer1 = "l";
        String correctAnswer2 = "a";
        String correctAnswer3 = "p";
        String correctAnswer4 = "t";
        String correctAnswer5 = "o";
        String correctAnswer6 = "p";

    
        if (enteredAnswer1.equals(correctAnswer1) &&
                enteredAnswer2.equals(correctAnswer2) &&
                enteredAnswer3.equals(correctAnswer3) &&
                enteredAnswer4.equals(correctAnswer4) &&
                enteredAnswer5.equals(correctAnswer5) &&
                enteredAnswer6.equals(correctAnswer6)) {
            timer.stop();
            String message = ("<html><div style='text-align: center; margin-left: 60px; margin-right: 60px;'><span style='font-family: Paytone One; font-size: 20px; color: #443C3C;'>LAPTOP!</span><br><span style='font-size: 27px; color: #5E4580;'>Brilliant</span></div></html>");

            // Create a custom option pane
            JOptionPane optionPane = new JOptionPane(message, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
            
            // Set the button text to "NEXT"
            optionPane.setOptions(new Object[]{"NEXT"});
                    

            // Create a dialog with the custom option pane
            JDialog dialog = optionPane.createDialog(gameFrame, "Next Level Unlocked!");
    
            // Show the dialog
            dialog.setVisible(true);
    
            gameFrame.dispose();
            openNextLevel();
        } else {
            JOptionPane.showMessageDialog(gameFrame, "Incorrect!");
        }
        answerField1.setText("");
        answerField2.setText("");
        answerField3.setText("");
        answerField4.setText("");
        answerField5.setText("");
        answerField6.setText("");
        answerField1.requestFocusInWindow();
    }
    
    // Method to open the next level of the game
    private void openNextLevel() {
        currentLevel++;
        new moderateLevels10();
    }

    // Method to restart the current level of the game
    private void restartLevel() {
        new moderateLevels9();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        openGameWindow();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new moderateLevels9();
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

    // Removed setPreferredSize line

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

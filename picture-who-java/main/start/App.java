package main.start;

// import necessary packages
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

// import for border styles 
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.FlowLayout;


// main class extending JFrame 
public class App extends JFrame {


    private JLabel imageLabel; // declare JLabel for the image

    public App() {
        // set frame properties 
        setSize(1920, 1200);
        setTitle("Picture Who");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(new Color(131, 101, 172));
        setLayout(new BorderLayout());
        

        // create a panel for the top section 
        JPanel topPanel = new JPanel(new BorderLayout());

        // create a custom image icon
        ImageIcon customIcon = new ImageIcon("#"); 
        

        // create a style the title label
        JLabel pictureWhoLabel = new JLabel("Picture Who?");
        pictureWhoLabel.setHorizontalAlignment(JLabel.CENTER);
        pictureWhoLabel.setFont(new Font("segoe ui black", Font.BOLD, 79));
        pictureWhoLabel.setForeground(Color.decode("#E7E4DB"));
        pictureWhoLabel.setBackground(Color.decode("#5E4580"));
        pictureWhoLabel.setOpaque(true);
        pictureWhoLabel.setIcon(customIcon);
        int topPadding = 80;
        int leftPadding = 0;
        int bottomPadding = 90;
        int rightPadding = 0;
        pictureWhoLabel.setBorder(BorderFactory.createEmptyBorder(topPadding, leftPadding, bottomPadding, rightPadding));


        // add the title to the top panel 
        topPanel.add(pictureWhoLabel, BorderLayout.NORTH);
        // add the top panel to the main frame (north position)
        add(topPanel, BorderLayout.NORTH);


        // create a panel for the buttons, the 3 buttons, play, quit, and ssettings
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 50));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(-50, 430, -50, 430));
        buttonPanel.setBackground(Color.decode("#5E4580"));
        Color buttonPanelColor = Color.decode("#E7E4DB");
        

        // create and style the Play button
        JButton playButton = new JButton("Play");
        playButton.setFont(new Font("segoe ui black", Font.BOLD, 35));
        playButton.setForeground(Color.decode("#5E4580"));
        playButton.setPreferredSize(new Dimension(200,80));
        playButton.setBackground(Color.WHITE);
        playButton.setForeground(Color.decode("#5E4580"));
        playButton.setBackground(buttonPanelColor); 

        // add action listener for play button once you click it it will automatically gp the openDifficultyWindow
        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openDifficultyWindow();
            }
        });
        buttonPanel.add(playButton);
    
        // Settings button once it click it will automatically go to the settings of this application
        JButton settingsButton = new JButton("Settings");
        settingsButton.setFont(new Font("segoe ui black", Font.BOLD, 35));
        settingsButton.setForeground(Color.decode("#5E4580"));
        settingsButton.setPreferredSize(new Dimension(200,80));
        settingsButton.setBackground(Color.WHITE);
        settingsButton.setBackground(buttonPanelColor);
        buttonPanel.add(settingsButton);
    
        // Exit button once it click it will automatically go to the exit of this application 
        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("segoe ui black", Font.BOLD, 35));
        exitButton.setForeground(Color.decode("#5E4580"));
        exitButton.setBackground(Color.WHITE);
        exitButton.setBackground(buttonPanelColor);
        exitButton.setPreferredSize(new Dimension(200,80));


        // exit confirmation listenere
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // thid displaying the confirmation dialog when the exitButton clicked
                int choice = JOptionPane.showConfirmDialog(App.this, "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    // this closing the application if user confirms to exit
                    dispose();
                }
            }
        });
        // adding the exitbutton to our collective panel for buttons
        buttonPanel.add(exitButton);
        // the borderlayout position
        add(buttonPanel, BorderLayout.CENTER);

        // Now, let's add a panel at the bottom for future expansion 
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(Color.decode("#5E4580"));
        // Adding this bottom panel to the southern area of the border layout
        add(bottomPanel, BorderLayout.SOUTH);
        // This sets the location of the window relative to the specified component. Passing in 'null' centers the window on screen.
        setLocationRelativeTo(null);
    }


    // This method is responsible for creating a new window to select the game's difficulty.
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
                
                // Shows a message to the user indicating an "Easy" game has been selected
                JOptionPane.showMessageDialog(difficultyFrame, "Easy difficulty selected", "Level Selected", getDefaultCloseOperation());
                // Close the difficultyFrame window
                difficultyFrame.dispose();
                
                // And begins a new game at the "Easy" difficulty
                new easyLevels();
            }
        });
        //  Same steps repeated for the moderate and hard levels
        moderateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(difficultyFrame, "Moderate difficulty selected");
                difficultyFrame.dispose();
                new moderatelevel1();
            }
        });

        hardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(difficultyFrame, "Hard difficulty selected");
                difficultyFrame.dispose();
                new hardlevel1();
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

    // Method to load custom font with specified style and size
    private Font loadFont(String path, int style, float size) {
        try {
            // this create a file object with the path 
            File file = new File(path);
            // this create a font object from the file, with specific style and size
            return Font.createFont(Font.TRUETYPE_FONT, file).deriveFont(style, size);
        } catch (IOException | FontFormatException e) {
            // Print the exception stack trace if an error occurs
            e.printStackTrace();
            // If errors occurred in creating Font, return a default sans-serif Font with the specified size
            return new Font(Font.SANS_SERIF, Font.PLAIN, (int) size);
        }
    }

    public static void main(String[] args) {
        // We start the app
        SwingUtilities.invokeLater(new Runnable() {
            // We create the app and make it visible
            public void run() {
                App game = new App();
                game.setVisible(true);
            }
        });
    }
}
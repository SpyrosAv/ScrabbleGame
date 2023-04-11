/*
    AVOURIS SPYRIDON 
 */
package wordgame_new_;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import javax.swing.*;
import java.util.HashMap;
import java.util.Set;
import java.util.Iterator;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author spiros
 */
public class WordGame extends JFrame {

    private JPanel mainPanel;
    private JPanel selectedPanel;
    private JButton[][] buttons;
    private JLabel[] selectedLabels;
    private int selectedIndex;
    private HashMap<Character, Integer> letterPoints;
    private JLabel pointsLabel;
    private int points, totalPoints;
    // Variables to store the last button pressed
    private int lastX, lastY;

    public WordGame() {

        letterPoints = new HashMap<Character, Integer>();

        letterPoints.put('Α', 1);
        letterPoints.put('Β', 8);
        letterPoints.put('Γ', 4);
        letterPoints.put('Δ', 4);
        letterPoints.put('Ε', 1);
        letterPoints.put('Ζ', 8);
        letterPoints.put('Η', 1);
        letterPoints.put('Θ', 8);
        letterPoints.put('Ι', 1);
        letterPoints.put('Κ', 2);
        letterPoints.put('Λ', 3);
        letterPoints.put('Μ', 3);
        letterPoints.put('Ν', 1);
        letterPoints.put('Ξ', 10);
        letterPoints.put('Ο', 1);
        letterPoints.put('Π', 2);
        letterPoints.put('Ρ', 2);
        letterPoints.put('Σ', 1);
        letterPoints.put('Τ', 1);
        letterPoints.put('Υ', 2);
        letterPoints.put('Φ', 8);
        letterPoints.put('Χ', 10);
        letterPoints.put('Ψ', 10);
        letterPoints.put('Ω', 3);

        // main panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(5, 6));
        buttons = new JButton[5][5];

        // menu bar
        JMenuBar menuBar = new JMenuBar();

//  File menu
        JMenu fileMenu = new JMenu("Μενού");
        JMenu tools = new JMenu("Εργαλεία");

// New item
        JMenuItem newItem = new JMenuItem("Οδηγίες");
        newItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                Manual man1 = new Manual();
            }
        });
        fileMenu.add(newItem);

// Exit item
        JMenuItem exitItem = new JMenuItem("Έξοδος");
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });
        fileMenu.add(exitItem);

        // Add the File menu to the menu bar
        menuBar.add(fileMenu);
        menuBar.add(tools);

        // Set the menu bar for the frame
        setJMenuBar(menuBar);

        // Create an array of all the letters
        Character[] letters = letterPoints.keySet().toArray(new Character[letterPoints.size()]);

        // Shuffle the array of letters
        Random random = new Random();
        for (int i = letters.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            Character temp = letters[index];
            letters[index] = letters[i];
            letters[i] = temp;
        }

        // Get the keys from the map and use them to create the buttons
        int i = 0;
        int j = 0;

        int redCount = 0, blueCount = 0, yellowCount = 0;

        for (Character letter : letters) {

            int points = letterPoints.get(letter);
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
            panel.setBackground(Color.white);
            JLabel letterLabel = new JLabel("" + letter);
            letterLabel.setFont(new Font("Calibri", Font.PLAIN, 26));
            JLabel pointsLabel = new JLabel(" " + points);
            pointsLabel.setFont(new Font("Calibri", Font.PLAIN, 15));

            panel.add(letterLabel);
            panel.add(pointsLabel);
            buttons[i][j] = new JButton();
            buttons[i][j].setBackground(Color.WHITE);
            buttons[i][j].setLayout(new BorderLayout());
            buttons[i][j].add(panel, BorderLayout.CENTER);

            buttons[i][j].addActionListener(new ButtonListener());
            mainPanel.add(buttons[i][j]);

            // Add code to randomly change the background color of a maximum of two panels to red
            if (redCount < 2) {
                Random random1 = new Random();
                if (random1.nextInt(5) == 0) {
                    panel.setBackground(Color.RED);
                    redCount++;
                }
            }

            if (blueCount < 3) {
                Random random2 = new Random();
                if (random2.nextInt(5) == 0) {
                    panel.setBackground(Color.CYAN);
                    blueCount++;
                }
            }

            j++;
            if (j > 4) {
                i++;
                j = 0;
            }
        }
        // Set up the panel to display the selected letters
        selectedPanel = new JPanel();
        selectedPanel.setLayout(new FlowLayout());
        JLabel label = new JLabel("Γράμματα: ");
        selectedPanel.add(label);
        selectedLabels = new JLabel[20];

        for (int k = 0; k < 20; k++) {
            selectedLabels[k] = new JLabel();
            selectedPanel.add(selectedLabels[k]);
        }
        pointsLabel = new JLabel("Συνολικό σκορ: 0");
        selectedPanel.add(pointsLabel);

        // Add the Undo button
        JButton undoButton = new JButton("Αναίρεση");

        undoButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                if (selectedIndex > 0) {
                    // Remove the last selected letter from the panel
                    selectedPanel.remove(--selectedIndex);
                    selectedPanel.revalidate();
                    selectedPanel.repaint();

                    // Add the points back to the total
                    char letter = selectedLabels[selectedIndex].getText().charAt(0);
                    int points = letterPoints.get(letter);
                    WordGame.this.points -= points;
                    pointsLabel.setText("Total points: " + (WordGame.this.points));
                }
            }
        });
        selectedPanel.add(undoButton);

        // Create the check button
        JButton checkButton = new JButton("Έλεγχος λέξης");
        checkButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                String selectedWord = "";
                int pointsHere = 0;
                for (int i = 0; i < selectedLabels.length; i++) {
                    selectedWord += selectedLabels[i].getText();
                   // pointsHere += letterPoints.get(selectedLabels[selectedIndex].getText().charAt(0));
                }
                try {
                    BufferedReader br = new BufferedReader(new FileReader("words.txt"));
                    String line;
                    boolean found = false;
                    while ((line = br.readLine()) != null) {
                        if (line.equalsIgnoreCase(selectedWord)) {
                            JOptionPane.showMessageDialog(null, "Συγχαρητήρια! Η λέξη " + selectedWord
                                    + " βρίσκεται στο αρχείο.");
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        JOptionPane.showMessageDialog(null, "Λυπούμαστε. Η λέξη δεν βρίσκεται στο αρχείο.");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        selectedPanel.add(checkButton);

        // Add the main panel and the selected panel to the frame
        add(mainPanel, BorderLayout.CENTER);
        add(selectedPanel, BorderLayout.SOUTH);

        // Set up the frame
        setTitle("The Word Game");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
        setVisible(true);

        // Set the last button pressed as not pressed
        lastX = -1;
        lastY = -1;

    }

// Inner class for button listeners
    private class ButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            JButton button = (JButton) event.getSource();
            // Add the letter and its corresponding points to the selectedLabels array
            JPanel panel = (JPanel) button.getComponent(0);
            JLabel letterLabel = (JLabel) panel.getComponent(0);
            button.setBackground(Color.yellow);

            // Get the x and y coordinates of the button
            int x = -1;
            int y = -1;
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (buttons[i][j] == button) {
                        x = i;
                        y = j;
                        break;
                    }
                }
            }

            // Check if the button pressed is a neighbour of the last button pressed
            if (lastX == -1 || lastY == -1 || (Math.abs(x - lastX) <= 1 && Math.abs(y - lastY) <= 1)) {
                // Update the last button pressed
                lastX = x;
                lastY = y;

                // Add the letter to the selected labels
                char letter = letterLabel.getText().charAt(0);
                int points = letterPoints.get(letter);

                // Check if the panel has a red background color and double the points if it does
                if (panel.getBackground() == Color.red) {
                    points *= 2;
                }

                selectedLabels[selectedIndex].setText("" + letter);
                selectedIndex++;
                // Update the total points
                WordGame.this.points += points;
                pointsLabel.setText("Total points: " + WordGame.this.points);

                // Disable the button and change its color
                button.setEnabled(false);
                button.setContentAreaFilled(true);

            } else {
                // Ενημέρωση του χρήστη για τον κανόνα γειτνίασης
                int choice = JOptionPane.showOptionDialog(null, "Μπορείτε να πατήσετε μόνο κουμπιά "
                        + "πλησιέστερα από το τελευταίο που πατήσατε!"
                        + " Θέλετε να ακυρώσετε την τελευταία επιλογή;", "Λάθος Κουμπί", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, new Object[]{"Ναί", "Όχι"}, "Όχι");
                if (choice == JOptionPane.YES_OPTION) {
// Re-enable the last button pressed" 
                    buttons[lastX][lastY].setEnabled(false);
                    // Remove the last letter from the selected labels
                    selectedLabels[selectedIndex].setText("");
                    button.setBackground(Color.white);

                } else {
                    JOptionPane.showMessageDialog(null, "Λυπούμαστε. Δεν μπορείτε να συνεχίσετε.");
                    button.setBackground(Color.white);
                }
            }
        }
    }
}

/*
    AVOURIS SPYRIDON
 */
package wordgame_new_;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 *
 * @author spiros
 */
public class Manual extends JFrame {

    private Container c;
// The main panel for the game
    private JPanel mainPanel;
    private JTextArea rulesText;

    public Manual() {
        setResizable(false);

        c = getContentPane();
        GridLayout layout = new GridLayout(2, 1);
        c.setLayout(layout);
        FlowLayout flowlayout = new FlowLayout();

        // Add a JTextArea to display the rules
        rulesText = new JTextArea();
        rulesText.setText("Το πρόγραμμα αποτελεί ένα παιχνίδι επιλογής γραμμάτων τύπου Scrabble. "
                + "Ο χρήστης επιλέγει ένα γράμμα από τον πίνακα κουμπιών, τηρώντας τον κανόνα γειτνίασης. Δηλαδή, "
                + "κάθε επόμενο γράμμα θα πρέπει να βρίσκεται πάνω/κάτω/δεξιά/αριστερά ή διαγώνια του τελευταίου γράμματος."
                + " Επίσης, ο χρήστης μπορεί να βλέπει τη σειρά των γραμμάτων που επέλεξε και τους συνολικούς πόντους "
                + "που έχει μαζέψει στο κάτω μέρος της οθόνης. Εφόσον σχηματίσει μια λέξη, μπορεί να πατήσει το κουμπί "
                + "\"'Ελεγχος λέξης\" για να αναζητηθεί αντιστοιχία με τις λέξεις του αρχείου. Τα γράμματα με κόκκινο χρώμα "
                + "είναι γράμματα διπλής αξίας, ενώ αυτά με μπλε χρώμα διπλασιάζουν την αξία της λέξης. Καλή διασκέδαση!");
        rulesText.setFont(new Font("Calibri", Font.BOLD, 15));
        rulesText.setLineWrap(true);
        rulesText.setWrapStyleWord(true);
        rulesText.setEditable(false);
        c.add(rulesText);
        // Set up the frame
        setTitle("The WordGame");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
        setVisible(true);

    }
}

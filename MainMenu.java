/*
    AVOYRIS SPYRIDON 
    321/2017001
 */
package wordgame_new_;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Map;
/**
 *
 * @author spiros
 */
class MainMenu extends JFrame implements ActionListener {

    Map<Character, Integer> letters;

    private Container c;

    private JLabel title, name, confirm;
    private JTextField tname;
    private JButton sub;
    private JPanel row1, row2, row3, row4;

    public MainMenu() {
        setTitle(" The Word Game");
        //setBounds(800, 400, 700, 300);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {

            //Otan o xrhsths pathsei to koumpi gia to kleisimo tou parathirou
            //emfanizetai to parakatw parathiro vevaiwshs
            public void windowClosing(WindowEvent e) {
                JFrame frame = (JFrame) e.getSource();

                int result = JOptionPane.showConfirmDialog(
                        frame,
                        "Πατήστε 'Yes' για αποθήκευση νέων δεδομένων",
                        "Έξοδος",
                        JOptionPane.YES_NO_OPTION);

                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });
        setResizable(false);

        c = getContentPane();
        GridLayout layout = new GridLayout(5, 1);
        c.setLayout(layout);
        FlowLayout flowlayout = new FlowLayout();

        row1 = new JPanel();
        title = new JLabel("Γειά σας! Παρακαλώ εισάγετε όνομα παίκτη:");
        title.setFont(new Font("Calibri", Font.PLAIN, 15));

        row2 = new JPanel();
        name = new JLabel("Όνομα:", JLabel.RIGHT);
        name.setFont(new Font("Calibri", Font.PLAIN, 14));
        tname = new JTextField(10);

        row3 = new JPanel();
        sub = new JButton("'Εναρξη παιχνιδιού");
        sub.addActionListener(this);

        row4 = new JPanel();
        confirm = new JLabel("");
        confirm.setForeground(Color.red);
        confirm.setFont(new Font("Arial", Font.CENTER_BASELINE, 15));

        row1.setLayout(flowlayout);
        row1.add(title);
        c.add(row1);

        row2.setLayout(flowlayout);
        row2.add(name);
        row2.add(tname);
        c.add(row2);

        row3.setLayout(flowlayout);
        row3.add(sub);
        c.add(row3);

        row4.setLayout(flowlayout);
        row4.add(confirm);
        c.add(confirm);

        setContentPane(c);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == sub) {
            if (!tname.getText().equals("")) {
                confirm.setText(" \tΕπιτυχής Καταχώρηση!");
                WordGame g1 = new WordGame();

            } else {
                confirm.setText(" \tΣυμπληρώστε το όνομα παίχτη.");

            }
        }
    }

    public static void main(String[] args) {
        MainMenu main = new MainMenu();

    }

}

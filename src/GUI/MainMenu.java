package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainMenu extends JFrame {

    public MainMenu() {
        setTitle("MathLab");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1, 10, 10));


        getContentPane().setBackground(new Color(173, 216, 230));

        JLabel label = new JLabel("یک گزینه را انتخاب کنید♥", SwingConstants.CENTER);
        add(label);


        JButton bigNumberBtn = new JButton("ماشین حساب اعداد صحیح ");
        bigNumberBtn.setBackground(Color.magenta);
        JButton decimalBtn = new JButton("ماشین حساب اعداد اعشاری");
        decimalBtn.setBackground(Color.magenta);
        JButton polynomialBtn = new JButton("ماشین حساب چند جمله ای");
        polynomialBtn.setBackground(Color.magenta);


        bigNumberBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BigNumberGUI();
                dispose();
            }
        });

        decimalBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DecimalGUI();
                dispose();
            }
        });

        polynomialBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PolynomialGUI();
                dispose();
            }
        });

        add(bigNumberBtn);
        add(decimalBtn);
        add(polynomialBtn);

        setVisible(true);
    }

    public static void main(String[] args) {
        new MainMenu();
    }
}

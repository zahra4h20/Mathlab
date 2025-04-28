package GUI;

import javax.swing.*;
import java.awt.*;
import Logic.PolynomialCalc;

public class PolynomialGUI extends JFrame {
    JTextField input1, input2;
    JButton addBtn, subBtn, mulBtn;
    JTextArea resultArea;

    public PolynomialGUI() {
        setTitle("ماشین حساب چندجمله‌ای");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(7, 1, 10, 10));

        input1 = new JTextField();
        input2 = new JTextField();
        addBtn = new JButton("+");
        subBtn = new JButton("-");
        mulBtn = new JButton("*");
        resultArea = new JTextArea();
        resultArea.setEditable(false);

        add(new JLabel("چندجمله‌ای اول :"));
        add(input1);
        add(new JLabel("چندجمله‌ای دوم:"));
        add(input2);

        JPanel btnPanel = new JPanel();
        btnPanel.add(addBtn);
        btnPanel.add(subBtn);
        btnPanel.add(mulBtn);
        add(btnPanel);

        add(new JScrollPane(resultArea));


        addBtn.addActionListener(e -> {
            String res = PolynomialCalc.add(input1.getText(), input2.getText());
            resultArea.setText("نتیجه جمع:\n" + res);
        });

        subBtn.addActionListener(e -> {
            String res = PolynomialCalc.subtract(input1.getText(), input2.getText());
            resultArea.setText("نتیجه تفریق:\n" + res);
        });

        mulBtn.addActionListener(e -> {
            String res = PolynomialCalc.multiply(input1.getText(), input2.getText());
            resultArea.setText("نتیجه ضرب:\n" + res);
        });

        setVisible(true);
    }

}

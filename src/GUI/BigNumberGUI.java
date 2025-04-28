package GUI;

import javax.swing.*;
import java.awt.*;
import Logic.BigNumberCalc;

public class BigNumberGUI extends JFrame {
    private JTextField display;
    private StringBuilder input;
    private String memory;

    public BigNumberGUI() {
        setTitle("ماشین حساب اعداد صحیح");
        setSize(400, 500);
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        input = new StringBuilder();

        display = new JTextField();
        display.setFont(new Font("Arial", Font.PLAIN, 24));
        display.setEditable(false);
        add(display, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(6, 4, 5, 5));
        String[] buttons = {
                "7", "8", "9", "+",
                "4", "5", "6", "-",
                "1", "2", "3", "*",
                "0", "^", "!", "/",
                "C", "(", ")", "=",
                "←", "M+", "MR", "MC"
        };


        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 20));
            button.addActionListener(e -> handleInput(text));
            buttonPanel.add(button);

        }

        add(buttonPanel, BorderLayout.CENTER);
        setVisible(true);
        buttonPanel.setBackground(Color.darkGray);
    }

    private void handleInput(String text) {
        if (text.equals("C")) {
            input.setLength(0);
            display.setText("");
        } else if (text.equals("=")) {
            try {
                String expression = input.toString();
                String result = BigNumberCalc.evaluate(expression); // انجام محاسبه با رعایت اولویت
                display.setText(result);
                input.setLength(0);
                input.append(result);
            } catch (Exception ex) {
                display.setText("خطا در محاسبه");
            }}
            else if (text.equals("←")) {  // حذف آخرین ورودی
                if (input.length() > 0) {
                    input.deleteCharAt(input.length() - 1);
                    display.setText(input.toString());
                }
            } else if (text.equals("M+")) {  // ذخیره در مموری
                memory = display.getText();
                display.setText("در مموری ذخیره شد");
            } else if (text.equals("MR")) {  // خواندن از مموری
                input.append(memory);
                display.setText(input.toString());
            } else if (text.equals("MC")) {  // پاک کردن مموری
                memory = "";
                display.setText("مموری پاک شد");
            } else {
                input.append(text);
                display.setText(input.toString());
            }
        }
    }

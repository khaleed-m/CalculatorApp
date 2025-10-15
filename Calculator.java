import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator extends JFrame implements ActionListener {

    private JTextField display;
    private double num1 = 0, num2 = 0, result = 0;
    private char operator;
    private boolean isResultDisplayed = false;

    public Calculator() {
        setTitle("Smart Calculator");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Responsive sizing for mobile/desktop
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (screenSize.width < 800) ? 280 : 360;   // Smaller window width
        int height = (screenSize.height < 800) ? 420 : 480; // Smaller height
        setSize(width, height);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(5, 5)); // reduced gap

        // Display field
        display = new JTextField();
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.BOLD, (screenSize.width < 800) ? 22 : 28)); // reduced size
        display.setHorizontalAlignment(SwingConstants.RIGHT);
        display.setBackground(Color.WHITE);
        display.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        add(display, BorderLayout.NORTH);

        // Buttons panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 4, 5, 5)); // reduced gaps between buttons
        panel.setBackground(new Color(230, 230, 230));
        add(panel, BorderLayout.CENTER);

        String[] buttons = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+",
            "C"
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, (screenSize.width < 800) ? 18 : 22)); // smaller font
            button.setBackground(Color.WHITE);
            button.setFocusPainted(false);
            button.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
            button.addActionListener(this);
            panel.add(button);
        }

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        // Reset display if result was shown
        if (isResultDisplayed && ((command.charAt(0) >= '0' && command.charAt(0) <= '9') || command.equals("."))) {
            display.setText("");
            isResultDisplayed = false;
        }

        if ((command.charAt(0) >= '0' && command.charAt(0) <= '9') || command.equals(".")) {
            display.setText(display.getText() + command);
        } 
        else if (command.equals("C")) {
            display.setText("");
            num1 = num2 = result = 0;
        } 
        else if (command.equals("=")) {
            try {
                num2 = Double.parseDouble(display.getText());
                switch (operator) {
                    case '+': result = num1 + num2; break;
                    case '-': result = num1 - num2; break;
                    case '*': result = num1 * num2; break;
                    case '/': 
                        if (num2 == 0) {
                            display.setText("Error: Divide by 0");
                            return;
                        }
                        result = num1 / num2; 
                        break;
                }
                display.setText(String.valueOf(result));
                num1 = result;
                isResultDisplayed = true;
            } catch (Exception ex) {
                display.setText("Error");
            }
        } 
        else { // operator
            try {
                num1 = Double.parseDouble(display.getText());
                operator = command.charAt(0);
                display.setText("");
            } catch (Exception ex) {
                display.setText("Error");
            }
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new Calculator();
    }
}

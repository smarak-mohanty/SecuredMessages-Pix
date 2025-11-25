import javax.swing.*;
import java.awt.*;

public class Main_Program extends JFrame {
    public Main_Program() {
        setTitle("Message Conversion");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Add heading label
        JLabel headingLabel = new JLabel("Secure Message Converter");
        headingLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
        headingLabel.setForeground(Color.BLUE);
        headingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(headingLabel, BorderLayout.NORTH);

        // Load and add background image
        ImageIcon backgroundImage = new ImageIcon("./img/background.jpg");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        panel.add(backgroundLabel, BorderLayout.CENTER);

        // Create panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton encode = new JButton("Encode");
        JButton decode = new JButton("Decode");

        encode.setBackground(new Color(200,210,50));
        decode.setBackground(new Color(200,230,90));

        buttonPanel.add(encode);
        buttonPanel.add(decode);

        // Add button panel below the image
        panel.add(buttonPanel, BorderLayout.SOUTH);

        decode.addActionListener(e -> SwingUtilities.invokeLater(() -> new Decode().setVisible(true)));
        encode.addActionListener(e -> SwingUtilities.invokeLater(() -> new Encode().setVisible(true)));

        add(panel);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main_Program().setVisible(true));
    }
}
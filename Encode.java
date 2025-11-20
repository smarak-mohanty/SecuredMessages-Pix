import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Encode extends JFrame {

    private JTextField filePathField;
    private JTextArea resultArea;

    public Encode() {
        setTitle("Message Encoder");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(240, 240, 240));
 
        JLabel filePathLabel = new JLabel("Selected File Path:");
        filePathField = new JTextField(20);
        JButton browseButton = new JButton("Browse");
        JButton Open_Photo=new JButton("Open Encoded Photo");
        JButton encodeButton = new JButton("Encode and Save as Image");
        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);

        filePathLabel.setBackground(new Color(173, 216, 230));
        browseButton.setBackground(new Color(50, 205, 50));
        encodeButton.setBackground(new Color(255, 165, 0));
        resultArea.setBackground(new Color(255, 255, 224));
        Open_Photo.setBackground(new Color(240,240,0));

        panel.add(filePathLabel);
        panel.add(filePathField);
        panel.add(browseButton);
        panel.add(encodeButton);
        panel.add(new JScrollPane(resultArea));
        panel.add(Open_Photo);

        Open_Photo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageDisplay();
            }
        });
        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                browseFile();
            }
        });

        encodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                encodeAndSave();
            }
        });

        add(panel);
    }
    public void ImageDisplay() {
        String imagePath = "C:\\Documents-D\\Projects Done\\Secured Messages Pix\\EncodedImage\\output.bmp";
        try {
            File file = new File(imagePath);
            Desktop desktop = Desktop.getDesktop();
            desktop.open(file);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    private void browseFile() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(this);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            filePathField.setText(selectedFile.getAbsolutePath());
        }
    }

    private void encodeAndSave() {
        String messageFilePath = filePathField.getText();

        String mainString = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890,.?;:'\" (){}[]|!~`@#$%^&*-_+=/><";
        String keyString =  "{}[]|!~`@#$%^&*-_+=/><,.?;:' \"()QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm1234567890";

        StringBuilder encodedMessage = new StringBuilder();

        try (FileReader reader = new FileReader(messageFilePath)) {
            int character;
            while ((character = reader.read()) != -1) {
                char c = (char) character;
                if (c != '\n' && c != '\r') {
                    c = keyString.charAt(mainString.indexOf(c));
                }
                encodedMessage.append(c);
            }
        } catch (IOException ex) {
            resultArea.setText("Error: " + ex.getMessage());
            return;
        }

        String codedMessage = encodedMessage.toString();
        char[] wordArray = codedMessage.toCharArray();
        int[] intArray = new int[codedMessage.length()];
        for (int i = 0; i < wordArray.length; i++) {
            intArray[i] = (int) wordArray[i];
        }

        int row = (int) Math.sqrt(codedMessage.length());
        int column = codedMessage.length() / row + 1;
        int index = 0;
        int[][] matrix = new int[row][column];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (index < codedMessage.length()) {
                    matrix[i][j] = intArray[index++];
                }
            }
        }

        try {
            int width = column;
            int height = row;
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    int pixelValue = matrix[i][j];
                    int rgb = (pixelValue << 16) | (pixelValue << 8) | pixelValue; // Grayscale
                    image.setRGB(j, i, rgb);
                }
            }

            // Save the image
            File outputImageFile = new File("C:\\Documents-D\\Projects Done\\Secured Messages Pix\\EncodedImage\\output.bmp");
            ImageIO.write(image, "bmp", outputImageFile);
            resultArea.append("\n\nBMP image saved successfully.");
        } catch (IOException ex) {
            resultArea.append("\n\nError saving BMP image: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Encode().setVisible(true);
            }
        });
    }
}

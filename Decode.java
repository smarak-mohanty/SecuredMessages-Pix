import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Decode extends JFrame {

    private JTextField filePathField;
    private JTextArea resultArea;

    public Decode() {
        setTitle("BMP to Message Converter");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(240, 240, 240));
        JLabel filePathLabel = new JLabel("Selected BMP Image Path:");
        filePathField = new JTextField(20);
        JButton browseButton = new JButton("Browse");
        JButton convertButton = new JButton("Decode and save as text");
        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);
        JButton Open_file=new JButton("Open file");
        Open_file.setBackground(new Color(255, 200, 0));
        filePathLabel.setBackground(new Color(173, 216, 230));
        filePathLabel.setBackground(new Color(173, 216, 230));
        browseButton.setBackground(new Color(50, 205, 50));
        convertButton.setBackground(new Color(255, 165, 0));
        resultArea.setBackground(new Color(255, 255, 224));

        panel.add(filePathLabel);
        panel.add(filePathField);
        panel.add(browseButton);
        panel.add(convertButton);
        panel.add(new JScrollPane(resultArea));
        panel.add(Open_file);

        Open_file.addActionListener(e -> Open_Decode_File());
        browseButton.addActionListener(e -> browseFile());
        convertButton.addActionListener(e -> convertToMatrix());

        
        add(panel);
    }

    private void Open_Decode_File() {
        String filepath = "C:\\Documents-D\\Projects Done\\Secured Messages Pix\\DecodedText\\DecodedFile.txt";
        File file = new File(filepath);
        if (file.exists()) {
            try {
                Desktop.getDesktop().edit(file);
            } catch( IOException e) {
                System.out.println("An error occured while opening the file " + e.getMessage());
            }
        } else {
            System.out.println("File does not exist.");
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

    

    private void convertToMatrix() {
        String imagePath = filePathField.getText();

        try {
            BufferedImage image = ImageIO.read(new File(imagePath));

            int width = image.getWidth();
            int height = image.getHeight();
            int index = 0;
            int[][] matrix = new int[height][width];
            int[] arr = new int [height*width];

            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    int rgb = image.getRGB(j, i);
                    int pixelValue = (rgb >> 16) & 0xFF; // Extracting red component for grayscale
                    matrix[i][j] = pixelValue;
                    arr[index++] = matrix[i][j];
                }
            }
            char wordArray[] = new char[height * width];
            for (int i = 0; i < height * width; i++) {
                wordArray[i] = (char) arr[i];
            }
            String encoddedMessage = String.valueOf(wordArray);
            String DecodedFilePath = "C:\\Documents-D\\Projects Done\\Secured Messages Pix\\DecodedText\\DecodedFile.txt";

            String mainString = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890,.?;:'\" (){}[]|!~`@#$%^&*-_+=/><";
            String keyString = "{}[]|!~`@#$%^&*-_+=/><,.?;:' \"()QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm1234567890";

            StringBuilder ec = new StringBuilder();
            for (char c : encoddedMessage.toCharArray()) {
                int keyIndex = keyString.indexOf(c);
                if (keyIndex != -1 && c != '\n' && c != '\r' && (int) c != 0) {
                    c = mainString.charAt(keyIndex);
                }
                ec.append(c);
            }
            String decodedString = ec.toString();    
            try (FileWriter writer = new FileWriter(DecodedFilePath)) {
                writer.write(decodedString);
                System.out.println("File Decoded successfully!");
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }   
            resultArea.append("\n\nMessage saved successfully."); 
        } catch (IOException e) {
            resultArea.setText("Error reading image: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Decode().setVisible(true));
    }
}
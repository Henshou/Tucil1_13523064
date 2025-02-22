import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Main {
    private static char[][] finalBoard;
    private static int iterations;
    private static long timeTaken;

    public static void main(String[] args) {
        JFrame frame = new JFrame("File Reader");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        centerFrame(frame);

        JPanel panel = new JPanel(new BorderLayout());

        JLabel titleLabel = new JLabel("200 IQ Gemplei", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 36));
        panel.add(titleLabel, BorderLayout.NORTH);

        ImageIcon imageIcon = new ImageIcon("src/erm actually.jpeg");
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(imageLabel, BorderLayout.CENTER);

        JButton button = new JButton("Choose a File");
        button.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                frame.dispose();
                InOut.readInput(selectedFile.getAbsolutePath());
                displayResults();
            }
        });

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.add(button);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    private static void displayResults() {
        JFrame resultsFrame = new JFrame("IQ Puzzler Solver");
        resultsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        resultsFrame.setSize(800, 600);
        centerFrame(resultsFrame);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel boardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int cellSize = 60;
                Map<Character, Color> colorMap = createColorMap();

                int boardWidth = finalBoard[0].length * cellSize;
                int boardHeight = finalBoard.length * cellSize;

                int startX = (getWidth() - boardWidth) / 2;
                int startY = (getHeight() - boardHeight) / 2;

                for (int i = 0; i < finalBoard.length; i++) {
                    for (int j = 0; j < finalBoard[i].length; j++) {
                        char cell = finalBoard[i][j];
                        if (cell != ' ') {
                            g.setColor(colorMap.getOrDefault(cell, Color.LIGHT_GRAY));
                            g.fillRect(startX + j * cellSize, startY + i * cellSize, cellSize, cellSize);
                            g.setColor(Color.BLACK);
                            g.drawString(String.valueOf(cell), startX + j * cellSize + 25, startY + i * cellSize + 35);
                        } else {
                            g.setColor(Color.LIGHT_GRAY);
                            g.fillRect(startX + j * cellSize, startY + i * cellSize, cellSize, cellSize);
                        }
                    }
                }
            }

            @Override
            public Dimension getPreferredSize() {
                int cellSize = 60;
                return new Dimension(finalBoard[0].length * cellSize, finalBoard.length * cellSize);
            }
        };

        JScrollPane scrollPane = new JScrollPane(boardPanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(2, 1));

        JLabel timeLabel = new JLabel("Waktu pencarian: " + timeTaken + " ms");
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        infoPanel.add(timeLabel);

        JLabel iterationsLabel = new JLabel("Banyak kasus yang ditinjau: " + iterations);
        iterationsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        infoPanel.add(iterationsLabel);

        mainPanel.add(infoPanel, BorderLayout.NORTH);

        JButton saveButton = new JButton("Save as File");
        saveButton.addActionListener(e -> {
            String fileName = JOptionPane.showInputDialog(resultsFrame, "Enter the file name:");
            if (fileName != null && !fileName.trim().isEmpty()) {
                String folderPath = "test/";
                File folder = new File(folderPath);
                String fullPath = folderPath + fileName.trim() + ".txt";
                InOut.saveResult(fullPath, finalBoard, iterations, timeTaken);
                JOptionPane.showMessageDialog(resultsFrame, "Results saved to " + fullPath);
            } else {
                JOptionPane.showMessageDialog(resultsFrame, "File name cannot be empty!");
            }
        });

        JButton saveImageButton = new JButton("Save as Image");
        saveImageButton.addActionListener(e -> {
            String fileName = JOptionPane.showInputDialog(resultsFrame, "Enter the image file name:");
            if (fileName != null && !fileName.trim().isEmpty()) {
                String folderPath = "test/";
                File folder = new File(folderPath);
                String fullPath = folderPath + fileName.trim() + ".png";
                InOut.saveAsImage(boardPanel, fullPath);
                JOptionPane.showMessageDialog(resultsFrame, "Image saved to " + fullPath);
            } else {
                JOptionPane.showMessageDialog(resultsFrame, "File name cannot be empty!");
            }
        });

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(saveButton);
        buttonPanel.add(saveImageButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        resultsFrame.getContentPane().add(mainPanel);
        resultsFrame.setVisible(true);
    }

    private static Map<Character, Color> createColorMap() {
        Map<Character, Color> colorMap = new HashMap<>();
        colorMap.put('A', Color.RED);
        colorMap.put('B', Color.BLUE);
        colorMap.put('C', Color.GREEN);
        colorMap.put('D', Color.YELLOW);
        colorMap.put('E', Color.ORANGE);
        colorMap.put('F', Color.CYAN);
        colorMap.put('G', Color.MAGENTA);
        colorMap.put('H', Color.PINK);
        colorMap.put('I', new Color(128, 0, 128));
        colorMap.put('J', new Color(255, 165, 0));
        colorMap.put('K', new Color(0, 128, 0));
        colorMap.put('L', new Color(128, 128, 0));
        colorMap.put('M', new Color(0, 128, 128));
        colorMap.put('N', new Color(128, 0, 0));
        colorMap.put('O', new Color(0, 0, 128));
        colorMap.put('P', new Color(255, 192, 203));
        colorMap.put('Q', new Color(165, 42, 42));
        colorMap.put('R', new Color(0, 255, 255));
        colorMap.put('S', new Color(255, 0, 255));
        colorMap.put('T', new Color(128, 128, 128));
        colorMap.put('U', new Color(0, 255, 89, 157));
        colorMap.put('V', new Color(221, 255, 0, 161));
        colorMap.put('W', new Color(6, 203, 140));
        colorMap.put('X', new Color(175, 130, 0));
        colorMap.put('Y', new Color(192, 192, 192));
        colorMap.put('Z', new Color(64, 64, 64));
        return colorMap;
    }

    private static void centerFrame(JFrame frame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - frame.getWidth()) / 2;
        int y = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(x, y);
    }

    public static void setResults(char[][] board, int iterations, long timeTaken) {
        finalBoard = board;
        Main.iterations = iterations;
        Main.timeTaken = timeTaken;
    }
}
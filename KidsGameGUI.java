import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.imageio.ImageIO;
import javax.swing.text.*;

public class KidsGameGUI extends JFrame {

    //Theme colors
    private final Color MC_DIRT = new Color(74, 52, 34); // Dark Brown
    private final Color MC_GRASS = new Color(70, 160, 60); // Green
    private final Color MC_STONE = new Color(120, 120, 120); // Gray
    private final Color MC_TEXT_WHITE = new Color(240, 240, 240);
    private final Font MC_FONT = new Font("Monospaced", Font.BOLD, 18); // Blocky Font
    private final Font MC_TITLE_FONT = new Font("Monospaced", Font.BOLD, 32);

    //GUI
    private BackgroundPanel mainPanel; // Custom panel for image
    private CardLayout cardLayout;

    //Screens
    private JPanel menuPanel;
    private JPanel setupPanel;
    private JPanel gamePanel;
    private JPanel summaryPanel;

    //game Data
    private int selectedMode;
    private boolean isMultiplayer;
    private ArrayList<Player> players;
    private int currentPlayerIndex;
    private Game currentGame;
    private int targetQuestions;
    private int timeLimitSeconds;

    //State
    private int questionsAnsweredCount;
    private int lives;
    private Timer gameTimer;
    private long startTime;
    private boolean isTimerRunning;

    //Widgets
    private JLabel lblPlayerName;
    private JLabel lblQuestion;
    private JTextField txtAnswer;
    private JLabel lblStatus;
    private JTextPane txtGameLog;   // <-- CHANGED FROM JTextArea
    private JButton btnSubmit;

    public KidsGameGUI() {
        setTitle("Minecraft Math Edition");
        setSize(800, 600); // Slightly larger for the theme
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new BackgroundPanel();
        mainPanel.setLayout(cardLayout);

        initMenuPanel();
        initSetupPanel();
        initGamePanel();
        initSummaryPanel();

        mainPanel.add(menuPanel, "MENU");
        mainPanel.add(setupPanel, "SETUP");
        mainPanel.add(gamePanel, "GAME");
        mainPanel.add(summaryPanel, "SUMMARY");

        add(mainPanel);
    }

    //Coloring helper
    private void appendColoredText(String text, Color color) {
        StyledDocument doc = txtGameLog.getStyledDocument();
        Style style = txtGameLog.addStyle("mc", null);
        StyleConstants.setForeground(style, color);
        try { doc.insertString(doc.getLength(), text, style); }
        catch (Exception e) { e.printStackTrace(); }
    }

    private void styleButton(JButton btn) {
        btn.setFont(MC_FONT);
        btn.setBackground(MC_STONE);
        btn.setForeground(Color.BLACK);
        btn.setBorder(BorderFactory.createBevelBorder(0));
        btn.setFocusPainted(false);
    }

    private void styleLabel(JLabel lbl) {
        lbl.setFont(MC_FONT);
        lbl.setForeground(MC_TEXT_WHITE);
        lbl.setOpaque(false);
    }

    private void initMenuPanel() {
        menuPanel = new JPanel(new GridBagLayout());
        menuPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("MINECRAFT MATH", SwingConstants.CENTER);
        title.setFont(MC_TITLE_FONT);
        title.setForeground(Color.WHITE);
        title.setOpaque(true);
        title.setBackground(new Color(0,0,0,150));

        JButton btnSingle = new JButton("Single Player");
        styleButton(btnSingle);

        JButton btnMulti = new JButton("Multiplayer");
        styleButton(btnMulti);

        btnSingle.addActionListener(e -> goToSetup(false));
        btnMulti.addActionListener(e -> goToSetup(true));

        gbc.gridx = 0; gbc.gridy = 0;
        menuPanel.add(title, gbc);
        gbc.gridy = 1;
        menuPanel.add(btnSingle, gbc);
        gbc.gridy = 2;
        menuPanel.add(btnMulti, gbc);
    }

    private void goToSetup(boolean multi) {
        this.isMultiplayer = multi;
        cardLayout.show(mainPanel, "SETUP");
    }

    private JComboBox<String> cmbMode;
    private JTextField txtParam;
    private JLabel lblParam;
    private JTextField txtPlayerNames;

    private void initSetupPanel() {
        setupPanel = new JPanel(new GridBagLayout());
        setupPanel.setOpaque(false);

        JPanel controlBox = new JPanel(new GridBagLayout());
        controlBox.setBackground(new Color(0, 0, 0, 180));
        controlBox.setBorder(new LineBorder(Color.WHITE, 2));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblMode = new JLabel("Select Mode:");
        styleLabel(lblMode);

        String[] modes = {"1) Make a Wish", "2) No Mistakes", "3) Take Chances", "4) Time Trial"};
        cmbMode = new JComboBox<>(modes);
        cmbMode.setFont(MC_FONT);
        cmbMode.setBackground(MC_STONE);

        lblParam = new JLabel("Questions:");
        styleLabel(lblParam);

        txtParam = new JTextField("5");
        txtParam.setFont(MC_FONT);

        JLabel lblNames = new JLabel("Names (comma sep):");
        styleLabel(lblNames);

        txtPlayerNames = new JTextField();
        txtPlayerNames.setFont(MC_FONT);

        JButton btnStart = new JButton("CREATE WORLD");
        styleButton(btnStart);
        btnStart.setBackground(MC_GRASS);

        gbc.gridx = 0; gbc.gridy = 0; controlBox.add(lblMode, gbc);
        gbc.gridx = 1; controlBox.add(cmbMode, gbc);
        gbc.gridx = 0; gbc.gridy = 1; controlBox.add(lblParam, gbc);
        gbc.gridx = 1; controlBox.add(txtParam, gbc);
        gbc.gridx = 0; gbc.gridy = 2; controlBox.add(lblNames, gbc);
        gbc.gridx = 1; controlBox.add(txtPlayerNames, gbc);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; controlBox.add(btnStart, gbc);

        setupPanel.add(controlBox);

        cmbMode.addActionListener(e -> {
            int index = cmbMode.getSelectedIndex();
            if (index == 0) { lblParam.setText("Questions:"); txtParam.setEnabled(true); }
            else if (index == 3) { lblParam.setText("Time (sec):"); txtParam.setEnabled(true); }
            else { lblParam.setText("N/A"); txtParam.setEnabled(false); }
        });

        btnStart.addActionListener(e -> {
            try { startGameSetup(); }
            catch (Exception ex) { JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage()); }
        });
    }

    private void startGameSetup() throws Exception {
        String nameInput = txtPlayerNames.getText().trim();
        if (nameInput.isEmpty()) throw new Exception("Enter a name!");
        String[] rawNames = nameInput.split(",");
        players = new ArrayList<>();

        for(String n : rawNames) {
            if(!n.trim().isEmpty()) players.add(new Player(n.trim()));
        }

        if(players.isEmpty()) throw new Exception("Need valid names.");

        if(!isMultiplayer && players.size() > 1)
            throw new Exception("Singleplayer mode only allows one name.");

        if(isMultiplayer && players.size() < 2)
            throw new Exception("Need 2+ players.");


        selectedMode = cmbMode.getSelectedIndex() + 1;
        if (selectedMode == 1) targetQuestions = Integer.parseInt(txtParam.getText());
        else if (selectedMode == 4) timeLimitSeconds = Integer.parseInt(txtParam.getText());

        currentPlayerIndex = 0;
        preparePlayerTurn();
    }

    private void initGamePanel() {
        gamePanel = new JPanel(new BorderLayout());
        gamePanel.setOpaque(false);

        JPanel topPanel = new JPanel(new GridLayout(2, 1));
        topPanel.setBackground(Color.BLACK);
        topPanel.setBorder(new LineBorder(MC_GRASS, 2));

        lblPlayerName = new JLabel("Player: ", SwingConstants.CENTER);
        styleLabel(lblPlayerName);
        lblStatus = new JLabel("Status", SwingConstants.CENTER);
        styleLabel(lblStatus);

        topPanel.add(lblPlayerName);
        topPanel.add(lblStatus);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);

        JPanel qBox = new JPanel(new GridLayout(3,1, 10, 10));
        qBox.setBackground(new Color(0,0,0,150));
        qBox.setBorder(new LineBorder(Color.WHITE, 2));

        lblQuestion = new JLabel("Question...");
        styleLabel(lblQuestion);
        lblQuestion.setFont(new Font("Monospaced", Font.BOLD, 28));

        txtAnswer = new JTextField(10);
        txtAnswer.setFont(new Font("Monospaced", Font.BOLD, 24));
        txtAnswer.setBackground(Color.BLACK);
        txtAnswer.setForeground(Color.WHITE);
        txtAnswer.setCaretColor(Color.WHITE);

        btnSubmit = new JButton("CRAFT ANSWER");
        styleButton(btnSubmit);

        qBox.add(lblQuestion);
        qBox.add(txtAnswer);
        qBox.add(btnSubmit);
        centerPanel.add(qBox);

        txtGameLog = new JTextPane();
        txtGameLog.setEditable(false);
        txtGameLog.setBackground(new Color(0, 0, 0, 200));
        txtGameLog.setForeground(Color.WHITE);
        txtGameLog.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JScrollPane scroll = new JScrollPane(txtGameLog);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);

        gamePanel.add(topPanel, BorderLayout.NORTH);
        gamePanel.add(centerPanel, BorderLayout.CENTER);
        gamePanel.add(scroll, BorderLayout.SOUTH);

        btnSubmit.addActionListener(e -> processAnswer());
        txtAnswer.addActionListener(e -> processAnswer());
    }

    private void preparePlayerTurn() {
        if(currentPlayerIndex >= players.size()) {
            showFinalLeaderboard();
            return;
        }

        Player p = players.get(currentPlayerIndex);
        currentGame = new Game();
        questionsAnsweredCount = 0;
        lives = 3;

        txtGameLog.setText("");
        appendColoredText("<Server> ", new Color(255, 255, 85));
        appendColoredText(p.name + " has joined the game\n", Color.WHITE);

        JOptionPane.showMessageDialog(this, "Spawning: " + p.name);

        lblPlayerName.setText("Steve: " + p.name);
        cardLayout.show(mainPanel, "GAME");

        if(selectedMode == 4) {
            startTime = System.currentTimeMillis();
            isTimerRunning = true;
            gameTimer = new Timer(100, e -> {
                long elapsed = (System.currentTimeMillis() - startTime) / 1000;
                long remaining = timeLimitSeconds - elapsed;
                lblStatus.setText("Time: " + remaining + " | Score: " + currentGame.score);
                if (remaining <= 0) endPlayerTurn();
            });
            gameTimer.start();
        } else {
            updateStatusLabel();
        }
        nextQuestion();
    }

    private void nextQuestion() {
        currentGame.generateNextQuestion();
        lblQuestion.setText(currentGame.getCurrentQuestionText());
        txtAnswer.setText("");
        txtAnswer.requestFocus();
    }

    private void processAnswer() {
        if(selectedMode == 4 && !isTimerRunning) return;

        String input = txtAnswer.getText().trim();
        if(input.isEmpty()) return;

        boolean correct = currentGame.checkAnswer(input);

        appendColoredText(correct ? "[Correct] " : "[Wrong] ", correct ? new Color(85,255,85) : new Color(255,85,85));
        appendColoredText(currentGame.getCurrentQuestionText() + " -> " + input + "\n", Color.WHITE);

        questionsAnsweredCount++;

        if (selectedMode == 1 && questionsAnsweredCount >= targetQuestions) { endPlayerTurn(); return; }
        else if (selectedMode == 2 && !correct) { JOptionPane.showMessageDialog(this, "You died! (Mistake)"); endPlayerTurn(); return; }
        else if (selectedMode == 3 && !correct) {
            lives--;
            if(lives <= 0) { JOptionPane.showMessageDialog(this, "Game Over!"); endPlayerTurn(); return; }
        }

        updateStatusLabel();
        nextQuestion();
    }

    private void updateStatusLabel() {
        if(selectedMode == 3) lblStatus.setText("XP: " + currentGame.score + " | Hearts: " + lives);
        else lblStatus.setText("XP Level: " + currentGame.score);
    }

    private void endPlayerTurn() {
        if(gameTimer != null) gameTimer.stop();
        isTimerRunning = false;
        players.get(currentPlayerIndex).setGameData(currentGame.score, currentGame.summary);
        currentPlayerIndex++;
        preparePlayerTurn();
    }

    private JTextArea txtLeaderboard;

    private void initSummaryPanel() {
        summaryPanel = new JPanel(new BorderLayout());
        summaryPanel.setOpaque(false);

        JLabel header = new JLabel("==== LEADERBOARD ====", SwingConstants.CENTER);
        header.setFont(MC_TITLE_FONT);
        header.setForeground(Color.YELLOW);

        JPanel pnlText = new JPanel(new BorderLayout());
        pnlText.setBackground(new Color(0,0,0,180));
        pnlText.setBorder(new LineBorder(MC_DIRT, 5));

        txtLeaderboard = new JTextArea();
        txtLeaderboard.setFont(new Font("Monospaced", Font.BOLD, 16));
        txtLeaderboard.setEditable(false);
        txtLeaderboard.setForeground(Color.WHITE);
        txtLeaderboard.setBackground(new Color(0,0,0,0));
        txtLeaderboard.setOpaque(false);

        JButton btnRestart = new JButton("Respawn (Menu)");
        styleButton(btnRestart);
        btnRestart.addActionListener(e -> cardLayout.show(mainPanel, "MENU"));

        JScrollPane lbScroll = new JScrollPane(txtLeaderboard);
        lbScroll.setOpaque(false);
        lbScroll.getViewport().setOpaque(false);
        pnlText.add(lbScroll, BorderLayout.CENTER);


        summaryPanel.add(header, BorderLayout.NORTH);
        summaryPanel.add(pnlText, BorderLayout.CENTER);
        summaryPanel.add(btnRestart, BorderLayout.SOUTH);
    }

    private void showFinalLeaderboard() {
        Collections.sort(players, Comparator.comparingInt(Player::getScore).reversed());
        StringBuilder sb = new StringBuilder();
        sb.append("PLAYER          | SCORE\n");
        sb.append("=======================\n");
        for (Player p : players) {
            sb.append(String.format("%-15s | %-5d\n", p.name, p.score));
        }
        if (isMultiplayer && !players.isEmpty()) sb.append("\nWINNER: " + players.get(0).name);

        txtLeaderboard.setText(sb.toString());
        cardLayout.show(mainPanel, "SUMMARY");
    }

    class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel() {
            try {
                backgroundImage = ImageIO.read(
                        getClass().getResource("/minecraft_bg.jpg")
                );
            } catch (IOException e) {
                setBackground(MC_DIRT);
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new KidsGameGUI().setVisible(true));
    }
}

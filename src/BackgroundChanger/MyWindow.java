package BackgroundChanger;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * Klasse für das normale User Interface,
 */
class MyWindow extends JFrame {

    private final Changer changer = new Changer();

    private JLabel titleLabel;
    private JPanel outerPanel;
    private JPanel actionPanel;
    private JFileChooser chooseLocalFile;
    private JButton btnSubreddit;
    private JTextField textSubreddit;
    private JTextField textTumblr;
    private JButton btnTumblr;
    private JButton btnSettings;
    private JList listHistory;
    private JPanel historyPanel;
    private JPanel rootPanel;


    /**
     * Default-Konstruktor macht das Fenster.
     */
    public MyWindow() {
        super("Background Changer");

        try {
            ImageIcon icon = new ImageIcon("./appicon.png");
            setIconImage(icon.getImage());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(rootPanel);
        setVisible(true);
        setLocationRelativeTo(null);


        btnSubreddit.addActionListener(e -> setInternetImage(textSubreddit.getText(), new RedditAPI()));
        btnTumblr.addActionListener(e -> setInternetImage(textTumblr.getText(), new TumblrAPI()));
        chooseLocalFile.addActionListener(e -> setLocalImage(chooseLocalFile.getSelectedFile()));
        btnSettings.addActionListener(e -> new SettingsWindow());

        chooseLocalFile.setFileFilter(new FileNameExtensionFilter("Bilddateien", "jpg", "png"));
        chooseLocalFile.setControlButtonsAreShown(false);

        updateHistoryList();

        pack();

    }


    /**
     * Nimmt den Verlauf aus dem ini-File und stellt ihn in der Liste dar.
     */
    private void updateHistoryList() {

        SettingsReader reader = new SettingsReader();
        ArrayList<DataHelper> iniFileList = (ArrayList) reader.readHistory();
        Collections.reverse(iniFileList);
        DefaultListModel listModel = new DefaultListModel();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        Date resultDate;
        for (DataHelper dh : iniFileList) {
            resultDate = new Date(Long.parseLong(dh.getKey()));
            listModel.addElement(dateFormat.format(resultDate) + ": " + dh.getValue());
        }
        listHistory.setModel(listModel);
        listHistory.updateUI();

    }

    /**
     * Nimmt ein Bild von einer Website.
     *
     * @param subUrl Teil des Links, vom User definiert.
     * @param api Die API, wird durch den Button, der gedrückt wurde, definiert.
     */
    void setInternetImage(String subUrl, API api) {
        if ("".equals(subUrl)) {
            JOptionPane.showMessageDialog(getContentPane(), "Sie haben kein Subreddit angegeben.");
        } else {
            if (changer.setImage(subUrl, api)) {
                updateHistoryList();
            }

        }

    }

    /**
     * Nimmt ein lokales Bild und macht dieses zum Desktophintergrund.
     *
     * @param selectedFile Das vom User ausgeählte File.
     */
    private void setLocalImage(File selectedFile) {
        if (!"".equals(selectedFile.getName())) {

            try {
                if (changer.useLocalImage(selectedFile)) {
                    JOptionPane.showMessageDialog(getContentPane(), "Bild wurde erfolgreich geändert.\n"
                            + "Das neue Bild ist: " + selectedFile.getAbsolutePath());
                } else
                    JOptionPane.showMessageDialog(getContentPane(), "Hintergrund nicht geändert. Bitte geben Sie ein Bild an.");
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } else JOptionPane.showMessageDialog(getContentPane(), "Sie haben noch kein Bild ausgewählt.");
    }


    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        rootPanel = new JPanel();
        rootPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(10, 10, 10, 10), -1, -1));
        titleLabel = new JLabel();
        titleLabel.setFont(new Font(titleLabel.getFont().getName(), Font.BOLD, 26));
        titleLabel.setHorizontalAlignment(0);
        titleLabel.setHorizontalTextPosition(0);
        titleLabel.setText("Hintergrundbild wechseln");
        titleLabel.setVerticalAlignment(0);
        rootPanel.add(titleLabel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_NORTH, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        outerPanel = new JPanel();
        outerPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        rootPanel.add(outerPanel, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        outerPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0), null));
        actionPanel = new JPanel();
        actionPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1));
        outerPanel.add(actionPanel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(255, 24), null, 0, false));
        actionPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Aktion auswählen", TitledBorder.CENTER, TitledBorder.TOP));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        actionPanel.add(panel1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), null));
        chooseLocalFile = new JFileChooser();
        chooseLocalFile.setApproveButtonText("Verwenden");
        chooseLocalFile.setDialogTitle("Lokales Bild wählen");
        chooseLocalFile.setDialogType(0);
        panel1.add(chooseLocalFile, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        actionPanel.add(panel2, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        btnSettings = new JButton();
        btnSettings.setText("Einstellungen");
        panel2.add(btnSettings, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        actionPanel.add(panel3, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        btnSubreddit = new JButton();
        btnSubreddit.setText("Bild aus Subreddit    ");
        panel3.add(btnSubreddit, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textSubreddit = new JTextField();
        textSubreddit.setToolTipText("Gib ein Subreddit an");
        panel3.add(textSubreddit, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(417, 24), null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        actionPanel.add(panel4, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        textTumblr = new JTextField();
        textTumblr.setToolTipText("Gib einen Tumblr Blog an");
        panel4.add(textTumblr, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        btnTumblr = new JButton();
        btnTumblr.setText("Bild von Tumblr Blog");
        panel4.add(btnTumblr, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        historyPanel = new JPanel();
        historyPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        outerPanel.add(historyPanel, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        historyPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Verlauf", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION));
        listHistory = new JList();
        listHistory.setSelectionMode(0);
        historyPanel.add(listHistory, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return rootPanel;
    }
}

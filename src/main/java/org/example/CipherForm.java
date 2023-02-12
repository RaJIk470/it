package org.example;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.filechooser.*;

public class CipherForm extends JFrame {

    private JTextField keyField;
    private JTextField fileField;
    private JTextArea outputArea;
    private JComboBox<String> algorithmBox;

    public CipherForm() {
        Font font = new Font("Noto", Font.PLAIN, 22);
        setTitle("Cipher Form");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel keyPanel = new JPanel();
        JLabel keyPanelLabel = new JLabel("Key: ");
        keyPanelLabel.setFont(font);
        keyPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        keyPanel.add(keyPanelLabel);
        keyField = new JTextField(20);
        keyField.setFont(font);
        keyPanel.add(keyField);
        mainPanel.add(keyPanel);

        JPanel filePanel = new JPanel();
        filePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel filePanelLabel = new JLabel("File to encode/decode: ");
        filePanelLabel.setFont(font);
        filePanel.add(filePanelLabel);
        filePanel.setFont(font);
        fileField = new JTextField(20);
        fileField.setFont(font);
        fileField.setEditable(false);
        filePanel.add(fileField);
        JButton fileButton = new JButton("Choose File");
        fileButton.setFont(font);
        fileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(CipherForm.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    fileField.setText(fileChooser.getSelectedFile().getAbsolutePath());
                }
            }
        });
        filePanel.add(fileButton);
        mainPanel.add(filePanel);

        JPanel algorithmPanel = new JPanel();
        algorithmPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel algoPanelLabel = new JLabel("Algorithm: ");
        algoPanelLabel.setFont(font);
        algorithmPanel.add(algoPanelLabel);
        algorithmBox = new JComboBox<>(new String[] { "Playfair", "Vigenere" });
        algorithmBox.setFont(new Font("Noto", Font.PLAIN, 22));
        algorithmPanel.add(algorithmBox);
        mainPanel.add(algorithmPanel);

        JPanel outputPanel = new JPanel();
        JLabel outputPanelLabel = new JLabel("Output: ");
        outputPanelLabel.setFont(font);
        outputPanel.setLayout(new BorderLayout());
        outputPanel.add(outputPanelLabel, BorderLayout.WEST);
        outputArea = new JTextArea();
        outputArea.setFont(font);
        outputArea.setEditable(false);
        outputPanel.add(new JScrollPane(outputArea), BorderLayout.CENTER);
        mainPanel.add(outputPanel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JButton encryptButton = new JButton("Encode");
        encryptButton.setFont(font);

        JButton decryptButton = new JButton("Decode");
        decryptButton.setFont(font);

        encryptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String key = keyField.getText();
                String file = fileField.getText();
                try {
                    Scanner in = new Scanner(new FileInputStream(file));
                    StringBuilder contents = new StringBuilder();
                    while (in.hasNextLine()) {
                        contents.append(in.nextLine());
                    }

                    String plainText = contents.toString();
                    String algorithm = (String) algorithmBox.getSelectedItem();
                    Encrypter encrypter = null;
                    switch (algorithm.toLowerCase()) {
                        case "playfair" -> encrypter = new PlayfairEncrypter();
                        case "vigenere" -> encrypter = new VigenereEncrypter();
                    }

                    String result = encrypter.encrypt(plainText, key);
                    outputArea.setText(result);
                    File dest = new File(file);
                    FileWriter writer = new FileWriter(dest.getParent() + "/" + dest.getName() + "_res");
                    writer.write(result);
                    writer.close();
                } catch (RuntimeException ex) {
                    outputArea.setText(ex.getMessage());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                //outputArea.setText("Key: " + key + "\nFile: " + file + "\nAlgorithm: " + algorithm);
            }
        });

        decryptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String key = keyField.getText();
                String file = fileField.getText();
                try {
                    Scanner in = new Scanner(new FileInputStream(file));
                    StringBuilder contents = new StringBuilder();
                    while (in.hasNextLine()) {
                        contents.append(in.nextLine());
                    }

                    String plainText = contents.toString();
                    String algorithm = (String) algorithmBox.getSelectedItem();
                    Encrypter encrypter = null;
                    switch (algorithm.toLowerCase()) {
                        case "playfair" -> encrypter = new PlayfairEncrypter();
                        case "vigenere" -> encrypter = new VigenereEncrypter();
                    }

                    String result = encrypter.decrypt(plainText, key);
                    outputArea.setText(result);
                    File dest = new File(file);
                    FileWriter writer = new FileWriter(dest.getParent() + "/" + dest.getName() + "_res");
                    writer.write(result);
                    writer.close();
                } catch (RuntimeException ex) {
                    outputArea.setText(ex.getMessage());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                //outputArea.setText("Key: " + key + "\nFile: " + file + "\nAlgorithm: " + algorithm);
            }
        });

        buttonPanel.add(encryptButton);
        buttonPanel.add(decryptButton);
        mainPanel.add(buttonPanel);

        add(mainPanel);
    }

    public static void main(String[] args) {
        CipherForm form = new CipherForm();
        form.setVisible(true);
    }
}

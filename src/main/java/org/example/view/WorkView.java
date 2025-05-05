package org.example.view;

import org.example.utils.UIConstants;
import org.example.utils.GradientPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Представление для добавления работ мастера
 */
public class WorkView extends JFrame {
    private JTextField titleField;
    private JTextArea descriptionArea;
    private JButton uploadImageButton;  // Переименовано для единообразия
    private JButton saveButton;
    private JButton cancelButton;
    private JLabel imagePreviewLabel;
    private JTextField priceField;
    private String savedImagePath;

    public WorkView() {
        initUI();
        setupLayout();
    }

    private void initUI() {
        setTitle(UIConstants.APP_TITLE + " - Добавить работу");
        setSize(UIConstants.WORK_WINDOW_SIZE);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void setupLayout() {
        JPanel mainPanel = new GradientPanel(
                UIConstants.MAIN_BACKGROUND,
                UIConstants.SECONDARY_BACKGROUND
        );
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Панель формы
        JPanel formPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        formPanel.setOpaque(false);

        // Поля формы
        titleField = createTextField("Название работы");
        descriptionArea = createTextArea("Описание работы");
        priceField = createTextField("Цена");
        imagePreviewLabel = new JLabel("", SwingConstants.CENTER);
        imagePreviewLabel.setPreferredSize(new Dimension(300, 300));

        // Кнопки
        uploadImageButton = createButton("Загрузить изображение");
        saveButton = createButton("Сохранить работу");
        cancelButton = createButton("Отмена");

        // Добавление компонентов
        formPanel.add(createLabel("Название работы:"));
        formPanel.add(titleField);
        formPanel.add(createLabel("Описание:"));
        formPanel.add(new JScrollPane(descriptionArea));
        formPanel.add(createLabel("Цена:"));
        formPanel.add(priceField);
        formPanel.add(createLabel("Изображение:"));
        formPanel.add(imagePreviewLabel);
        formPanel.add(uploadImageButton);
        formPanel.add(saveButton);
        formPanel.add(cancelButton);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        add(mainPanel);
    }

    private JTextField createTextField(String placeholder) {
        JTextField field = new JTextField();
        field.setFont(UIConstants.INPUT_FONT);
        field.setBorder(UIConstants.createInputBorder());
        field.setBackground(UIConstants.INPUT_BACKGROUND);
        field.setForeground(UIConstants.PRIMARY_TEXT_COLOR);
        return field;
    }

    private JTextArea createTextArea(String placeholder) {
        JTextArea area = new JTextArea(5, 20);
        area.setFont(UIConstants.INPUT_FONT);
        area.setBorder(UIConstants.createInputBorder());
        area.setBackground(UIConstants.INPUT_BACKGROUND);
        area.setForeground(UIConstants.PRIMARY_TEXT_COLOR);
        area.setLineWrap(true);
        return area;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(UIConstants.BUTTON_FONT);
        button.setBackground(UIConstants.BUTTON_COLOR);
        button.setForeground(UIConstants.PRIMARY_TEXT_COLOR);
        button.setFocusPainted(false);
        button.setBorder(UIConstants.createButtonBorder());
        return button;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(UIConstants.LABEL_FONT);
        label.setForeground(UIConstants.PRIMARY_TEXT_COLOR);
        return label;
    }

    public JButton getUploadImageButton() {  // Единообразное название
        return uploadImageButton;
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }

    // Геттеры для контроллера

    public String getTitleText() {
        return titleField.getText();
    }

    public String getDescriptionText() {
        return descriptionArea.getText();
    }

    public String getPriceText() {
        return priceField.getText();
    }

    public String getImagePath() {
        return savedImagePath;
    }

    public void setImagePath(String path) {
        this.savedImagePath = path != null ? path : "";
    }

    public void setImagePreview(ImageIcon icon) {
        imagePreviewLabel.setIcon(icon);
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public void close() {
        dispose();
    }
}
package org.example.view;

import org.example.utils.UIConstants;
import org.example.utils.GradientPanel;
import javax.swing.*;
import java.awt.*;


public class WorkView extends JFrame {
    private JTextField titleField;
    private JTextArea descriptionArea;
    private JButton uploadImageButton;
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
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Центральная панель с фиксированной шириной
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setOpaque(false);
        formPanel.setMaximumSize(new Dimension(400, Integer.MAX_VALUE));

        // Создаем компоненты
        titleField = createStyledTextField("Название работы");
        descriptionArea = createStyledTextArea("Описание работы");
        priceField = createStyledTextField("Цена");
        imagePreviewLabel = new JLabel("", SwingConstants.CENTER);
        imagePreviewLabel.setPreferredSize(new Dimension(300, 200));

        uploadImageButton = createButton("Загрузить изображение");
        saveButton = createButton("Сохранить работу");
        cancelButton = createButton("Отмена");

        // Добавляем компоненты с жесткими отступами
        addComponentPair(formPanel, "Название работы:", titleField);
        addComponentPair(formPanel, "Описание:", new JScrollPane(descriptionArea));
        addComponentPair(formPanel, "Цена:", priceField);

        // Добавляем изображение
        JLabel imageLabel = createLabel("Изображение:");
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        formPanel.add(imageLabel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(imagePreviewLabel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Добавляем кнопки
        addButton(formPanel, uploadImageButton);
        addButton(formPanel, saveButton);
        addButton(formPanel, cancelButton);

        // Центрируем форму
        mainPanel.add(formPanel);
        add(mainPanel);
    }

    private void addComponentPair(JPanel panel, String labelText, Component component) {
        JLabel label = createLabel(labelText);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));

        // Устанавливаем фиксированные размеры для компонентов ввода
        component.setMaximumSize(new Dimension(Integer.MAX_VALUE, component.getPreferredSize().height));
        panel.add(component);
    }

    private void addButton(JPanel panel, JButton button) {
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        panel.add(button);
    }

    private JTextField createStyledTextField(String placeholder) {
        JTextField field = new JTextField();
        field.setFont(UIConstants.INPUT_FONT);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(100, 100, 100), 1),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        field.setBackground(new Color(220, 220, 220));
        field.setForeground(Color.BLACK);
        return field;
    }

    private JTextArea createStyledTextArea(String placeholder) {
        JTextArea area = new JTextArea(3, 20);
        area.setFont(UIConstants.INPUT_FONT);
        area.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(100, 100, 100), 1),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        area.setBackground(new Color(220, 220, 220));
        area.setForeground(Color.BLACK);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        return area;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(UIConstants.BUTTON_FONT);
        button.setBackground(new Color(80, 80, 80));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        return button;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(UIConstants.LABEL_FONT.deriveFont(Font.BOLD));
        label.setForeground(new Color(240, 240, 240));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    // Остальные методы остаются без изменений
    public JButton getUploadImageButton() {
        return uploadImageButton;
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }

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
package org.example.view;

import org.example.utils.GradientPanel;
import org.example.utils.UIConstants;
import javax.swing.*;
import java.awt.*;

public class LoginWindow extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private JButton backButton;

    public LoginWindow() {
        initUI();
        setupLayout();
    }

    private void initUI() {
        setTitle(UIConstants.APP_TITLE + " - Вход");
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void setupLayout() {
        JPanel mainPanel = new GradientPanel(
                UIConstants.MAIN_BACKGROUND,
                UIConstants.SECONDARY_BACKGROUND
        );
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));

        // Заголовок
        JLabel titleLabel = new JLabel("Вход в систему", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(UIConstants.PRIMARY_TEXT_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Поля ввода
        emailField = createField("Email", "example@example.com");
        passwordField = createPasswordField("Пароль");

        // Кнопки
        loginButton = createButton("Войти");
        registerButton = createButton("Регистрация");
        backButton = createButton("Назад");

        // Компоновка
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(30));
        mainPanel.add(createLabeledComponent("Email:", emailField));
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(createLabeledComponent("Пароль:", passwordField));
        mainPanel.add(Box.createVerticalStrut(30));
        mainPanel.add(loginButton);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(registerButton);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(backButton);

        add(mainPanel);
    }

    // Методы создания компонентов
    private JTextField createField(String placeholder, String tooltip) {
        JTextField field = new JTextField();
        field.setMaximumSize(new Dimension(400, 35));
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setForeground(UIConstants.PRIMARY_TEXT_COLOR);
        field.setBackground(new Color(40, 40, 50));
        field.setBorder(UIConstants.createInputBorder());
        field.setToolTipText(tooltip);
        field.setCaretColor(UIConstants.PRIMARY_TEXT_COLOR);
        return field;
    }

    private JPasswordField createPasswordField(String placeholder) {
        JPasswordField field = new JPasswordField();
        field.setMaximumSize(new Dimension(400, 35));
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setForeground(UIConstants.PRIMARY_TEXT_COLOR);
        field.setBackground(new Color(40, 40, 50));
        field.setBorder(UIConstants.createInputBorder());
        field.setToolTipText(placeholder);
        field.setCaretColor(UIConstants.PRIMARY_TEXT_COLOR);
        return field;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(UIConstants.BUTTON_FONT);
        button.setBackground(UIConstants.BUTTON_COLOR);
        button.setForeground(UIConstants.PRIMARY_TEXT_COLOR);
        button.setFocusPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(200, 35));
        button.setBorder(UIConstants.createButtonBorder());

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(UIConstants.BUTTON_HOVER_COLOR);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(UIConstants.BUTTON_COLOR);
            }
        });

        return button;
    }

    private JPanel createLabeledComponent(String labelText, JComponent component) {
        JPanel panel = new JPanel(new BorderLayout(10, 5));
        panel.setOpaque(false);

        JLabel label = new JLabel(labelText);
        label.setForeground(UIConstants.PRIMARY_TEXT_COLOR);
        label.setFont(new Font("Arial", Font.PLAIN, 14));

        panel.add(label, BorderLayout.NORTH);
        panel.add(component, BorderLayout.CENTER);

        return panel;
    }

    // Геттеры для контроллера
    public JButton getLoginButton() { return loginButton; }
    public JButton getRegisterButton() { return registerButton; }
    public JButton getBackButton() { return backButton; }
    public String getEmail() { return emailField.getText().trim(); }
    public String getPassword() { return new String(passwordField.getPassword()); }


    public void close() {
        dispose();
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Ошибка", JOptionPane.ERROR_MESSAGE);
    }

    public void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Успех", JOptionPane.INFORMATION_MESSAGE);
    }

}
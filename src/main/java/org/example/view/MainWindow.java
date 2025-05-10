package org.example.view;

import org.example.utils.UIConstants;
import org.example.utils.GradientPanel;
import javax.swing.*;
import java.awt.*;


public class MainWindow extends JFrame {
    private JButton loginButton;
    private JButton registerButton;
    private JLabel titleLabel;

    public MainWindow() {
        initUI();
        setupLayout();
    }

    private void initUI() {
        setTitle(UIConstants.APP_TITLE);
        setSize(UIConstants.MAIN_WINDOW_SIZE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void setupLayout() {
        JPanel mainPanel = new GradientPanel(
                UIConstants.MAIN_BACKGROUND,
                UIConstants.SECONDARY_BACKGROUND
        );
        mainPanel.setLayout(new BorderLayout());

        // Заголовок
        titleLabel = new JLabel(UIConstants.STUDIO_NAME, SwingConstants.CENTER);
        titleLabel.setFont(UIConstants.MAIN_TITLE_FONT);
        titleLabel.setForeground(UIConstants.PRIMARY_TEXT_COLOR);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(
                0, 0, UIConstants.TITLE_BOTTOM_PADDING, 0
        ));

        // Кнопки
        loginButton = createButton("Войти");
        registerButton = createButton("Зарегистрироваться");

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, UIConstants.BUTTON_GAP, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(
                UIConstants.BUTTON_PANEL_PADDING.top,
                UIConstants.BUTTON_PANEL_PADDING.left,
                UIConstants.BUTTON_PANEL_PADDING.bottom,
                UIConstants.BUTTON_PANEL_PADDING.right
        ));
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        mainPanel.add(titleLabel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
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

    // Геттеры для контроллера
    public JButton getLoginButton() {
        return loginButton;
    }

    public JButton getRegisterButton() {
        return registerButton;
    }

    public void close() {
        dispose();
    }
}
package org.example.view;

import org.example.model.User;
import org.example.utils.GradientPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import static org.example.utils.UIConstants.*;


public class ClientWindow extends JFrame {
    private JButton chooseMasterButton;
    private JButton makeAppointmentButton;
    private JButton backButton;
    private User user; // Добавляем поле для хранения пользователя

    // Основной конструктор, принимающий User
    public ClientWindow(User user) {
        this.user = user;
        initComponents(user.getName()); // Используем имя пользователя
        setupFrame();
    }

    // Устаревший конструктор (можно удалить, если не используется)
    @Deprecated
    public ClientWindow(String clientName) {
        initComponents(clientName);
        setupFrame();
    }

    private void initComponents(String clientName) {
        // Главная панель с градиентом
        JPanel mainPanel = new GradientPanel(MAIN_BACKGROUND, SECONDARY_BACKGROUND);
        mainPanel.setLayout(new BorderLayout());

        // Заголовок
        JLabel welcomeLabel = new JLabel("Welcome, " + clientName + "!", SwingConstants.CENTER);
        welcomeLabel.setFont(MAIN_TITLE_FONT);
        welcomeLabel.setForeground(PRIMARY_TEXT_COLOR);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // Панель с кнопками
        JPanel buttonPanel = createButtonPanel();

        // Компоновка элементов
        mainPanel.add(welcomeLabel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        this.add(mainPanel);
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 1, BUTTON_GAP, BUTTON_GAP));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 60, 20, 60));

        chooseMasterButton = createStyledButton("Выбрать мастера");
        makeAppointmentButton = createStyledButton("Записаться");
        backButton = createStyledButton("Назад");

        panel.add(chooseMasterButton);
        panel.add(makeAppointmentButton);
        panel.add(backButton);

        return panel;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(BUTTON_FONT);
        button.setBackground(BUTTON_COLOR);
        button.setForeground(PRIMARY_TEXT_COLOR);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BUTTON_BORDER, 1),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_HOVER_COLOR);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_COLOR);
            }
        });

        return button;
    }

    private void setupFrame() {
        setTitle("Окно клиента");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(MAIN_WINDOW_SIZE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    // Геттеры для кнопок
    public JButton getChooseMasterButton() { return chooseMasterButton; }
    public JButton getMakeAppointmentButton() { return makeAppointmentButton; }
    public JButton getBackButton() { return backButton; }

    // Геттер для пользователя
    public User getUser() { return user; }

    public void close() {
        dispose();
    }
}
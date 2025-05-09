package org.example.view;

import org.example.utils.GradientPanel;
import org.example.utils.UIConstants;
import javax.swing.*;
import java.awt.*;


public class AppointmentWindow extends JFrame {
    private JLabel titleLabel;
    private JButton backButton;

    public AppointmentWindow() {
        initUI();
        setupLayout();
    }

    private void setupLayout() {
        JPanel mainPanel = new GradientPanel(
                UIConstants.MAIN_BACKGROUND,
                UIConstants.SECONDARY_BACKGROUND
        );
        mainPanel.setLayout(new BorderLayout());

        // Заголовок вверху
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setOpaque(false);
        titleLabel = new JLabel(UIConstants.APPOINTMENT);
        titleLabel.setFont(UIConstants.MAIN_TITLE_FONT);
        titleLabel.setForeground(UIConstants.PRIMARY_TEXT_COLOR);
        titlePanel.add(titleLabel);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        mainPanel.add(titlePanel, BorderLayout.NORTH);

        // Центральная панель с содержимым
        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Панель с кнопкой "Назад" внизу
        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        backButton = createButton("Назад");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottomPanel.add(Box.createHorizontalGlue());
        bottomPanel.add(backButton);
        bottomPanel.add(Box.createHorizontalGlue());

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void initUI() {
        setTitle(UIConstants.APP_TITLE);
        setSize(UIConstants.MAIN_WINDOW_SIZE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
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

    public void close() {
        dispose();
    }

    public JButton getBackButton() { return backButton; }
}
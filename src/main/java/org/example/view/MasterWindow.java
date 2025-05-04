package org.example.view;


import org.example.model.MasterModel;
import org.example.utils.GradientPanel;
import javax.swing.*;
import java.awt.*;
import static org.example.utils.UIConstants.*;

public class MasterWindow extends JFrame {
    private JButton addWorkButton;
    private JButton viewWorksButton;
    private JButton viewScheduleButton;
    private JButton viewAppointmentsButton;
    private JButton logoutButton;

    public MasterWindow(String masterName) {
        initUI(masterName);
    }

    private void initUI(String masterName) {
        setTitle("Панель мастера");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(MAIN_WINDOW_SIZE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new GradientPanel(MAIN_BACKGROUND, SECONDARY_BACKGROUND);
        mainPanel.setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel(masterName + ", welcome!", SwingConstants.CENTER);
        welcomeLabel.setFont(MAIN_TITLE_FONT);
        welcomeLabel.setForeground(PRIMARY_TEXT_COLOR);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        mainPanel.add(welcomeLabel, BorderLayout.NORTH);
        mainPanel.add(createButtonPanel(), BorderLayout.CENTER);

        add(mainPanel);
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 1, BUTTON_GAP, BUTTON_GAP));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 60, 20, 60));

        addWorkButton = createStyledButton("Добавить работу");
        viewWorksButton = createStyledButton("Мои работы");
        viewScheduleButton = createStyledButton("Расписание");
        viewAppointmentsButton = createStyledButton("Записи клиентов");
        logoutButton = createStyledButton("Выйти");

        panel.add(addWorkButton);
        panel.add(viewWorksButton);
        panel.add(viewScheduleButton);
        panel.add(viewAppointmentsButton);
        panel.add(logoutButton);

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

    // Геттеры для кнопок
    public JButton getAddWorkButton() { return addWorkButton; }
    public JButton getViewWorksButton() { return viewWorksButton; }
    public JButton getViewScheduleButton() { return viewScheduleButton; }
    public JButton getViewAppointmentsButton() { return viewAppointmentsButton; }
    public JButton getLogoutButton() { return logoutButton; }

    // Методы для работы с UI
//    public void showAddWorkDialog() {
//        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
//        panel.setOpaque(false);
//
//        JTextField titleField = new JTextField();
//        JTextArea descriptionArea = new JTextArea(3, 20);
//        JSpinner priceSpinner = new JSpinner(new SpinnerNumberModel(1000, 500, 50000, 500));
//        JButton uploadBtn = new JButton("Загрузить фото");
//
//        panel.add(new JLabel("Название:"));
//        panel.add(titleField);
//        panel.add(new JLabel("Описание:"));
//        panel.add(new JScrollPane(descriptionArea));
//        panel.add(new JLabel("Цена:"));
//        panel.add(priceSpinner);
//        panel.add(new JLabel("Фото:"));
//        panel.add(uploadBtn);
//
//        int result = JOptionPane.showConfirmDialog(
//                this,
//                panel,
//                "Добавить работу",
//                JOptionPane.OK_CANCEL_OPTION,
//                JOptionPane.PLAIN_MESSAGE
//        );
//
//        if (result == JOptionPane.OK_OPTION) {
//            // Возвращаем данные через массив Object
//            Object[] resultData = {
//                    titleField.getText(),
//                    descriptionArea.getText(),
//                    priceSpinner.getValue()
//            };
//            // Контроллер получит эти данные и обработает
//        }
//    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}

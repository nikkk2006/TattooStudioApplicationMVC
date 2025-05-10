package org.example.view;

import org.example.controller.MasterController;
import org.example.database.ScheduleDao;
import org.example.model.ScheduleModel;
import org.example.utils.UIConstants;
import org.example.utils.GradientPanel;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;


public class MasterScheduleWindow extends JFrame {
    private final MasterController controller;
    private final int masterId;

    private JTextField dateField;
    private JTextField startTimeField;
    private JTextField endTimeField;
    private JButton addButton;

    public MasterScheduleWindow(MasterController controller, int masterId) {
        this.controller = controller;
        this.masterId = masterId;
        initUI();
    }

    private void initUI() {
        setTitle(UIConstants.APP_TITLE + " - Добавление слотов расписания");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        setupLayout();
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
        dateField = createStyledTextField("ГГГГ-ММ-ДД");
        startTimeField = createStyledTextField("ЧЧ:ММ");
        endTimeField = createStyledTextField("ЧЧ:ММ");
        addButton = createButton("Добавить слот");

        // Добавляем компоненты с жесткими отступами
        addComponentPair(formPanel, "Дата (ГГГГ-ММ-ДД):", dateField);
        addComponentPair(formPanel, "Время начала (ЧЧ:ММ):", startTimeField);
        addComponentPair(formPanel, "Время окончания (ЧЧ:ММ):", endTimeField);

        // Добавляем кнопку
        formPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        addButton(formPanel, addButton);

        // Центрируем форму
        mainPanel.add(formPanel);
        add(mainPanel);

        // Устанавливаем обработчик событий
        addButton.addActionListener(e -> addScheduleSlot());
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

    private void addScheduleSlot() {
        try {
            LocalDate date = LocalDate.parse(dateField.getText());
            LocalTime startTime = LocalTime.parse(startTimeField.getText());
            LocalTime endTime = LocalTime.parse(endTimeField.getText());

            if (endTime.isBefore(startTime)) {
                JOptionPane.showMessageDialog(this,
                        "Время окончания должно быть позже времени начала",
                        "Ошибка", JOptionPane.ERROR_MESSAGE);
                return;
            }

            ScheduleModel schedule = new ScheduleModel();
            schedule.setMasterId(masterId);
            schedule.setDate(date);
            schedule.setStartTime(startTime);
            schedule.setEndTime(endTime);
            schedule.setAvailable(true);

            ScheduleDao.addScheduleSlot(schedule);

            // Очистка полей
            dateField.setText("");
            startTimeField.setText("");
            endTimeField.setText("");

            JOptionPane.showMessageDialog(this,
                    "Слот расписания успешно добавлен",
                    "Успех", JOptionPane.INFORMATION_MESSAGE);

        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this,
                    "Неверный формат данных. Используйте:\nДата: ГГГГ-ММ-ДД\nВремя: ЧЧ:ММ",
                    "Ошибка", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Ошибка при сохранении в базу данных: " + e.getMessage(),
                    "Ошибка", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
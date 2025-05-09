package org.example.view;

import org.example.model.MasterModel;
import org.example.utils.GradientPanel;
import org.example.utils.UIConstants;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AppointmentWindow extends JFrame {
    private JLabel titleLabel;
    private JButton backButton;
    private JButton refreshButton;
    private JTable scheduleTable;
    private List<MasterModel> masters;

    public AppointmentWindow(List<MasterModel> masters) {
        this.masters = masters;
        initUI();
        setupLayout();
        loadSchedules();
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

        // Центральная панель с таблицей
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Создаем модель таблицы
        DefaultTableModel tableModel = new DefaultTableModel(
                new Object[]{"Мастер", "Дата", "Начало", "Конец", "Доступно"}, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 4 ? Boolean.class : String.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        scheduleTable = new JTable(tableModel);
        scheduleTable.setFont(UIConstants.BUTTON_FONT);
        scheduleTable.setForeground(UIConstants.PRIMARY_TEXT_COLOR);
        scheduleTable.setBackground(UIConstants.SECONDARY_BACKGROUND);
        scheduleTable.setSelectionBackground(UIConstants.BUTTON_COLOR);
        scheduleTable.setSelectionForeground(UIConstants.PRIMARY_TEXT_COLOR);
        scheduleTable.setRowHeight(30);
        scheduleTable.setFillsViewportHeight(true);
        scheduleTable.getTableHeader().setFont(UIConstants.BUTTON_FONT);
        scheduleTable.getTableHeader().setBackground(UIConstants.BUTTON_COLOR);
        scheduleTable.getTableHeader().setForeground(UIConstants.PRIMARY_TEXT_COLOR);

        JScrollPane scrollPane = new JScrollPane(scheduleTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(UIConstants.SECONDARY_BACKGROUND);

        centerPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Панель с кнопками внизу
        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        refreshButton = createButton("Обновить");
        refreshButton.addActionListener(e -> loadSchedules());

        backButton = createButton("Назад");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        bottomPanel.add(Box.createHorizontalGlue());
        bottomPanel.add(refreshButton);
        bottomPanel.add(Box.createHorizontalStrut(20));
        bottomPanel.add(backButton);
        bottomPanel.add(Box.createHorizontalGlue());

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    // Метод для загрузки расписания мастеров
    private void loadSchedules() {
        clearTable();

        if (masters == null) {
            return;
        }

        for (MasterModel master : masters) {

            if (master.getSchedule().isEmpty()) {
                addScheduleRow(
                        master.getMasterName(),
                        "Нет данных",
                        "",
                        "",
                        false
                );
            } else {
                for (MasterModel.ScheduleSlot slot : master.getSchedule()) {
                    addScheduleRow(
                            master.getMasterName(),
                            slot.getDate(),
                            slot.getStartTime(),
                            slot.getEndTime(),
                            slot.isAvailable()
                    );
                }
            }
        }
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

    public void addScheduleRow(String master, String date, String startTime, String endTime, boolean isAvailable) {
        DefaultTableModel model = (DefaultTableModel) scheduleTable.getModel();
        model.addRow(new Object[]{master, date, startTime, endTime, isAvailable});
    }

    public void clearTable() {
        DefaultTableModel model = (DefaultTableModel) scheduleTable.getModel();
        model.setRowCount(0);
    }

    public void close() {
        dispose();
    }

    public JButton getBackButton() {
        return backButton;
    }

    public JButton getRefreshButton() {
        return refreshButton;
    }
}
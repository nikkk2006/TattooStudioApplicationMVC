package org.example.view;

import org.example.database.AppointmentDao;
import org.example.database.DatabaseManager;
import org.example.model.AppointmentModel;
import org.example.model.MasterModel;
import org.example.utils.GradientPanel;
import org.example.utils.UIConstants;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AppointmentWindow extends JFrame {
    private JLabel titleLabel;
    private JButton backButton;
    private JButton refreshButton;
    private JButton bookButton;
    private JTable scheduleTable;
    private List<MasterModel> masters;
    private int currentClientId;
    private Timer refreshTimer;

    public AppointmentWindow(List<MasterModel> masters, int clientId) {
        this.masters = masters;
        this.currentClientId = clientId;

        // Обновляем расписание у всех мастеров перед отображением
        for (MasterModel master : masters) {
            master.loadScheduleFromDatabase();
        }

        initUI();
        setupLayout();
        loadSchedules();

        // Таймер автообновления каждые 30 секунд
        refreshTimer = new Timer(30000, e -> loadSchedules());
        refreshTimer.start();

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                refreshTimer.stop();
            }
        });
    }

    private void setupLayout() {
        JPanel mainPanel = new GradientPanel(
                UIConstants.MAIN_BACKGROUND,
                UIConstants.SECONDARY_BACKGROUND
        );
        mainPanel.setLayout(new BorderLayout());

        // Заголовок
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setOpaque(false);
        titleLabel = new JLabel(UIConstants.APPOINTMENT);
        titleLabel.setFont(UIConstants.MAIN_TITLE_FONT);
        titleLabel.setForeground(UIConstants.PRIMARY_TEXT_COLOR);
        titlePanel.add(titleLabel);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        mainPanel.add(titlePanel, BorderLayout.NORTH);

        // Таблица с расписанием
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

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

        // Панель кнопок
        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        refreshButton = createButton("Обновить");
        refreshButton.addActionListener(e -> loadSchedules());

        bookButton = createButton("Записаться");
        bookButton.addActionListener(new BookButtonListener());

        backButton = createButton("Назад");

        bottomPanel.add(Box.createHorizontalGlue());
        bottomPanel.add(refreshButton);
        bottomPanel.add(Box.createHorizontalStrut(20));
        bottomPanel.add(bookButton);
        bottomPanel.add(Box.createHorizontalStrut(20));
        bottomPanel.add(backButton);
        bottomPanel.add(Box.createHorizontalGlue());

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private class BookButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = scheduleTable.getSelectedRow();
            if (selectedRow == -1) {
                showError("Пожалуйста, выберите время для записи");
                return;
            }

            boolean isAvailable = (boolean) scheduleTable.getValueAt(selectedRow, 4);
            if (!isAvailable) {
                showError("Выбранное время уже занято");
                return;
            }

            String masterName = (String) scheduleTable.getValueAt(selectedRow, 0);
            String date = (String) scheduleTable.getValueAt(selectedRow, 1);
            String startTime = (String) scheduleTable.getValueAt(selectedRow, 2);
            String endTime = (String) scheduleTable.getValueAt(selectedRow, 3);

            MasterModel selectedMaster = findMasterByName(masterName);
            if (selectedMaster == null) {
                showError("Ошибка при поиске мастера");
                return;
            }

            MasterModel.ScheduleSlot selectedSlot = findScheduleSlot(selectedMaster, date, startTime, endTime);
            if (selectedSlot == null) {
                showError("Ошибка при поиске времени записи");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(
                    AppointmentWindow.this,
                    String.format("Вы хотите записаться к мастеру %s на %s с %s до %s?",
                            masterName, date, startTime, endTime),
                    "Подтверждение записи",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (confirm == JOptionPane.YES_OPTION) {
                createAppointment(selectedMaster, selectedSlot);
            }
        }

        private void createAppointment(MasterModel master, MasterModel.ScheduleSlot slot) {
            try {
                // Проверяем доступность слота в БД (дополнительная защита)
                if (!isSlotAvailableInDB(slot.getId())) {
                    showError("Это время уже занято другим клиентом");
                    loadSchedules(); // Обновляем таблицу
                    return;
                }

                AppointmentModel appointment = new AppointmentModel();
                appointment.setClientId(currentClientId);
                appointment.setMasterId(master.getId());
                appointment.setScheduleId(slot.getId());
                appointment.setStatus("pending"); // Исправленный статус

                AppointmentDao appointmentDao = new AppointmentDao();
                boolean success = appointmentDao.createAppointment(appointment);

                if (success) {
                    markSlotAsBooked(slot.getId());
                    JOptionPane.showMessageDialog(
                            AppointmentWindow.this,
                            "Вы успешно записаны!",
                            "Успех",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    loadSchedules();
                } else {
                    showError("Ошибка при записи");
                }
            } catch (Exception ex) {
                showError("Произошла ошибка: " + ex.getMessage());
            }
        }

        private boolean isSlotAvailableInDB(int scheduleId) {
            String sql = "SELECT is_available FROM schedule WHERE id = ?";
            try (Connection conn = DatabaseManager.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, scheduleId);
                ResultSet rs = pstmt.executeQuery();
                return rs.next() && rs.getBoolean("is_available");
            } catch (SQLException e) {
                System.err.println("Error checking slot availability: " + e.getMessage());
                return false;
            }
        }

        private void markSlotAsBooked(int scheduleId) {
            String sql = "UPDATE schedule SET is_available = false WHERE id = ?";

            try (Connection conn = DatabaseManager.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, scheduleId);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.err.println("Error updating slot availability: " + e.getMessage());
            }
        }
    }

    private MasterModel findMasterByName(String masterName) {
        for (MasterModel master : masters) {
            if (master.getMasterName().equals(masterName)) {
                return master;
            }
        }
        return null;
    }

    private MasterModel.ScheduleSlot findScheduleSlot(MasterModel master, String date, String startTime, String endTime) {
        for (MasterModel.ScheduleSlot slot : master.getSchedule()) {
            if (slot.getDate().equals(date) &&
                    slot.getStartTime().equals(startTime) &&
                    slot.getEndTime().equals(endTime)) {
                return slot;
            }
        }
        return null;
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(
                this,
                message,
                "Ошибка",
                JOptionPane.ERROR_MESSAGE
        );
    }

    private void loadSchedules() {
        clearTable();
        AppointmentDao appointmentDao = new AppointmentDao();
        List<AppointmentModel> activeAppointments = appointmentDao.getActiveAppointments();

        for (MasterModel master : masters) {
            master.loadScheduleFromDatabase();
            for (MasterModel.ScheduleSlot slot : master.getSchedule()) {
                boolean isBooked = activeAppointments.stream()
                        .anyMatch(app -> app.getScheduleId() == slot.getId());

                addScheduleRow(
                        master.getMasterName(),
                        slot.getDate(),
                        slot.getStartTime(),
                        slot.getEndTime(),
                        slot.isAvailable() && !isBooked
                );
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
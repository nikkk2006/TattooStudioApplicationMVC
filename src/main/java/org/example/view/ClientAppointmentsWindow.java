package org.example.view;

import org.example.model.AppointmentModel;
import org.example.database.AppointmentDao;
import org.example.utils.GradientPanel;
import org.example.utils.UIConstants;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;


public class ClientAppointmentsWindow extends JFrame {
    private final AppointmentDao appointmentDAO;
    private final int masterId;
    private final JFrame parentWindow;

    public ClientAppointmentsWindow(int masterId, AppointmentDao appointmentDAO, JFrame parentWindow) {
        this.masterId = masterId;
        this.appointmentDAO = appointmentDAO;
        this.parentWindow = parentWindow;
        initUI();
    }

    private void initUI() {
        setTitle(UIConstants.APP_TITLE + " - Записи клиентов");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(550, 400);
        setLocationRelativeTo(parentWindow);
        setResizable(false);

        setupLayout();
    }

    private void setupLayout() {
        JPanel mainPanel = new GradientPanel(
                UIConstants.MAIN_BACKGROUND,
                UIConstants.SECONDARY_BACKGROUND
        );
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Заголовок
        JLabel titleLabel = new JLabel("Client's appointments", SwingConstants.CENTER);
        titleLabel.setFont(UIConstants.MAIN_TITLE_FONT);
        titleLabel.setForeground(UIConstants.PRIMARY_TEXT_COLOR);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Таблица с записями
        JTable appointmentsTable = createAppointmentsTable();
        JScrollPane scrollPane = new JScrollPane(appointmentsTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(new Color(60, 63, 65));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Панель с кнопками
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        // Кнопка "Назад"
        JButton backButton = createButton("Назад");
        backButton.addActionListener(e -> {
            this.dispose();
            parentWindow.setVisible(true);
        });
        buttonPanel.add(backButton);

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
        button.setPreferredSize(new Dimension(120, 40));

        // Добавляем эффекты при наведении как в MainWindow
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

    private JTable createAppointmentsTable() {
        List<AppointmentModel> appointments = appointmentDAO.getAppointmentsByMaster(masterId);

        String[] columnNames = {"ID", "Клиент", "Дата", "Время", "Статус"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (AppointmentModel appointment : appointments) {
            Object[] row = {
                    appointment.getId(),
                    appointment.getClientName(),
                    appointment.getDate().toString(),
                    appointment.getTime().toString(),
                    appointment.getStatus()
            };
            model.addRow(row);
        }

        JTable table = new JTable(model);
        // Стиль таблицы остается темным как в предыдущей версии
        table.setBackground(new Color(60, 63, 65));
        table.setForeground(new Color(187, 187, 187));
        table.setGridColor(new Color(81, 81, 81));
        table.setSelectionBackground(new Color(75, 110, 175));
        table.setSelectionForeground(Color.WHITE);
        table.setFont(UIConstants.TABLE_FONT);
        table.setRowHeight(30);
        table.setFillsViewportHeight(true);

        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(77, 77, 77));
        header.setForeground(new Color(187, 187, 187));
        header.setFont(UIConstants.TABLE_HEADER_FONT);
        header.setBorder(BorderFactory.createLineBorder(new Color(81, 81, 81)));

        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
        table.getColumnModel().getColumn(2).setPreferredWidth(120);
        table.getColumnModel().getColumn(3).setPreferredWidth(80);
        table.getColumnModel().getColumn(4).setPreferredWidth(120);

        return table;
    }

    private void refreshTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        List<AppointmentModel> appointments = appointmentDAO.getAppointmentsByMaster(masterId);
        for (AppointmentModel appointment : appointments) {
            Object[] row = {
                    appointment.getId(),
                    appointment.getClientName(),
                    appointment.getDate().toString(),
                    appointment.getTime().toString(),
                    appointment.getStatus()
            };
            model.addRow(row);
        }
    }
}
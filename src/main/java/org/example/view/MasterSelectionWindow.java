package org.example.view;

import org.example.model.MasterModel;
import org.example.utils.UIConstants;
import org.example.utils.GradientPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MasterSelectionWindow extends JFrame {
    private JButton backButton;
    private JList<MasterModel> mastersList;

    public MasterSelectionWindow() {
        initUI();
        setupLayout();
    }

    private void initUI() {
        setTitle(UIConstants.APP_TITLE + " - Выбор мастера");
        setSize(UIConstants.MAIN_WINDOW_SIZE);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void setupLayout() {
        // Основная панель с градиентом
        GradientPanel mainPanel = new GradientPanel(
                UIConstants.MAIN_BACKGROUND,
                UIConstants.SECONDARY_BACKGROUND
        );
        mainPanel.setLayout(new BorderLayout());

        // Заголовок
        JLabel titleLabel = new JLabel("Choose the master", SwingConstants.CENTER);
        titleLabel.setFont(UIConstants.MAIN_TITLE_FONT);
        titleLabel.setForeground(UIConstants.PRIMARY_TEXT_COLOR);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(
                UIConstants.TITLE_TOP_PADDING, 0,
                UIConstants.TITLE_BOTTOM_PADDING, 0
        ));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Список мастеров
        mastersList = new JList<>();
        mastersList.setCellRenderer(new MasterListRenderer());
        mastersList.setBackground(UIConstants.SECONDARY_BACKGROUND);
        mastersList.setForeground(UIConstants.PRIMARY_TEXT_COLOR);
        mastersList.setSelectionBackground(UIConstants.BUTTON_COLOR);
        mastersList.setSelectionForeground(UIConstants.PRIMARY_TEXT_COLOR);
        mastersList.setFont(UIConstants.BUTTON_FONT);
        mastersList.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JScrollPane scrollPane = new JScrollPane(mastersList);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(UIConstants.SECONDARY_BACKGROUND);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Панель для кнопки "Назад" внизу
        JPanel bottomPanel = new JPanel(new GridBagLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        backButton = createButton("Назад");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        bottomPanel.add(backButton, gbc);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(UIConstants.BUTTON_FONT);
        button.setBackground(UIConstants.BUTTON_COLOR);
        button.setForeground(UIConstants.PRIMARY_TEXT_COLOR);
        button.setFocusPainted(false);
        button.setBorder(UIConstants.createButtonBorder());
        button.setPreferredSize(new Dimension(
                UIConstants.BUTTON_WIDTH,
                UIConstants.BUTTON_HEIGHT
        ));

        // Добавляем эффект при наведении
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

    public void displayMasters(List<MasterModel> masters) {
        DefaultListModel<MasterModel> model = new DefaultListModel<>();
        for (MasterModel master : masters) {
            model.addElement(master);
        }
        mastersList.setModel(model);
        mastersList.repaint();
    }

    private static class MasterListRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(
                JList<?> list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {

            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            if (value instanceof MasterModel) {
                MasterModel master = (MasterModel) value;
                setText(master.getMasterName());
                setIcon(new ImageIcon("path/to/default/icon.png")); // Добавьте иконку при необходимости
                setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            }
            return this;
        }
    }

    public interface MasterSelectionListener {
        void onMasterSelected(MasterModel master);
    }

    private MasterSelectionListener masterSelectionListener;

    public void addMasterSelectionListener(MasterSelectionListener listener) {
        this.masterSelectionListener = listener;
        mastersList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && masterSelectionListener != null) {
                MasterModel selected = mastersList.getSelectedValue();
                if (selected != null) {
                    masterSelectionListener.onMasterSelected(selected);
                }
            }
        });
    }

    public JButton getBackButton() {
        return backButton;
    }

    public void close() {
        dispose();
    }
}
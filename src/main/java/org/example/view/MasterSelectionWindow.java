package org.example.view;

import org.example.model.MasterModel;
import org.example.utils.UIConstants;
import org.example.utils.GradientPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MasterSelectionWindow extends JFrame {
    private JButton backButton;
    private JPanel mastersPanel;
    private JLabel titleLabel;

    public MasterSelectionWindow() {
        initUI();
        setupLayout();
    }

    private void initUI() {
        setTitle("Выбор мастера");
        setSize(400, 600); // Уже окно для списка
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void setupLayout() {
        GradientPanel mainPanel = new GradientPanel(
                UIConstants.MAIN_BACKGROUND,
                UIConstants.SECONDARY_BACKGROUND
        );
        mainPanel.setLayout(new BorderLayout());

        // Заголовок
        titleLabel = new JLabel("Выберите мастера", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Панель с мастерами - вертикальный список
        mastersPanel = new JPanel();
        mastersPanel.setLayout(new BoxLayout(mastersPanel, BoxLayout.Y_AXIS));
        mastersPanel.setOpaque(false);
        mastersPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JScrollPane scrollPane = new JScrollPane(mastersPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Кнопка "Назад"
        backButton = new JButton("Назад");
        backButton.setFont(new Font("Arial", Font.PLAIN, 16));
        backButton.setBackground(new Color(70, 70, 70));
        backButton.setForeground(Color.WHITE);
        backButton.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        buttonPanel.add(backButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    public void displayMasters(List<MasterModel> masters) {
        mastersPanel.removeAll();

        for (MasterModel master : masters) {
            JButton masterButton = createMasterButton(master);
            masterButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            mastersPanel.add(masterButton);
            mastersPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Отступ между кнопками
        }

        mastersPanel.revalidate();
        mastersPanel.repaint();
    }

    private JButton createMasterButton(MasterModel master) {
        JButton button = new JButton(master.getMasterName() + " - " + master.getSpecialization());
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setBackground(new Color(50, 50, 50));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50)); // Ширина на весь экран
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Можно добавить обработчик выбора мастера
        button.addActionListener(e -> {
            // Здесь будет логика выбора мастера
            System.out.println("Выбран мастер: " + master.getMasterName());
        });

        return button;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public void close() {
        dispose();
    }
}
package org.example.utils;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;


public class UIConstants {
    /*
     * Основные константы приложения
     */
    public static final String APP_TITLE = "Тату Салон";
    public static final String STUDIO_NAME = "BLACK INK";
    public static final String APPOINTMENT = "APPOINTMENT";

    /*
     * Цветовая палитра:
     * MAIN_BACKGROUND - основной цвет фона (верх градиента)
     * SECONDARY_BACKGROUND - вторичный цвет фона (низ градиента)
     * PRIMARY_TEXT_COLOR - основной цвет текста
     */
    public static final Color MAIN_BACKGROUND = new Color(15, 15, 15);
    public static final Color SECONDARY_BACKGROUND = new Color(57, 57, 66);
    public static final Color PRIMARY_TEXT_COLOR = new Color(220, 220, 220);

    /*
     * Стили кнопок:
     * BUTTON_COLOR - основной цвет кнопки
     * BUTTON_HOVER_COLOR - цвет при наведении
     * BUTTON_BORDER - цвет границы
     * BUTTON_FONT - шрифт кнопок
     * BUTTON_INSETS - внутренние отступы кнопок
     */
    public static final Color BUTTON_COLOR = new Color(50, 50, 60);
    public static final Color BUTTON_HOVER_COLOR = new Color(70, 70, 80);
    public static final Color BUTTON_BORDER = new Color(80, 80, 90);
    public static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 16);
    public static final Font LABEL_FONT = new Font("Arial", Font.BOLD, 14);
    public static final Insets BUTTON_INSETS = new Insets(8, 20, 8, 20);

    /*
     * Шрифты:
     * MAIN_TITLE_FONT - шрифт главного заголовка
     */
    public static final Font MAIN_TITLE_FONT = new Font("Old English Text MT", Font.BOLD, 48);
    public static final Font INPUT_FONT = new Font("Arial", Font.PLAIN, 14);

    /*
     * Размеры и отступы:
     * MAIN_WINDOW_SIZE - размер главного окна
     * BUTTON_PANEL_PADDING - отступы панели кнопок
     * TITLE_BOTTOM_PADDING - отступ под заголовком
     */
    public static final Dimension WORK_WINDOW_SIZE = new Dimension(500, 600);
    public static final Dimension MAIN_WINDOW_SIZE = new Dimension(600, 450);
    public static final Insets BUTTON_PANEL_PADDING = new Insets(0, 80, 80, 80);
    public static final int TITLE_BOTTOM_PADDING = 100;
    public static final int BUTTON_GAP = 20;


    public static final int TITLE_TOP_PADDING = 30;
    public static final int BUTTON_WIDTH = 120;
    public static final int BUTTON_HEIGHT = 40;

    /*
     * Методы для создания границ:
     * createButtonBorder() - граница для кнопок
     * createInputBorder() - граница для полей ввода
     */
    public static Border createButtonBorder() {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BUTTON_BORDER, 1),
                BorderFactory.createEmptyBorder(
                        BUTTON_INSETS.top,
                        BUTTON_INSETS.left,
                        BUTTON_INSETS.bottom,
                        BUTTON_INSETS.right
                )
        );
    }

    public static Border createInputBorder() {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 70, 80), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        );
    }
}
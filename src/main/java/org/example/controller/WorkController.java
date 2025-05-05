package org.example.controller;

import org.example.database.WorkDao;
import org.example.model.MasterModel;
import org.example.view.WorkView;
import org.example.utils.ImageUtils;
import org.example.utils.FileUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class WorkController {
    private final WorkView view;
    private final WorkDao workDao;
    private final MasterModel master;

    public WorkController(WorkView view, WorkDao workDao, MasterModel master) {
        this.view = view;
        this.workDao = workDao;
        this.master = master;
        initController();
    }

    private void initController() {
        setupEventListeners();
    }

    private void setupEventListeners() {
        view.getUploadImageButton().addActionListener(this::handleImageUpload);
        view.getSaveButton().addActionListener(this::handleSaveWork);
        view.getCancelButton().addActionListener(e -> view.close());
    }

    private void handleImageUpload(ActionEvent event) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Выберите изображение работы");
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileFilter() {
            @Override
            public boolean accept(File f) {
                String name = f.getName().toLowerCase();
                return f.isDirectory() || name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png");
            }

            @Override
            public String getDescription() {
                return "Изображения (JPG, PNG)";
            }
        });

        int returnValue = fileChooser.showOpenDialog(view);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String imagePath = selectedFile.getAbsolutePath();

            // Устанавливаем путь и показываем превью
            view.setImagePath(imagePath);
            ImageIcon icon = ImageUtils.createScaledIcon(imagePath, 300, 300);
            view.setImagePreview(icon);
            view.getUploadImageButton().setText("Изменить изображение");
        }
    }

    private void handleSaveWork(ActionEvent event) {
        try {
            if (!validateInput()) {
                return;
            }

            String title = view.getTitleText().trim();
            String description = view.getDescriptionText().trim();
            int price = Integer.parseInt(view.getPriceText().trim());
            String imagePath = view.getImagePath();

            // Дополнительная проверка imagePath
            if (imagePath == null || imagePath.isEmpty()) {
                view.showMessage("Изображение не загружено");
                return;
            }

            String savedImagePath = saveWorkImage(imagePath);
            if (savedImagePath == null) {
                view.showMessage("Не удалось сохранить изображение");
                return;
            }

            MasterModel.Work work = new MasterModel.Work(title, description, price);
            if (workDao.createWork(master.getId(), work, savedImagePath)) {
                view.showMessage("Работа успешно добавлена!");
                view.close();
            } else {
                view.showMessage("Ошибка при сохранении работы в БД");
            }
        } catch (NumberFormatException e) {
            view.showMessage("Введите корректную цену (число)");
        } catch (Exception e) {
            view.showMessage("Ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean validateInput() {
        if (view.getTitleText().trim().isEmpty()) {
            view.showMessage("Введите название работы");
            return false;
        }

        if (view.getPriceText().trim().isEmpty()) {
            view.showMessage("Введите цену работы");
            return false;
        }

        if (view.getImagePath() == null || view.getImagePath().isEmpty()) {
            view.showMessage("Загрузите изображение работы");
            return false;
        }

        return true;
    }

    private String saveWorkImage(String sourcePath) {
        try {
            Path worksDir = Paths.get("works", String.valueOf(master.getId()));
            FileUtils.createDirectoriesIfNotExists(worksDir);

            String fileName = "work_" + System.currentTimeMillis() + getFileExtension(sourcePath);
            Path destination = worksDir.resolve(fileName);

            FileUtils.copyFile(Paths.get(sourcePath), destination);
            return destination.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getFileExtension(String filePath) {
        int dotIndex = filePath.lastIndexOf('.');
        return (dotIndex == -1) ? "" : filePath.substring(dotIndex);
    }
}
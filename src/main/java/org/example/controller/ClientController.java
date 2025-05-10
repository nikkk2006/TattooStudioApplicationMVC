package org.example.controller;

import org.example.database.AppointmentDao;
import org.example.database.UserDao;
import org.example.database.ScheduleDao;
import org.example.model.ClientModel;
import org.example.model.MasterModel;
import org.example.model.User;
import org.example.view.AppointmentWindow;
import org.example.view.ClientWindow;
import org.example.view.MainWindow;
import org.example.view.MasterSelectionWindow;
import java.util.List;
import java.util.Map;
import java.util.HashMap;


public class ClientController {
    private ClientModel model;
    private ClientWindow view;
    private User currentUser;
    private AppointmentDao appointmentDao;
    private UserDao userDao;
    private ScheduleDao scheduleDao;
    private Map<String, Integer> mastersMap;

    public ClientController(ClientWindow view, User user,
                            AppointmentDao appointmentDao, UserDao userDao, ScheduleDao scheduleDao) {
        this.view = view;
        this.currentUser = user;
        this.model = new ClientModel(user);
        this.appointmentDao = appointmentDao;
        this.userDao = userDao;
        this.scheduleDao = scheduleDao;
        this.mastersMap = new HashMap<>();

        setupListeners();
    }

    @Deprecated
    public ClientController(ClientModel model, ClientWindow view, MainWindow mainWindow) {
        this(view, model.getUser(), new AppointmentDao(), new UserDao(), new ScheduleDao());
    }

    private void setupListeners() {
        view.getChooseMasterButton().addActionListener(e -> chooseMaster());
        view.getMakeAppointmentButton().addActionListener(e -> openAppointmentWindow());
        view.getBackButton().addActionListener(e -> goBack());
    }

    private void openAppointmentWindow() {
        view.close();
        List<MasterModel> masters = MasterModel.getAllMasters();
        for (MasterModel master : masters) {
            master.loadScheduleFromDatabase(); // Эта строка критически важна
        }

        AppointmentWindow appointmentWindow = new AppointmentWindow(masters, currentUser.getId());
        new AppointmentController(appointmentWindow, currentUser, appointmentDao);

        appointmentWindow.setVisible(true);

    }

    private void chooseMaster() {
        view.close();
        MasterSelectionWindow masterSelectionView = new MasterSelectionWindow();
        new MasterSelectionController(masterSelectionView, currentUser);
        masterSelectionView.setVisible(true);
    }

    private void goBack() {
        view.close();
        MainWindow mainWindowView = new MainWindow();
        new MainController(mainWindowView);
        mainWindowView.setVisible(true);
    }
}
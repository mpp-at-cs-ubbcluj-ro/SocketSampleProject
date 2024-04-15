package ro.ubb;

import ro.ubb.controller.SocketController;
import ro.ubb.repository.CarRepository;
import ro.ubb.repository.CarRepositoryDbImpl;
import ro.ubb.repository.DatabaseConnection;
import ro.ubb.service.MainService;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream("app.properties"));

        DatabaseConnection databaseConnection = new DatabaseConnection(properties);
        CarRepository carRepository = new CarRepositoryDbImpl(databaseConnection);
        MainService mainService = new MainService(carRepository);
        SocketController socketController = new SocketController(mainService, properties);
        socketController.listenForClients();
    }
}
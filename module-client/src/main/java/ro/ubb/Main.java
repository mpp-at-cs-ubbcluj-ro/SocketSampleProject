package ro.ubb;

import ro.ubb.dto.CarDto;
import ro.ubb.dto.RequestDto;
import ro.ubb.dto.ResponseDto;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {

    private static String option;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Socket serverSocket = new Socket("127.0.0.1", 9999);
        ObjectOutputStream out = new ObjectOutputStream(serverSocket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(serverSocket.getInputStream());

        createListener(in);

        Scanner scanner = new Scanner(System.in);

        showOptions();
        option = scanner.next();
        while (!"0".equals(option)) {
            if ("1".equals(option)) {
                performSaveAction(out);
            } else if ("2".equals(option)) {
                performFindAllAction(out);
            }
            showOptions();
            option = scanner.next();
        }

        out.close();
        in.close();
        serverSocket.close();
    }

    private static void createListener(ObjectInputStream in) {
        new Thread(() -> {
            try {
                reader(in);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    private static void performFindAllAction(ObjectOutputStream out) throws IOException, ClassNotFoundException {
        out.writeObject(new RequestDto("findAllCars", null));
    }

    private static void performSaveAction(ObjectOutputStream out) throws IOException, ClassNotFoundException {
        int randomValue = (int) (Math.random() * 1000);
        System.out.println("Generated " + randomValue);
        out.writeObject(new RequestDto("saveCar",
                new CarDto(null, "name" + randomValue, "manufacturer" + randomValue,
                        LocalDate.of(2021, 1, 1))));
    }

    private static void reader(ObjectInputStream in) throws IOException, ClassNotFoundException {
        while (!"0".equals(option)) {
            ResponseDto responseDto = (ResponseDto) in.readObject();
            if ("Notify".equals(responseDto.getResponseType())) {
                System.out.println("New car was added!!!");
                System.out.println(responseDto.getCarDtoList().get(0));
            } else if ("findAllCars".equals(responseDto.getResponseType())) {
                responseDto.getCarDtoList().forEach(System.out::println);
            } else if ("saveCar".equals(responseDto.getResponseType())) {
                System.out.println("Success!");
            } else {
                System.out.println("Unrecognised response type.");
            }
        }
    }

    private static void showOptions() {
        System.out.println("0 - Exit");
        System.out.println("1 - Save");
        System.out.println("2 - FindAll");
        System.out.println("Please choose an option!");
    }
}
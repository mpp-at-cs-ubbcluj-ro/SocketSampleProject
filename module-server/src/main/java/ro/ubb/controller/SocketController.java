package ro.ubb.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ro.ubb.dto.CarDto;
import ro.ubb.dto.RequestDto;
import ro.ubb.dto.ResponseDto;
import ro.ubb.service.MainService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class SocketController {

    private static final Logger LOG = LogManager.getLogger(SocketController.class);

    private final MainService mainService;
    private final ServerSocket serverSocket;
    private final Map<Integer, ObjectOutputStream> portToSocketOutputStream = new HashMap<>();

    public SocketController(MainService mainService, Properties properties) {
        this.mainService = mainService;
        serverSocket = createServerSocket(properties);
    }

    private ServerSocket createServerSocket(Properties properties) {
        try {
            String portString = properties.getProperty("server.port");
            return new ServerSocket(Integer.parseInt(portString));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void listenForClients() {
        while (true) {
            Socket clientSocket = acceptClient();
            new Thread(() -> {
                try {
                    processClient(clientSocket);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
    }

    private void processClient(Socket clientSocket) throws IOException {
        ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
        ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
        portToSocketOutputStream.put(clientSocket.getPort(), out);
        try {
            while (true) {
                RequestDto requestDto = readCommandFromClient(in);
                String requestType = requestDto.getRequestType();
                if ("saveCar".equals(requestType)) {
                    mainService.saveCar(requestDto.getCarDto());
                    out.writeObject(new ResponseDto("saveCar", "Success", null));
                    notifyClients(requestDto.getCarDto());
                } else if ("findAllCars".equals(requestType)) {
                    List<CarDto> cars = mainService.findAllCars();
                    out.writeObject(new ResponseDto("findAllCars", "Success", cars));
                } else {
                    LOG.error("processClient - unknown request type  - {}", requestType);
                }
            }
        } catch (Exception exception) {
            LOG.error("processClient - There has been an exception - {}", exception.getMessage());
            portToSocketOutputStream.remove(clientSocket.getPort()).close();
            in.close();
            clientSocket.close();
        }
    }

    private RequestDto readCommandFromClient(ObjectInputStream in) {
        LOG.info("reading");
        try {
            return (RequestDto) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private Socket acceptClient() {
        try {
            return serverSocket.accept();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void notifyClients(CarDto carDto) {
        for (ObjectOutputStream outputStream : portToSocketOutputStream.values()) {
            new Thread(() -> {
                try {
                    notifyClient(outputStream, carDto);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
    }

    private static void notifyClient(ObjectOutputStream outputStream, CarDto carDto) throws IOException {
        outputStream.writeObject(new ResponseDto("Notify", "Success", List.of(carDto)));
    }
}

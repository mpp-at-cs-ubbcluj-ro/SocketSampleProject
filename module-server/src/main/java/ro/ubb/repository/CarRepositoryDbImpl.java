package ro.ubb.repository;

import ro.ubb.model.Car;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CarRepositoryDbImpl implements CarRepository {

    private final DatabaseConnection databaseConnection;

    public CarRepositoryDbImpl(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public Long save(Car entity) throws SQLException {
        Connection connection = databaseConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO " +
                "Cars(name, manufacturer, productionDate) values (?,?,?)");
        preparedStatement.setString(1, entity.getName());
        preparedStatement.setString(2, entity.getManufacturer());
        preparedStatement.setString(3, entity.getProductionDate().toString());
        preparedStatement.executeUpdate();
        return getLastIdForCar(entity);
    }

    private Long getLastIdForCar(Car entity) throws SQLException {
        Connection connection = databaseConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT id FROM Cars " +
                "WHERE name=? AND manufacturer=? AND productionDate=? ORDER BY id DESC");
        preparedStatement.setString(1, entity.getName());
        preparedStatement.setString(2, entity.getManufacturer());
        preparedStatement.setString(3, entity.getProductionDate().toString());
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getLong("id");
        }
        throw new RuntimeException("getLastIdForCar - No entity found with these fields.");
    }

    @Override
    public List<Car> findAll() throws SQLException {
        List<Car> cars = new ArrayList<>();
        Connection connection = databaseConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Cars");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            String manufacturer = resultSet.getString("manufacturer");
            String productionDateString = resultSet.getString("productionDate");
            LocalDate productionDate = LocalDate.parse(productionDateString);
            cars.add(new Car(id, name, manufacturer, productionDate));
        }
        return cars;
    }
}

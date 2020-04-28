import java.sql.*;
import java.util.ArrayList;

public class DBManager {
    private static Connection connection;

    public static void connect() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/airport?useUnicode=true&serverTimezone=UTC",
                "root", ""
        );
    }

    public static User getUserByLoginAndPassword(User user) throws SQLException {
        User resultUser = new User();
        PreparedStatement statement = connection.prepareStatement("" +
                "SELECT usery.id, usery.login, usery.password, roles.role " +
                "FROM usery INNER JOIN roles ON usery.role_id = roles.id " +
                "WHERE login = ? AND password = ?");
        statement.setString(1, user.getLogin());
        statement.setString(2, user.getPassword());
        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next()) {
            Long id = resultSet.getLong("id");
            String login = resultSet.getString("login");
            String password = resultSet.getString("password");
            Role role = Role.valueOf(resultSet.getString("role"));
            resultUser = new User(id, login, password, role);
        }
        return resultUser;
    }

    public static ArrayList getAllAircrafts() throws SQLException {
        ArrayList aicrafts = new ArrayList();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM aircrafts");
        ResultSet resultSet = statement.executeQuery();

        while(resultSet.next()){
            Long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            String model = resultSet.getString("model");
            int businessCapacity = resultSet.getInt("business_class_capacity");
            int economCapacity = resultSet.getInt("econom_class_capacity");
            aicrafts.add(new Aircraft(id, name, model, businessCapacity, economCapacity));
        }
        statement.close();
        return aicrafts;
    }

    public static ArrayList getAllCities() throws SQLException {
        ArrayList list = new ArrayList();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM cities");
        ResultSet resultSet = statement.executeQuery();

        while(resultSet.next()){
            Long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            String country = resultSet.getString("country");
            String shortName = resultSet.getString("short_name");
            list.add(new City(id, name, country, shortName));
        }
        statement.close();
        return list;
    }

    public static ArrayList<Flight> getAllFlights() {
        ArrayList<Flight> flights = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * from flights");
            statement.executeQuery();
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                Long idFromDB = resultSet.getLong("id");
                Long aircraftIdFromDB = resultSet.getLong("aircraft_id");
                Long depCityIdFromDB = resultSet.getLong("departure_city_id");
                Long arrCityIdFromDB = resultSet.getLong("arrival_city_id");
                String dateFromDB = resultSet.getString("departure_time");
                Integer ePPrice = resultSet.getInt("econom_place_price");
                Integer bPPrice = resultSet.getInt("business_place_price");

                PreparedStatement statementForAircraft = connection.prepareStatement("SELECT * from aircrafts where id = " + aircraftIdFromDB);
                statementForAircraft.executeQuery();
                ResultSet aircraftResultSet = statementForAircraft.getResultSet();
                Aircraft aircraftFromDB = null;
                if (aircraftResultSet.next()) {
                    Long aircraftID = aircraftResultSet.getLong("id");
                    String aircraftName = aircraftResultSet.getString("name");
                    String aircraftModel = aircraftResultSet.getString("model");
                    Integer aircraftbCC = aircraftResultSet.getInt("business_class_capacity");
                    Integer aircrafteCC = aircraftResultSet.getInt("econom_class_capacity");
                    aircraftFromDB = new Aircraft(aircraftID, aircraftName, aircraftModel, aircraftbCC, aircrafteCC);
                }

                PreparedStatement statementForDepCity = connection.prepareStatement("SELECT * from cities where id = " + depCityIdFromDB);
                statementForDepCity.executeQuery();
                ResultSet depCityResultSet = statementForDepCity.getResultSet();
                City depCity = null;
                if (depCityResultSet.next()) {
                    Long depCityID = depCityResultSet.getLong("id");
                    String depCityName = depCityResultSet.getString("name");
                    String depCityCountry = depCityResultSet.getString("country");
                    String depCityShortName = depCityResultSet.getString("short_name");
                    depCity = new City(depCityID, depCityName, depCityCountry, depCityShortName);
                }

                PreparedStatement statementForArrCity = connection.prepareStatement("SELECT * from cities where id = " + arrCityIdFromDB);
                statementForArrCity.executeQuery();
                ResultSet arrCityResultSet = statementForArrCity.getResultSet();
                City arrCity = null;
                if (arrCityResultSet.next()) {
                    Long arrCityID = arrCityResultSet.getLong("id");
                    String arrCityName = arrCityResultSet.getString("name");
                    String arrCityCountry = arrCityResultSet.getString("country");
                    String arrCityShortName = arrCityResultSet.getString("short_name");
                    arrCity = new City(arrCityID, arrCityName, arrCityCountry, arrCityShortName);
                }

                flights.add(new Flight(idFromDB, aircraftFromDB, depCity, arrCity, dateFromDB, ePPrice, bPPrice));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flights;
    }

    public static ArrayList getAllTickets() throws SQLException {
        ArrayList list = new ArrayList();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM tickets");
        ResultSet resultSet = statement.executeQuery();

        while(resultSet.next()){
            Long id = resultSet.getLong("id");
            Long flightId = resultSet.getLong("flight_id");
            String name = resultSet.getString("name");
            String surname = resultSet.getString("surname");
            String passportNumber = resultSet.getString("passport_number");
            String ticketType = resultSet.getString("ticket_type");

            PreparedStatement statementFlight = connection.prepareStatement("SELECT * from flights WHERE id = " + flightId);
            statementFlight.executeQuery();
            ResultSet resultSetFlight = statementFlight.getResultSet();
            Flight flight = null;
            if (resultSetFlight.next()) {
                Long idFromDB = resultSetFlight.getLong("id");
                Long aircraftIdFromDB = resultSetFlight.getLong("aircraft_id");
                Long depCityIdFromDB = resultSetFlight.getLong("departure_city_id");
                Long arrCityIdFromDB = resultSetFlight.getLong("arrival_city_id");
                String dateFromDB = resultSetFlight.getString("departure_time");
                Integer ePPrice = resultSetFlight.getInt("econom_place_price");
                Integer bPPrice = resultSetFlight.getInt("business_place_price");

                PreparedStatement statementForAircraft = connection.prepareStatement("SELECT * from aircrafts where id = " + aircraftIdFromDB);
                statementForAircraft.executeQuery();
                ResultSet aircraftResultSet = statementForAircraft.getResultSet();
                Aircraft aircraftFromDB = null;
                if (aircraftResultSet.next()) {
                    Long aircraftID = aircraftResultSet.getLong("id");
                    String aircraftName = aircraftResultSet.getString("name");
                    String aircraftModel = aircraftResultSet.getString("model");
                    Integer aircraftbCC = aircraftResultSet.getInt("business_class_capacity");
                    Integer aircrafteCC = aircraftResultSet.getInt("econom_class_capacity");
                    aircraftFromDB = new Aircraft(aircraftID, aircraftName, aircraftModel, aircraftbCC, aircrafteCC);
                }

                PreparedStatement statementForDepCity = connection.prepareStatement("SELECT * from cities where id = " + depCityIdFromDB);
                statementForDepCity.executeQuery();
                ResultSet depCityResultSet = statementForDepCity.getResultSet();
                City depCity = null;
                if (depCityResultSet.next()) {
                    Long depCityID = depCityResultSet.getLong("id");
                    String depCityName = depCityResultSet.getString("name");
                    String depCityCountry = depCityResultSet.getString("country");
                    String depCityShortName = depCityResultSet.getString("short_name");
                    depCity = new City(depCityID, depCityName, depCityCountry, depCityShortName);
                }

                PreparedStatement statementForArrCity = connection.prepareStatement("SELECT * from cities where id = " + arrCityIdFromDB);
                statementForArrCity.executeQuery();
                ResultSet arrCityResultSet = statementForArrCity.getResultSet();
                City arrCity = null;
                if (arrCityResultSet.next()) {
                    Long arrCityID = arrCityResultSet.getLong("id");
                    String arrCityName = arrCityResultSet.getString("name");
                    String arrCityCountry = arrCityResultSet.getString("country");
                    String arrCityShortName = arrCityResultSet.getString("short_name");
                    arrCity = new City(arrCityID, arrCityName, arrCityCountry, arrCityShortName);
                }
                flight = new Flight(idFromDB, aircraftFromDB, depCity, arrCity, dateFromDB, ePPrice, bPPrice);
            }

            list.add(new Ticket(id, flight, name, surname, passportNumber, ticketType));

        }
        statement.close();
        return list;
    }

    public static boolean addAircraft(Aircraft aircraft) {
        try {
            PreparedStatement statement = connection.prepareStatement("" +
                    "INSERT INTO aircrafts (id, name, model, business_class_capacity," +
                    "econom_class_capacity) " +
                    "VALUES (NULL, ?, ?, ?, ?)"
            );
            statement.setString(1, aircraft.getName());
            statement.setString(2, aircraft.getModel());
            statement.setInt(3, aircraft.getBusinessCapacity());
            statement.setInt(4, aircraft.getEconomCapacity());
            int rows = statement.executeUpdate();
            statement.close();
            return true;
        }catch (SQLException e){
            return false;
        }
    }

    public static boolean addCity(City city) {
        try{
            PreparedStatement statement = connection.prepareStatement("" +
                    "INSERT INTO cities (id, name, country, short_name) " +
                    "VALUES (NULL, ?, ?, ?)"
            );
            statement.setString(1, city.getName() );
            statement.setString(2, city.getCountry());
            statement.setString(3, city.getShortName());
            int rows = statement.executeUpdate();
            statement.close();
        return true;
        }catch (SQLException e){
            return false;
        }
    }

    public static boolean addFlight(Flight flight) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO " +
                    "flights (id, aircraft_id, departure_city_id, arrival_city_id, departure_time , econom_place_price, business_place_price  ) " +
                    "VALUES (null, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setLong(1, flight.getAircraft().getId());
            preparedStatement.setLong(2, flight.getDepartureCity().getId());
            preparedStatement.setLong(3, flight.getArrivalCity().getId());
            preparedStatement.setString(4, flight.getDepartureTime());
            preparedStatement.setInt(5, flight.getEconomPrice());
            preparedStatement.setInt(6, flight.getBusinessPrice());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean addTicket(Ticket ticket) throws SQLException {
        try {
            PreparedStatement statement = connection.prepareStatement("" +
                    "INSERT INTO tickets (id, flight_id, name, surname, passport_number," +
                    "ticket_type) " +
                    "VALUES (NULL, ?, ?, ?, ?, ?)"
            );
            statement.setLong(1, ticket.getFlight().getId());
            statement.setString(2, ticket.getName());
            statement.setString(3, ticket.getSurname());
            statement.setString(4, ticket.getPassportNumber());
            statement.setString(5, ticket.getType());
            int rows = statement.executeUpdate();
            statement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean changeAircraft (Aircraft aircraft) {
        try {
            PreparedStatement statement = connection.prepareStatement("" +
                    "UPDATE aircrafts " +
                    "SET name = ?, model = ?, business_class_capacity = ?, " +
                    "econom_class_capacity = ? " +
                    "WHERE id = ?"
            );
        statement.setString(1, aircraft.getName() );
        statement.setString(2, aircraft.getModel());
        statement.setInt(3, aircraft.getBusinessCapacity());
        statement.setInt(4, aircraft.getEconomCapacity());
        statement.setLong(5, aircraft.getId());
        int rows = statement.executeUpdate();
        statement.close();
        return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public static boolean changeCity (City city)  {
        try {
            PreparedStatement statement = connection.prepareStatement("" +
                    "UPDATE cities " +
                    "SET name = ?, country = ?, short_name =? " +
                    "WHERE id = ?"
            );
            statement.setString(1, city.getName() );
            statement.setString(2, city.getCountry());
            statement.setString(3, city.getShortName());
            statement.setLong(4, city.getId());
            int rows = statement.executeUpdate();
            statement.close();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public static boolean changeFlight (Flight flight) {
        try {
            PreparedStatement statement = connection.prepareStatement("" +
                    "UPDATE flights " +
                    "SET aircraft_id = ?, departure_city_id = ?, arrival_city_id = ?, " +
                    "departure_time = ?, econom_place_price = ?, business_place_price = ? " +
                    "WHERE id = ?"
            );
            statement.setLong(1, flight.getAircraft().getId());
            statement.setLong(2, flight.getDepartureCity().getId());
            statement.setLong(3, flight.getArrivalCity().getId());
            statement.setString(4, flight.getDepartureTime());
            statement.setInt(5, flight.getEconomPrice());
            statement.setInt(6, flight.getBusinessPrice());
            statement.setLong(7, flight.getId());
            int rows = statement.executeUpdate();
            statement.close();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public static boolean changeTicket (Ticket ticket) throws SQLException {
        try {
            PreparedStatement statement = connection.prepareStatement("" +
                    "UPDATE tickets " +
                    "SET flight_id = ?, name = ?, surname =?," +
                    "passport_number = ?, ticket_type = ? " +
                    "WHERE id = ?"
            );
            statement.setLong(1, ticket.getFlight().getId());
            statement.setString(2, ticket.getName());
            statement.setString(3, ticket.getSurname());
            statement.setString(4, ticket.getPassportNumber());
            statement.setString(5, ticket.getType());
            statement.setLong(6, ticket.getId());
            int rows = statement.executeUpdate();
            statement.close();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public static boolean deleteAircraft (Aircraft aircraft) {
        try {
            PreparedStatement statement = connection.prepareStatement("" +
                    "DELETE FROM aircrafts WHERE id =?"
            );
            statement.setLong(1, aircraft.getId());
            int rows = statement.executeUpdate();
            statement.close();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public static boolean deleteCity (City city) throws SQLException {
        try {
            PreparedStatement statement = connection.prepareStatement("" +
                    "DELETE FROM cities WHERE id =?"
            );
            statement.setLong(1, city.getId());
            int rows = statement.executeUpdate();
            statement.close();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public static boolean deleteFlight (Flight flight) {
        try {
            PreparedStatement statement = connection.prepareStatement("" +
                    "DELETE FROM flights WHERE id =?"
            );
            statement.setLong(1, flight.getId());
            int rows = statement.executeUpdate();
            statement.close();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public static boolean deleteTicket (Ticket ticket) {
        try {
            PreparedStatement statement = connection.prepareStatement("" +
                    "DELETE FROM tickets WHERE id =?"
            );
            statement.setLong(1, ticket.getId());
            int rows = statement.executeUpdate();
            statement.close();
            return true;
        } catch (SQLException throwables) {
        throwables.printStackTrace();
        return false;
        }
    }
}

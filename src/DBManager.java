import java.sql.*;
import java.util.ArrayList;

public class DBManager {
    private Connection connection;

    public void connect() throws ClassNotFoundException, SQLException {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/airport?useUnicode=true&serverTimezone=UTC","root", ""
            );
    }

    public User getUserByLogin(User user) throws SQLException {
        User result = new User();
        PreparedStatement statement = connection.prepareStatement("" +
                "SELECT id, login, password, role FROM users WHERE login = ?");
        statement.setString(1, user.getLogin());
        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next()) {
            Long id = resultSet.getLong("id");
            String login = resultSet.getString("login");
            String password = resultSet.getString("password");
            String role = resultSet.getString("role");
            result = new User(id, login, password, role);
        }
        return result;

    }

    public void addAircraft(Aircraft aircraft) throws SQLException {
            PreparedStatement statement = connection.prepareStatement("" +
                    "INSERT INTO aircrafts (id, name, model, business_class_capacity," +
                    "econom_class_capacity) " +
                    "VALUES (NULL, ?, ?, ?, ?)"
            );
            statement.setString(1, aircraft.getName() );
            statement.setString(2, aircraft.getModel());
            statement.setInt(3, aircraft.getBusinessCapacity());
            statement.setInt(4, aircraft.getEconomCapacity());
            int rows = statement.executeUpdate();
            statement.close();
    }

    public void addCity(City city) throws SQLException {
            PreparedStatement statement = connection.prepareStatement("" +
                    "INSERT INTO cities (id, name, country, short_name) " +
                    "VALUES (NULL, ?, ?, ?)"
            );
            statement.setString(1, city.getName() );
            statement.setString(2, city.getCountry());
            statement.setString(3, city.getShortName());
            int rows = statement.executeUpdate();
            statement.close();
    }

    public void addFlight(Flight flight) throws SQLException {
            PreparedStatement statement = connection.prepareStatement("" +
                    "INSERT INTO flights (id, aircraft_id, departure_city_id, " +
                    "arrival_city_id, departure_time, econom_place_price, " +
                    "business_place_price ) " +
                    "VALUES (NULL, ?, ?, ?, ?, ?, ?)"
            );
            statement.setLong(1, flight.getAircraftId());
            statement.setLong(2, flight.getDepartureCityId());
            statement.setLong(3, flight.getArrivalCityId());
            statement.setString(4, flight.getDepartureTime());
            statement.setInt(5, flight.getBusinessPrice());
            statement.setInt(6, flight.getEconomPrice());
            int rows = statement.executeUpdate();
            statement.close();
    }

    public void addTicket(Ticket ticket) throws SQLException {
            PreparedStatement statement = connection.prepareStatement("" +
                    "INSERT INTO tickets (id, flight_id, name, surname, passport_number," +
                    "ticket_type) " +
                    "VALUES (NULL, ?, ?, ?, ?, ?)"
            );
            statement.setLong(1, ticket.getFlightId());
            statement.setString(2, ticket.getName() );
            statement.setString(3, ticket.getSurname());
            statement.setString(4, ticket.getPassportNumber());
            statement.setString(5, ticket.getType());
            int rows = statement.executeUpdate();
            statement.close();
    }

    public void changeAircraft (Aircraft aircraft) throws SQLException {
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
    }

    public void changeCity (City city) throws SQLException {
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
    }

    public void changeFlight (Flight flight) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("" +
                "UPDATE flights " +
                "SET aircraft_id = ?, departure_city_id = ?, arrival_city_id = ?" +
                "departure_time = ?, econom_place_price = ?, business_place_price = ? " +
                "WHERE id = ?"
        );
        statement.setLong(1, flight.getAircraftId());
        statement.setLong(2, flight.getDepartureCityId());
        statement.setLong(3, flight.getArrivalCityId());
        statement.setString(4, flight.getDepartureTime());
        statement.setInt(5, flight.getBusinessPrice());
        statement.setInt(6, flight.getEconomPrice());
        statement.setLong(7, flight.getId());
        int rows = statement.executeUpdate();
        statement.close();
    }

    public void changeTicket (Ticket ticket) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("" +
                "UPDATE tickets " +
                "SET flight_id = ?, name = ?, surname =?," +
                "passport_number = ?, ticket_type = ? " +
                "WHERE id = ?"
        );
        statement.setLong(1, ticket.getFlightId());
        statement.setString(2, ticket.getName() );
        statement.setString(3, ticket.getSurname());
        statement.setString(4, ticket.getPassportNumber());
        statement.setString(5, ticket.getType());
        statement.setLong(6, ticket.getId());
        int rows = statement.executeUpdate();
        statement.close();
    }

    public void deleteAircraft (Aircraft aircraft) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("" +
                "DELETE FROM aircrafts WHERE id =?"
        );
        statement.setLong(1, aircraft.getId());
        int rows = statement.executeUpdate();
        statement.close();
    }

    public void deleteCity (City city) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("" +
                "DELETE FROM cities WHERE id =?"
        );
        statement.setLong(1, city.getId());
        int rows = statement.executeUpdate();
        statement.close();
    }

    public void deleteFlight (Flight flight) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("" +
                "DELETE FROM flights WHERE id =?"
        );
        statement.setLong(1, flight.getId());
        int rows = statement.executeUpdate();
        statement.close();
    }

    public void deleteTicket (Ticket ticket) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("" +
                "DELETE FROM tickets WHERE id =?"
        );
        statement.setLong(1, ticket.getId());
        int rows = statement.executeUpdate();
        statement.close();
    }

    public ArrayList getAllAircrafts() throws SQLException {
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

    public ArrayList getAllCities() throws SQLException {
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

    public ArrayList getAllFlights() throws SQLException {
        ArrayList list = new ArrayList();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM flights");
        ResultSet resultSet = statement.executeQuery();

        while(resultSet.next()){
            Long id = resultSet.getLong("id");
            Long aircraftId = resultSet.getLong("aircraft_id");
            Long departureId = resultSet.getLong("departure_city_id");
            Long arrivalId = resultSet.getLong("arrival_city_id");
            String departureTime = resultSet.getString("departure_time");
            int economPrice = resultSet.getInt("econom_place_price");
            int businessPrice = resultSet.getInt("business_place_price");
            list.add(new Flight(id, aircraftId, departureId, arrivalId, departureTime, economPrice, businessPrice));
        }
        statement.close();
        return list;
    }

    public ArrayList getAllTickets() throws SQLException {
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
            list.add(new Ticket(id, flightId, name, surname, passportNumber, ticketType));
        }
        statement.close();
        return list;
    }
}
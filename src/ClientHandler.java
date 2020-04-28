import java.io.IOException;
import java.io.*;
import java.net.*;
import java.sql.SQLException;

public class ClientHandler extends Thread {
    private Socket socket;

    public ClientHandler (Socket socket){
        this.socket = socket;
    }

    public void run(){
        try(ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())) {
            PackageData packageData;

            while ((packageData = (PackageData)inputStream.readObject())!=null){
                outStream.reset();
                if(packageData.getOperation().equals(Operation.AUTHORIZATION)){
                    User userFromDB = DBManager.getUserByLoginAndPassword(packageData.getUser());
                    PackageData pdWithUser = new PackageData();
                    pdWithUser.setUser(userFromDB);
                    outStream.writeObject(pdWithUser);

                }else if (packageData.getOperation().equals(Operation.LIST_AIRCRAFTS)){
                    listFromDBToClient(Operation.LIST_AIRCRAFTS, outStream);

                }else if (packageData.getOperation().equals(Operation.LIST_CITIES)) {
                    listFromDBToClient(Operation.LIST_CITIES, outStream);

                }else if (packageData.getOperation().equals(Operation.LIST_FLIGHTS)) {
                    listFromDBToClient(Operation.LIST_FLIGHTS, outStream);

                }else if (packageData.getOperation().equals(Operation.LIST_TICKETS)) {
                    listFromDBToClient(Operation.LIST_TICKETS, outStream);

                }else if (packageData.getOperation().equals(Operation.ADD_AIRCRAFT)){
                    responseFromDB(DBManager.addAircraft(packageData.getAircraft()), outStream);

                }else if (packageData.getOperation().equals(Operation.CHANGE_AIRCRAFT)){
                    responseFromDB(DBManager.changeAircraft(packageData.getAircraft()), outStream);

                }else if (packageData.getOperation().equals(Operation.DELETE_AIRCRAFT)){
                    responseFromDB(DBManager.deleteAircraft(packageData.getAircraft()), outStream);

                }else if (packageData.getOperation().equals(Operation.ADD_CITY)){
                    responseFromDB(DBManager.addCity(packageData.getCity()), outStream);

                }else if (packageData.getOperation().equals(Operation.CHANGE_CITY)){
                    responseFromDB(DBManager.changeCity(packageData.getCity()), outStream);

                }else if (packageData.getOperation().equals(Operation.DELETE_CITY)){
                    responseFromDB(DBManager.deleteCity(packageData.getCity()), outStream);

                }else if (packageData.getOperation().equals(Operation.ADD_FLIGHT)){
                    responseFromDB(DBManager.addFlight(packageData.getFlight()), outStream);

                }else if (packageData.getOperation().equals(Operation.CHANGE_FLIGHT)){
                    responseFromDB(DBManager.changeFlight(packageData.getFlight()), outStream);

                }else if (packageData.getOperation().equals(Operation.DELETE_FLIGHT)){
                    responseFromDB(DBManager.deleteFlight(packageData.getFlight()), outStream);

                }else if (packageData.getOperation().equals(Operation.ADD_TICKET)){
                    responseFromDB(DBManager.addTicket(packageData.getTicket()), outStream);

                }else if (packageData.getOperation().equals(Operation.CHANGE_TICKET)){
                    responseFromDB(DBManager.changeTicket(packageData.getTicket()), outStream);

                }else if (packageData.getOperation().equals(Operation.DELETE_TICKET)){
                    responseFromDB(DBManager.deleteTicket(packageData.getTicket()), outStream);
                }

            }
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void responseFromDB(boolean bool, ObjectOutputStream out) throws IOException {
        if(bool){
            PackageData response = new PackageData(Operation.OK);
            out.writeObject(response);
        }else out.writeObject(new PackageData());
    }

    public static void listFromDBToClient(Operation operation, ObjectOutputStream out) throws IOException, SQLException {
        PackageData listPD = new PackageData();
        if(operation.equals(Operation.LIST_AIRCRAFTS))
            listPD.setAllAtList(DBManager.getAllAircrafts());
        else if(operation.equals(Operation.LIST_CITIES))
            listPD.setAllAtList(DBManager.getAllCities());
        else if(operation.equals(Operation.LIST_FLIGHTS))
            listPD.setAllAtList(DBManager.getAllFlights());
        else if(operation.equals(Operation.LIST_TICKETS))
            listPD.setAllAtList(DBManager.getAllTickets());
        out.writeObject(listPD);
    }
}

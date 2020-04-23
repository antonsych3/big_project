import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;

public class ClientHandler extends Thread {
    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            DBManager manager = new DBManager();
            manager.connect();
            ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            PackageData pd, response = new PackageData();
            User user;

            while ((pd = (PackageData)inputStream.readObject()) != null) {
                outStream.reset();
                response.setOperationType("false");
                user = manager.getUserByLogin(pd.getUser());

                if(pd.getUser().getPasswod().equals(user.getPasswod())){
                    pd.setUser(user);
                    outStream.writeObject(pd);

                    while ((pd = (PackageData)inputStream.readObject()) != null) {
                        outStream.reset();
                        if (pd.getDataType().equals("Aircraft")){
                            if(pd.getOperationType().equals("add")){
                                manager.addAircraft(pd.getAircraft());
                            }else if (pd.getOperationType().equals("change")){
                                manager.changeAircraft(pd.getAircraft());
                            }else if(pd.getOperationType().equals("delete")){
                                manager.deleteAircraft(pd.getAircraft());
                            }else if(pd.getOperationType().equals("getAll")){
                                pd.setElemOfAirport(manager.getAllAircrafts());
                                outStream.writeObject(pd);
                            }

                        } else if (pd.getDataType().equals("City")){
                            if(pd.getOperationType().equals("add")){
                                manager.addCity(pd.getCity());
                                response.setOperationType("added");
                                outStream.writeObject(response);
                            }else if (pd.getOperationType().equals("change")){
                                manager.changeCity(pd.getCity());
                            }else if(pd.getOperationType().equals("delete")){
                                manager.deleteCity(pd.getCity());
                                response.setOperationType("deleted " + pd.getCity().getId());
                                outStream.writeObject(response);
                            }else if(pd.getOperationType().equals("getAll")){
                                pd.setElemOfAirport(manager.getAllCities());
                                outStream.writeObject(pd);
                            }

                        }else if (pd.getDataType().equals("Flight")){
                            if(pd.getOperationType().equals("add")){
                                manager.addFlight(pd.getFlight());
                            }else if (pd.getOperationType().equals("change")){
                                manager.changeFlight(pd.getFlight());
                            }else if(pd.getOperationType().equals("delete")){
                                manager.deleteFlight(pd.getFlight());
                            }else if(pd.getOperationType().equals("getAll")){
                                pd.setElemOfAirport(manager.getAllFlights());
                                outStream.writeObject(pd);
                            }

                        }else if (pd.getDataType().equals("Ticket")){
                            if(pd.getOperationType().equals("add")){
                                manager.addTicket(pd.getTicket());
                            }else if (pd.getOperationType().equals("change")){
                                manager.changeTicket(pd.getTicket());
                            }else if(pd.getOperationType().equals("delete")){
                                manager.deleteTicket(pd.getTicket());
                            }else if(pd.getOperationType().equals("getAll")){
                                pd.setElemOfAirport(manager.getAllTickets());
                                outStream.writeObject(pd);
                            }
                        }
                    }

                }else{
                    user.setRole("wrong");
                    pd.setUser(user);
                    outStream.writeObject(pd);
                }
            }
            inputStream.close();
            outStream.close();
            socket.close();
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}

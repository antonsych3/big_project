import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class MainFrame extends JFrame {
    private static AuthorizationFrame authorization;
    private static AdminFrame adminFrame;
    private static MenuFrame menuFrame, menuTickets;
    private static ListFrame listFrame;
    private static AddChangeDeleteAircraft addAircraft, changeAircraft, deleteAircraft;
    private static AddChangeDeleteCity addCity, changeCity, deleteCity;
    private static AddChngDeleteFlight addFlight, changeFlight, deleteFlight;
    private static AddChngDeleteTicket addTicket, changeTicket, deleteTicket;
    private static ArrayList<Container> containers = new ArrayList<>();
    private static TypeData typeData;

    public MainFrame() throws IOException, ClassNotFoundException {

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);
        setTitle("Airport");
        setSize(600, 600);

        authorization = new AuthorizationFrame();

        adminFrame = new AdminFrame();

        menuFrame = new MenuFrame("");
        menuTickets = new MenuFrame("TICKET");

        addAircraft = new AddChangeDeleteAircraft(Operation.ADD_AIRCRAFT);
        changeAircraft = new AddChangeDeleteAircraft(Operation.CHANGE_AIRCRAFT);
        deleteAircraft = new AddChangeDeleteAircraft(Operation.DELETE_AIRCRAFT);

        addCity = new AddChangeDeleteCity(Operation.ADD_CITY);
        changeCity = new AddChangeDeleteCity(Operation.CHANGE_CITY);
        deleteCity = new AddChangeDeleteCity(Operation.DELETE_CITY);

        addFlight = new AddChngDeleteFlight(Operation.ADD_FLIGHT);
        changeFlight = new AddChngDeleteFlight(Operation.CHANGE_FLIGHT);
        deleteFlight = new AddChngDeleteFlight(Operation.DELETE_FLIGHT);

        addTicket = new AddChngDeleteTicket(Operation.ADD_TICKET);
        changeTicket = new AddChngDeleteTicket(Operation.CHANGE_TICKET);
        deleteTicket = new AddChngDeleteTicket(Operation.DELETE_TICKET);

        listFrame = new ListFrame();

        containers.add(authorization);
        containers.add(adminFrame);
        containers.add(menuFrame);
        containers.add(menuTickets);
        containers.add(addAircraft);
        containers.add(changeAircraft);
        containers.add(deleteAircraft);
        containers.add(addCity);
        containers.add(changeCity);
        containers.add(deleteCity);
        containers.add(addFlight);
        containers.add(changeFlight);
        containers.add(deleteFlight);
        containers.add(addTicket);
        containers.add(changeTicket);
        containers.add(deleteTicket);
        containers.add(listFrame);

        for (Container container: containers  ) {
            add(container);
        }

        showWindow(authorization);
    }

    public static void showWindow(Container window){
        for (Container c: containers) {
            c.setVisible(false);
        }
        window.setVisible(true);
    }

    public static ArrayList listFromDB(Operation operation) throws IOException, ClassNotFoundException {
        PackageData responseFromDB = Client.queryWithResponse(new PackageData(), operation);
        return responseFromDB.getAllAtList();
    }

    public static void writeListToCombo(ArrayList arrayList, JComboBox comboBox, TypeData typeData){
        comboBox.removeAllItems();
        for (Object o:arrayList) {
            if (typeData.equals(TypeData.CITY)){
                comboBox.addItem(((City)o).getId() + " - " + ((City)o).getName());
            }else {
                comboBox.addItem(o);
            }
        }
    }

    public static void writeToListFrame() throws IOException, ClassNotFoundException {
        MainFrame.getListFrame().getListTA().setText("");
        if (MainFrame.getTypeData().equals(TypeData.AIRCRAFT)) {
            cycleToWriteToListFrame(MainFrame.listFromDB(Operation.LIST_AIRCRAFTS));
        }else if (MainFrame.getTypeData().equals(TypeData.CITY)) {
            cycleToWriteToListFrame(MainFrame.listFromDB(Operation.LIST_CITIES));
        }else if (MainFrame.getTypeData().equals(TypeData.FLIGHT)) {
            cycleToWriteToListFrame(MainFrame.listFromDB(Operation.LIST_FLIGHTS));
        }else if (MainFrame.getTypeData().equals(TypeData.TICKET)) {
            cycleToWriteToListFrame(MainFrame.listFromDB(Operation.LIST_TICKETS));
        }
    }

    private static void cycleToWriteToListFrame(ArrayList arrayList){
        for (Object o : arrayList) {
            MainFrame.getListFrame().getListTA().append(o.toString()+"\n");
        }
    }

    public static void lastFlightsInfoForAdd() throws IOException, ClassNotFoundException {
        MainFrame.writeListToCombo(MainFrame.listFromDB(Operation.LIST_AIRCRAFTS), MainFrame.getAddFlight().getAircraftCB(), TypeData.AIRCRAFT);
        MainFrame.writeListToCombo(MainFrame.listFromDB(Operation.LIST_CITIES), MainFrame.getAddFlight().getDepartureCityCB(), TypeData.CITY);
        MainFrame.writeListToCombo(MainFrame.listFromDB(Operation.LIST_CITIES), MainFrame.getAddFlight().getArrivalCityCB(), TypeData.CITY);
    }

    public static void lastFlightsInfoForChange() throws IOException, ClassNotFoundException {
        MainFrame.writeListToCombo(MainFrame.listFromDB(Operation.LIST_AIRCRAFTS), MainFrame.getChangeFlight().getAircraftCB(), TypeData.AIRCRAFT);
        MainFrame.writeListToCombo(MainFrame.listFromDB(Operation.LIST_CITIES), MainFrame.getChangeFlight().getDepartureCityCB(), TypeData.CITY);
        MainFrame.writeListToCombo(MainFrame.listFromDB(Operation.LIST_CITIES), MainFrame.getChangeFlight().getArrivalCityCB(), TypeData.CITY);
        MainFrame.writeListToCombo(MainFrame.listFromDB(Operation.LIST_FLIGHTS), MainFrame.getChangeFlight().getFlightsCombo(), TypeData.FLIGHT);
    }

    public static void lastFlightsInfoForDelete() throws IOException, ClassNotFoundException {
        MainFrame.writeListToCombo(MainFrame.listFromDB(Operation.LIST_FLIGHTS), MainFrame.getDeleteFlight().getFlightsCombo(), TypeData.FLIGHT);
    }

    public static void lastTicketsInfoForAdd() throws IOException, ClassNotFoundException {
        MainFrame.writeListToCombo(MainFrame.listFromDB(Operation.LIST_FLIGHTS), MainFrame.getAddTicket().getFlightIdCB(), TypeData.FLIGHT);
    }

    public static void lastTicketsInfoForChange() throws IOException, ClassNotFoundException {
        MainFrame.writeListToCombo(MainFrame.listFromDB(Operation.LIST_FLIGHTS), MainFrame.getChangeTicket().getFlightIdCB(), TypeData.FLIGHT);
        MainFrame.writeListToCombo(MainFrame.listFromDB(Operation.LIST_TICKETS), MainFrame.getChangeTicket().getTicketsCombo(), TypeData.TICKET);


    }

    public static void lastTicketsInfoForDelete() throws IOException, ClassNotFoundException {
        MainFrame.writeListToCombo(MainFrame.listFromDB(Operation.LIST_TICKETS), MainFrame.getDeleteTicket().getTicketsCombo(), TypeData.TICKET);
    }

    public static AuthorizationFrame getAuthorization() {
        return authorization;
    }

    public static AdminFrame getAdminFrame() {
        return adminFrame;
    }

    public static MenuFrame getMenuFrame() {
        return menuFrame;
    }

    public static MenuFrame getMenuTickets() {
        return menuTickets;
    }

    public static AddChangeDeleteAircraft getAddAircraft() {
        return addAircraft;
    }

    public static AddChangeDeleteAircraft getChangeAircraft() {
        return changeAircraft;
    }

    public static AddChangeDeleteAircraft getDeleteAircraft() {
        return deleteAircraft;
    }

    public static ListFrame getListFrame() {
        return listFrame;
    }

    public static AddChangeDeleteCity getAddCity() {
        return addCity;
    }

    public static AddChangeDeleteCity getChangeCity() {
        return changeCity;
    }

    public static AddChangeDeleteCity getDeleteCity() {
        return deleteCity;
    }

    public static AddChngDeleteFlight getAddFlight() {
        return addFlight;
    }

    public static AddChngDeleteFlight getChangeFlight() {
        return changeFlight;
    }

    public static AddChngDeleteFlight getDeleteFlight() {
        return deleteFlight;
    }

    public static AddChngDeleteTicket getAddTicket() {
        return addTicket;
    }

    public static AddChngDeleteTicket getChangeTicket() {
        return changeTicket;
    }

    public static AddChngDeleteTicket getDeleteTicket() {
        return deleteTicket;
    }

    public static TypeData getTypeData() {
        return typeData;
    }

    public static void setTypeData(TypeData typeData) {
        MainFrame.typeData = typeData;
    }
}

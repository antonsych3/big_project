import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Scanner sc = new Scanner(System.in);
        Socket socket = new Socket("127.0.0.1", 1111);
        ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());
        PackageData pd = new PackageData();

        while(true){
            outStream.reset();
            System.out.println("1 - SIGN IN\n" +
                    "2 - EXIT");
            int signOrExit = sc.nextInt();
            if(signOrExit ==1) {
                System.out.println("Login:");
                String login = sc.next();
                System.out.println("Password:");
                String password = sc.next();
                pd.setUser(new User(login, password));
                outStream.writeObject(pd);
                pd = (PackageData) inStream.readObject();

                if (pd.getUser().getRole().equals("admin")) {
                    while (true){
                        outStream.reset();
                        System.out.println("1 - CITIES\n" +
                                "2 - AIRCRAFTS\n" +
                                "3 - FLIGHTS\n" +
                                "0 - BACK");
                        int choice = sc.nextInt();

                        if(choice == 1){
                            pd.setDataType("City");
                            System.out.println("1 - ADD\n" +
                                    "2 - CHANGE\n" +
                                    "3 - DELETE\n" +
                                    "4 - VIEW ALL");
                            int choiceChange = sc.nextInt();
                            if (choiceChange == 1){
                                System.out.println("Insert name:");
                                String name = sc.next();
                                System.out.println("Insert country:");
                                String country = sc.next();
                                System.out.println("Insert short name:");
                                String shortName = sc.next();
                                City city = new City(null, name, country, shortName);
                                pd.setOperationType("add");
                                pd.setCity(city);
                                outStream.writeObject(pd);
                                pd = (PackageData) inStream.readObject();
                                System.out.println(pd.getOperationType());

                            }else if(choiceChange == 2) {
                                System.out.println("Insert id of city to change:");
                                Long id = sc.nextLong();
                                System.out.println("Insert new name:");
                                String name = sc.next();
                                System.out.println("Insert new country:");
                                String country = sc.next();
                                System.out.println("Insert new short name:");
                                String shortName = sc.next();
                                City city = new City(id, name, country, shortName);
                                pd.setOperationType("change");
                                pd.setCity(city);
                                outStream.writeObject(pd);

                            }else if(choiceChange == 3){
                                System.out.println("Insert id of city to delete:");
                                Long id = sc.nextLong();
                                pd.setOperationType("delete");
                                pd.setCity(new City(id));
                                outStream.writeObject(pd);
                                pd = (PackageData) inStream.readObject();
                                System.out.println(pd.getOperationType());

                            }else if(choiceChange == 4){
                                pd.setOperationType("getAll");
                                outStream.writeObject(pd);
                                pd = (PackageData) inStream.readObject();
                                printArrayList(pd.getElemOfAirport());
                                System.out.println();

                            }else System.out.println("Unknown command!\n");

                        }else if(choice ==2){
                            pd.setDataType("Aircraft");
                            System.out.println("1 - ADD\n" +
                                    "2 - CHANGE\n" +
                                    "3 - DELETE\n" +
                                    "4 - VIEW ALL");
                            int choiceChange = sc.nextInt();
                            if (choiceChange == 1){
                                System.out.println("Insert name:");
                                String name = sc.next();
                                System.out.println("Insert model:");
                                String model = sc.next();
                                System.out.println("Insert business class capacity:");
                                int businessCapacity = sc.nextInt();
                                System.out.println("Insert econom class capacity:");
                                int economCapacity = sc.nextInt();
                                Aircraft aircraft = new Aircraft(null, name, model,
                                        businessCapacity, economCapacity);
                                pd.setOperationType("add");
                                pd.setAircraft(aircraft);
                                outStream.writeObject(pd);

                            }else if(choiceChange == 2){
                                System.out.println("Insert id for change:");
                                Long id = sc.nextLong();
                                System.out.println("Insert name:");
                                String name = sc.next();
                                System.out.println("Insert model:");
                                String model = sc.next();
                                System.out.println("Insert business class capacity:");
                                int businessCapacity = sc.nextInt();
                                System.out.println("Insert econom class capacity:");
                                int economCapacity = sc.nextInt();
                                Aircraft aircraft = new Aircraft(id, name, model,
                                        businessCapacity, economCapacity);
                                pd.setOperationType("change");
                                pd.setAircraft(aircraft);
                                outStream.writeObject(pd);

                            }else if(choiceChange == 3){
                                System.out.println("Insert id to delete:");
                                Long id = sc.nextLong();
                                pd.setOperationType("delete");
                                pd.setAircraft(new Aircraft(id));
                                outStream.writeObject(pd);

                            }else if(choiceChange == 4){
                                pd.setOperationType("getAll");
                                outStream.writeObject(pd);
                                pd = (PackageData) inStream.readObject();
                                printArrayList(pd.getElemOfAirport());
                                System.out.println();

                            }else System.out.println("Unknown command!\n");

                        }else if (choice == 3){
                            pd.setDataType("Flight");
                            System.out.println("1 - ADD\n" +
                                    "2 - CHANGE\n" +
                                    "3 - DELETE\n" +
                                    "4 - VIEW ALL");
                            int choiceChange = sc.nextInt();
                            if (choiceChange == 1){
                                System.out.println("Insert aircraft id:");
                                Long aircraftId = sc.nextLong();
                                System.out.println("Insert departure city id:");
                                Long departureId = sc.nextLong();
                                System.out.println("Insert arrival city id:");
                                Long arrivalId = sc.nextLong();
                                System.out.println("Insert departure time:");
                                String departureTime = sc.next();
                                System.out.println("Insert econom price:");
                                int economPrice = sc.nextInt();
                                System.out.println("Insert business price:");
                                int businessPrice = sc.nextInt();
                                if(departureId == arrivalId){
                                    System.out.println("Arrival and departure are same. Mistake!\n");
                                } else {
                                    Flight flight = new Flight(null, aircraftId, departureId,
                                            arrivalId, departureTime, economPrice, businessPrice);
                                    pd.setOperationType("add");
                                    pd.setFlight(flight);
                                    outStream.writeObject(pd);
                                }

                            } else if (choiceChange ==2){
                                System.out.println("Insert if of flight to change:");
                                Long id = sc.nextLong();
                                System.out.println("Insert aircraft id:");
                                Long aircraftId = sc.nextLong();
                                System.out.println("Insert departure city id:");
                                Long departureId = sc.nextLong();
                                System.out.println("Insert arrival city id:");
                                Long arrivalId = sc.nextLong();
                                System.out.println("Insert departure time:");
                                String departureTime = sc.next();
                                System.out.println("Insert econom price:");
                                int economPrice = sc.nextInt();
                                System.out.println("Insert business price:");
                                int businessPrice = sc.nextInt();
                                if(departureId == arrivalId){
                                    System.out.println("Arrival and departure are same. Mistake!\n");
                                } else {
                                    Flight flight = new Flight(id, aircraftId, departureId,
                                            arrivalId, departureTime, economPrice, businessPrice);
                                    pd.setOperationType("change");
                                    pd.setFlight(flight);
                                    outStream.writeObject(pd);
                                }

                            } else if (choiceChange ==3) {
                                System.out.println("Insert id of flight to delete:");
                                Long id = sc.nextLong();
                                pd.setOperationType("delete");
                                pd.setFlight(new Flight(id));
                                outStream.writeObject(pd);

                            }else if(choiceChange == 4){
                                pd.setOperationType("getAll");
                                outStream.writeObject(pd);
                                pd = (PackageData) inStream.readObject();
                                printArrayList(pd.getElemOfAirport());
                                System.out.println();

                            }else System.out.println("Unknown command!\n");

                        }else if(choice == 0){
                            outStream.writeObject(null);
                            break;
                        }
                        else System.out.println("Unknown command!\n");
                    }
                } else if (pd.getUser().getRole().equals("cashier")) {
                    pd.setDataType("Ticket");

                    while (true){
                        outStream.reset();
                        System.out.println("1 - ADD TICKET\n" +
                                "2 - CHANGE TICKET\n" +
                                "3 - DELETE TICKET\n" +
                                "4 - VIEW ALL\n" +
                                "0 - BACK");
                        int choice = sc.nextInt();
                        if (choice == 1){
                            System.out.println("Insert flight id:");
                            Long flightId = sc.nextLong();
                            System.out.println("Insert name:");
                            String name = sc.next();
                            System.out.println("Insert surname:");
                            String surname = sc.next();
                            System.out.println("Insert passport number:");
                            String passport = sc.next();
                            System.out.println("Insert ticket type (ec or bc):");
                            String ticketType = sc.next();
                            Ticket ticket = new Ticket(null, flightId, name, surname,
                                    passport, ticketType);
                            pd.setTicket(ticket);
                            pd.setOperationType("add");
                            outStream.writeObject(pd);

                        }else if (choice ==2){
                            System.out.println("Insert id of ticket to change:");
                            Long id = sc.nextLong();
                            System.out.println("Insert flight id:");
                            Long flightId = sc.nextLong();
                            System.out.println("Insert name:");
                            String name = sc.next();
                            System.out.println("Insert surname:");
                            String surname = sc.next();
                            System.out.println("Insert passport number:");
                            String passport = sc.next();
                            System.out.println("Insert ticket type(ec or bc):");
                            String ticketType = sc.next();
                            Ticket ticket = new Ticket(id, flightId, name, surname,
                                    passport, ticketType);
                            pd.setOperationType("change");
                            pd.setTicket(ticket);
                            outStream.writeObject(pd);

                        }else if (choice == 3) {
                            System.out.println("Insert id of ticket to delete:");
                            Long id = sc.nextLong();
                            pd.setOperationType("delete");
                            pd.setTicket(new Ticket(id));
                            outStream.writeObject(pd);

                        }else if(choice == 4){
                            pd.setOperationType("getAll");
                            outStream.writeObject(pd);
                            pd = (PackageData) inStream.readObject();
                            printArrayList(pd.getElemOfAirport());
                            System.out.println();

                        }else  if (choice == 0) {
                            outStream.writeObject(null);
                            break;
                        }
                        else System.out.println("Unknown command!\n");
                    }
                } else if (pd.getUser().getRole().equals("wrong")) {
                    System.out.println("Wrong login or password!\n" +
                            "Try again!\n");
                }
            }else if(signOrExit == 2){
                outStream.writeObject(null);
                outStream.close();
                inStream.close();
                socket.close();
                break;
            }else System.out.println("Unknown command!\n");
        }
    }

    public static void printArrayList(ArrayList list) {
        for (Object o:list) {
            System.out.println(o);
        }
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class AddChngDeleteFlight extends Container {
    private JLabel aircrafrLbl, departureCityLbl, arrivalCityLbl, depatureTimeLbl, economPriceLbl, businessPriceLbl, listLabel, errorLbl;
    private JTextField departureTimeTF, economPriceTF, businessPriceTF;
    private JComboBox aircraftCB, departureCityCB, arrivalCityCB, flightsCombo = new JComboBox();
    private JButton confirmBtn, backBtn;

    public AddChngDeleteFlight(Operation operation) {
        setLayout(null);
        setSize(600, 600);

        backBtn = new JButton("BACK");
        backBtn.setBounds(350, 370, 100, 40 );
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame.showWindow(MainFrame.getMenuFrame());
            }
        });
        add(backBtn);

        if (operation.equals(Operation.ADD_FLIGHT) || operation.equals(Operation.CHANGE_FLIGHT)) {
            aircrafrLbl = new JLabel("AIRCRAFT:");
            aircrafrLbl.setBounds(150, 100, 150, 30);
            add(aircrafrLbl);

            departureCityLbl = new JLabel("DEPARTURE CITY:");
            departureCityLbl.setBounds(150, 140, 150, 30);
            add(departureCityLbl);

            arrivalCityLbl = new JLabel("ARRIVAL CITY:");
            arrivalCityLbl.setBounds(150, 180, 150, 30);
            add(arrivalCityLbl);

            depatureTimeLbl = new JLabel("DEPARTURE TIME:");
            depatureTimeLbl.setBounds(150, 220, 150, 30);
            add(depatureTimeLbl);

            economPriceLbl = new JLabel("ECONOM PRICE:");
            economPriceLbl.setBounds(150, 260, 150, 30);
            add(economPriceLbl);

            businessPriceLbl = new JLabel("BUSINESS PRICE:");
            businessPriceLbl.setBounds(150, 300, 150, 30);
            add(businessPriceLbl);

            errorLbl = new JLabel("");
            errorLbl.setBounds(200, 440, 150, 30);
            add(errorLbl);

            aircraftCB = new JComboBox();
            aircraftCB.setBounds(300, 100, 150, 30);
            add(aircraftCB);

            departureCityCB = new JComboBox();
            departureCityCB.setBounds(300, 140, 150, 30);
            add(departureCityCB);

            arrivalCityCB = new JComboBox();
            arrivalCityCB.setBounds(300, 180, 150, 30);
            add(arrivalCityCB);

            departureTimeTF = new JTextField();
            departureTimeTF.setBounds(300, 220, 150, 30 );
            add(departureTimeTF);

            economPriceTF = new JTextField();
            economPriceTF.setBounds(300, 260, 150, 30 );
            add(economPriceTF);

            businessPriceTF = new JTextField();
            businessPriceTF.setBounds(300, 300, 150, 30);
            add(businessPriceTF);
        }

        if (operation.equals(Operation.CHANGE_FLIGHT) || operation.equals(Operation.DELETE_FLIGHT)){
            listLabel = new JLabel("CHOOSE FLIGHT");
            add(listLabel);
            add(flightsCombo);

            if (operation.equals(Operation.DELETE_FLIGHT)){
                listLabel.setBounds(200, 120, 200, 30);
                flightsCombo.setBounds(200, 150, 200, 30);
                confirmBtn = new JButton("DELETE");
                confirmBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            String selectedItem = String.valueOf(flightsCombo.getSelectedItem());
                            Long id  = Long.parseLong(selectedItem.substring(0, selectedItem.indexOf('-')).trim());
                            deleteFlight(new Flight(id));
                        } catch (IOException | ClassNotFoundException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                });
            } else {
                listLabel.setBounds(200, 20, 200, 30);
                flightsCombo.setBounds(200, 50, 200, 30);
                confirmBtn = new JButton("CHANGE");
                confirmBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(departureCityCB.getSelectedItem().equals(arrivalCityCB.getSelectedItem())){
                            errorLbl.setText("ERROR CITIES!:");
                        }else {
                            getInfoAndSend(Operation.CHANGE_FLIGHT);
                        }
                    }
                });
            }
        }else if (operation.equals(Operation.ADD_FLIGHT)){
            confirmBtn = new JButton("ADD");
            confirmBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(departureCityCB.getSelectedItem().equals(arrivalCityCB.getSelectedItem())){
                        errorLbl.setText("ERROR CITIES!:");
                    }else {
                        getInfoAndSend(Operation.ADD_FLIGHT);
                    }
                }
            });
        }
        confirmBtn.setBounds(150, 370, 100, 40 );
        add(confirmBtn);
    }

    public void addFlight(Flight flight) throws IOException, ClassNotFoundException {
        PackageData addPd = new PackageData();
        addPd.setFlight(flight);
        if (Client.queryWithResponse(addPd, Operation.ADD_FLIGHT).getOperation().equals(Operation.OK)){
            clearForms();
        }
    }

    public void getInfoAndSend(Operation operation) {
        try {
            String selectedItem = String.valueOf(aircraftCB.getSelectedItem());
            Long idForAircraft = Long.parseLong(selectedItem.substring(0, selectedItem.indexOf('-')).trim());
            String selectedItem2 = String.valueOf(departureCityCB.getSelectedItem());
            Long idForDepartureCity = Long.parseLong(selectedItem2.substring(0, selectedItem2.indexOf('-')).trim());
            String selectedItem3 = String.valueOf(arrivalCityCB.getSelectedItem());
            Long idForArrivalCity = Long.parseLong(selectedItem3.substring(0, selectedItem3.indexOf('-')).trim());
            if (operation.equals(Operation.ADD_FLIGHT)) {
                addFlight(new Flight(null, new Aircraft(idForAircraft), new City(idForDepartureCity), new City(idForArrivalCity),
                        departureTimeTF.getText(), Integer.parseInt(economPriceTF.getText()), Integer.parseInt(businessPriceTF.getText())));
                errorLbl.setText("ADDED!");
            } else if (operation.equals(Operation.CHANGE_FLIGHT)) {
                String selectedItemForId = String.valueOf(flightsCombo.getSelectedItem());
                Long id = Long.parseLong(selectedItemForId.substring(0, selectedItemForId.indexOf('-')).trim());
                changeFlight(new Flight(id, new Aircraft(idForAircraft), new City(idForDepartureCity), new City(idForArrivalCity),
                        departureTimeTF.getText(), Integer.parseInt(economPriceTF.getText()), Integer.parseInt(businessPriceTF.getText())));
                errorLbl.setText("CHANGED!");
            }
        } catch (IOException | ClassNotFoundException | NumberFormatException ioException) {
            errorLbl.setText("ERROR!");
            ioException.printStackTrace();
        }
    }

    public void changeFlight(Flight flight) throws IOException, ClassNotFoundException {
        PackageData changePd = new PackageData();
        changePd.setFlight(flight);
        if (Client.queryWithResponse(changePd, Operation.CHANGE_FLIGHT).getOperation().equals(Operation.OK)){
            MainFrame.writeListToCombo(MainFrame.listFromDB(Operation.LIST_FLIGHTS), MainFrame.getChangeFlight().flightsCombo, TypeData.FLIGHT);
            clearForms();
        }
    }

    public void deleteFlight(Flight flight) throws IOException, ClassNotFoundException {
        PackageData deletePD = new PackageData();
        deletePD.setFlight(flight);
        if (Client.queryWithResponse(deletePD, Operation.DELETE_FLIGHT).getOperation().equals(Operation.OK)){
            MainFrame.writeListToCombo(MainFrame.listFromDB(Operation.LIST_FLIGHTS), MainFrame.getDeleteFlight().flightsCombo, TypeData.FLIGHT);
        }
    }

    public void clearForms(){
        aircraftCB.setSelectedIndex(0);
        departureCityCB.setSelectedIndex(0);
        arrivalCityCB.setSelectedIndex(1);
        departureTimeTF.setText("");
        economPriceTF.setText("");
        businessPriceTF.setText("");
    }

    public JComboBox getFlightsCombo() {
        return flightsCombo;
    }

    public JComboBox getAircraftCB() {
        return aircraftCB;
    }

    public JComboBox getDepartureCityCB() {
        return departureCityCB;
    }

    public JComboBox getArrivalCityCB() {
        return arrivalCityCB;
    }
}

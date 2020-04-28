import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class AddChangeDeleteCity extends Container {
    private JLabel nameLabel, countryLabel, shortNameLabel, listLabel;
    private JTextField nameTf, countryTF, shortNameTF;
    private JComboBox citiesCombo = new JComboBox();
    private JButton confirmBtn, backBtn;

    public AddChangeDeleteCity(Operation operation) throws IOException, ClassNotFoundException {
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

        if (operation.equals(Operation.ADD_CITY) || operation.equals(Operation.CHANGE_CITY)) {
            nameLabel = new JLabel("NAME:");
            nameLabel.setBounds(150, 100, 150, 30);
            add(nameLabel);

            countryLabel = new JLabel("COUNTRY:");
            countryLabel.setBounds(150, 140, 150, 30);
            add(countryLabel);

            shortNameLabel = new JLabel("SHORT NAME:");
            shortNameLabel.setBounds(150, 180, 150, 30);
            add(shortNameLabel);

            nameTf = new JTextField();
            nameTf.setBounds(300, 100, 150, 30 );
            add(nameTf);

            countryTF = new JTextField();
            countryTF.setBounds(300, 140, 150, 30);
            add(countryTF);

            shortNameTF = new JTextField();
            shortNameTF.setBounds(300, 180, 150, 30);
            add(shortNameTF);
        }

        if (operation.equals(Operation.CHANGE_CITY) || operation.equals(Operation.DELETE_CITY)){
            listLabel = new JLabel("CHOOSE CITY");
            add(listLabel);
            MainFrame.writeListToCombo(MainFrame.listFromDB(Operation.LIST_CITIES),
                    citiesCombo, TypeData.CITY );
            add(citiesCombo);

            if (operation.equals(Operation.DELETE_CITY)){
                listLabel.setBounds(200, 120, 200, 30);
                citiesCombo.setBounds(200, 150, 200, 30);
                confirmBtn = new JButton("DELETE");
                confirmBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            String selectedItem = String.valueOf(citiesCombo.getSelectedItem());
                            Long id  = Long.parseLong(selectedItem.substring(0, selectedItem.indexOf('-')).trim());
                            deleteCity(new City(id));
                        } catch (IOException | ClassNotFoundException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                });
            } else {
                listLabel.setBounds(200, 20, 200, 30);
                citiesCombo.setBounds(200, 50, 200, 30);
                confirmBtn = new JButton("CHANGE");
                confirmBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            String selectedItem = String.valueOf(citiesCombo.getSelectedItem());
                            Long id  = Long.parseLong(selectedItem.substring(0, selectedItem.indexOf('-')).trim());
                            changeCity(new City(id, nameTf.getText(), countryTF.getText(), shortNameTF.getText()));
                        } catch (IOException | ClassNotFoundException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                });
            }
        }else if (operation.equals(Operation.ADD_CITY)){
            confirmBtn = new JButton("ADD");
            confirmBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        addCity(new City(null, nameTf.getText(), countryTF.getText(), shortNameTF.getText()));
                    } catch (IOException | ClassNotFoundException ioException) {
                        ioException.printStackTrace();
                    }
                }
            });
        }
        confirmBtn.setBounds(150, 370, 100, 40 );
        add(confirmBtn);
    }

    public void addCity(City city) throws IOException, ClassNotFoundException {
        PackageData addCityPd = new PackageData();
        addCityPd.setCity(city);
        if (Client.queryWithResponse(addCityPd, Operation.ADD_CITY).getOperation().equals(Operation.OK)){
            refreshCities();
            clearForms();
        }
    }

    public void changeCity(City city) throws IOException, ClassNotFoundException {
        PackageData changeCityPd = new PackageData();
        changeCityPd.setCity(city);
        if (Client.queryWithResponse(changeCityPd, Operation.CHANGE_CITY).getOperation().equals(Operation.OK)){
            refreshCities();
            clearForms();
        }
    }

    public void deleteCity(City city) throws IOException, ClassNotFoundException {
        PackageData deleteCityPD = new PackageData();
        deleteCityPD.setCity(city);
        if (Client.queryWithResponse(deleteCityPD, Operation.DELETE_CITY).getOperation().equals(Operation.OK)){
            refreshCities();
        }
    }

    public static void refreshCities() throws IOException, ClassNotFoundException {
        ArrayList temp = MainFrame.listFromDB(Operation.LIST_CITIES);
        MainFrame.writeListToCombo(temp, MainFrame.getChangeCity().citiesCombo, TypeData.CITY);
        MainFrame.writeListToCombo(temp, MainFrame.getDeleteCity().citiesCombo, TypeData.CITY);
    }

    public void clearForms(){
        nameTf.setText("");
        countryTF.setText("");
        shortNameTF.setText("");
    }
}

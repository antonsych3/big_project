import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class AddChangeDeleteAircraft extends Container {
    private JLabel nameLabel, modelLabel, businesCapLabel, economCapLabel, listLabel;
    private JTextField nameTf, modelTf;
    private JComboBox businessCapacity, economCapacity, listOfAircrafts = new JComboBox();
    private JButton confirmBtn, backBtn;

    public AddChangeDeleteAircraft(Operation operation) throws IOException, ClassNotFoundException {
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

        if (operation.equals(Operation.ADD_AIRCRAFT) || operation.equals(Operation.CHANGE_AIRCRAFT)) {
            nameLabel = new JLabel("NAME:");
            nameLabel.setBounds(150, 100, 150, 30);
            add(nameLabel);

            modelLabel = new JLabel("MODEL:");
            modelLabel.setBounds(150, 140, 150, 30);
            add(modelLabel);

            businesCapLabel = new JLabel("BUSINESS CAPACITY:");
            businesCapLabel.setBounds(150, 180, 150, 30);
            add(businesCapLabel);

            economCapLabel = new JLabel("ECONOM CAPACITY:");
            economCapLabel.setBounds(150, 220, 150, 30);
            add(economCapLabel);

            nameTf = new JTextField();
            nameTf.setBounds(300, 100, 150, 30 );
            add(nameTf);

            modelTf = new JTextField();
            modelTf.setBounds(300, 140, 150, 30);
            add(modelTf);

            businessCapacity = new JComboBox();
            businessCapacity.setBounds(300, 180, 150, 30);
            for (int i = 0; i < 30; i++) {
                businessCapacity.addItem(i);
            }
            add(businessCapacity);

            economCapacity = new JComboBox();
            economCapacity.setBounds(300, 220, 150, 30);
            for (int i = 0; i < 300; i++) {
                economCapacity.addItem(i);
            }
            add(economCapacity);
        }

        if (operation.equals(Operation.CHANGE_AIRCRAFT) || operation.equals(Operation.DELETE_AIRCRAFT)){
            listLabel = new JLabel("CHOOSE AIRCRAFT");
            add(listLabel);
            MainFrame.writeListToCombo(MainFrame.listFromDB(Operation.LIST_AIRCRAFTS),
                    listOfAircrafts, TypeData.AIRCRAFT );
            add(listOfAircrafts);

            if (operation.equals(Operation.DELETE_AIRCRAFT)){
                listLabel.setBounds(200, 120, 200, 30);
                listOfAircrafts.setBounds(200, 150, 200, 30);
                confirmBtn = new JButton("DELETE");
                confirmBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            String selectedItem = String.valueOf(listOfAircrafts.getSelectedItem());
                            Long id  = Long.parseLong(selectedItem.substring(0, selectedItem.indexOf('-')).trim());
                                deleteAircraft(new Aircraft(id));
                        } catch (IOException | ClassNotFoundException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                });
            } else {
                listLabel.setBounds(200, 20, 200, 30);
                listOfAircrafts.setBounds(200, 50, 200, 30);
                confirmBtn = new JButton("CHANGE");
                confirmBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            String selectedItem = String.valueOf(listOfAircrafts.getSelectedItem());
                            Long id  = Long.parseLong(selectedItem.substring(0, selectedItem.indexOf('-')).trim());
                            changeAircraft(new Aircraft(id, nameTf.getText(), modelTf.getText(), (Integer) businessCapacity.getSelectedItem(), (Integer) economCapacity.getSelectedItem()));
                        } catch (IOException | ClassNotFoundException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                });
            }
        }else if (operation.equals(Operation.ADD_AIRCRAFT)){
            confirmBtn = new JButton("ADD");
            confirmBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        addAircraft(new Aircraft(null, nameTf.getText(), modelTf.getText(), (Integer) businessCapacity.getSelectedItem(), (Integer) economCapacity.getSelectedItem()));
                    } catch (IOException | ClassNotFoundException ioException) {
                        ioException.printStackTrace();
                    }
                }
            });
        }
        confirmBtn.setBounds(150, 370, 100, 40 );
        add(confirmBtn);
    }

    public void addAircraft(Aircraft aircraft) throws IOException, ClassNotFoundException {
            PackageData addAircraftPd = new PackageData();
            addAircraftPd.setAircraft(aircraft);
            if (Client.queryWithResponse(addAircraftPd, Operation.ADD_AIRCRAFT).getOperation().equals(Operation.OK)){
                refreshAircrafts();
                clearForms();
            }
    }

    public void changeAircraft(Aircraft aircraft) throws IOException, ClassNotFoundException {
            PackageData changeAircraftPd = new PackageData();
            changeAircraftPd.setAircraft(aircraft);
            if (Client.queryWithResponse(changeAircraftPd, Operation.CHANGE_AIRCRAFT).getOperation().equals(Operation.OK)){
                refreshAircrafts();
                clearForms();
            }
    }

    public void deleteAircraft(Aircraft aircraft) throws IOException, ClassNotFoundException {
        PackageData deleteAircraftPD = new PackageData();
        deleteAircraftPD.setAircraft(aircraft);
        if (Client.queryWithResponse(deleteAircraftPD, Operation.DELETE_AIRCRAFT).getOperation().equals(Operation.OK)){
            refreshAircrafts();
        }
    }

    public static void refreshAircrafts() throws IOException, ClassNotFoundException {
        ArrayList temp = MainFrame.listFromDB(Operation.LIST_AIRCRAFTS);
        MainFrame.writeListToCombo(temp, MainFrame.getChangeAircraft().listOfAircrafts, TypeData.AIRCRAFT);
        MainFrame.writeListToCombo(temp, MainFrame.getDeleteAircraft().listOfAircrafts, TypeData.AIRCRAFT);
    }

    public void clearForms(){
        nameTf.setText("");
        modelTf.setText("");
        businessCapacity.setSelectedIndex(0);
        economCapacity.setSelectedIndex(0);
    }
}

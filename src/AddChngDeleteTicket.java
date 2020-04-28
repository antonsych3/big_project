import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class AddChngDeleteTicket extends Container {
    private JLabel flightIdLbl, nameLbl, surnameLbl, passportLbl, ticetTypeLbl, listLabel;
    private JTextField nameTF, surnameTF, passportTF;
    private JComboBox flightIdCB, ticketTypeCB, ticketsCombo = new JComboBox();
    private JButton confirmBtn, backBtn;

    public AddChngDeleteTicket(Operation operation) {
        setLayout(null);
        setSize(600, 600);

        backBtn = new JButton("BACK");
        backBtn.setBounds(350, 370, 100, 40 );
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame.showWindow(MainFrame.getMenuTickets());
            }
        });
        add(backBtn);

        if (operation.equals(Operation.ADD_TICKET) || operation.equals(Operation.CHANGE_TICKET)) {
            flightIdLbl = new JLabel("FLIGHT ID:");
            flightIdLbl.setBounds(150, 100, 150, 30);
            add(flightIdLbl);

            nameLbl = new JLabel("NAME:");
            nameLbl.setBounds(150, 140, 150, 30);
            add(nameLbl);

            surnameLbl = new JLabel("SURNAME:");
            surnameLbl.setBounds(150, 180, 150, 30);
            add(surnameLbl);

            passportLbl = new JLabel("PASSPORT:");
            passportLbl.setBounds(150, 220, 150, 30);
            add(passportLbl);

            ticetTypeLbl = new JLabel("TICKET TYPE:");
            ticetTypeLbl.setBounds(150, 260, 150, 30);
            add(ticetTypeLbl);

            flightIdCB = new JComboBox();
            flightIdCB.setBounds(300, 100, 150, 30);
            add(flightIdCB);

            ticketTypeCB = new JComboBox();
            ticketTypeCB.setBounds(300, 260, 150, 30);
            ticketTypeCB.addItem("bc");
            ticketTypeCB.addItem("ec");
            add(ticketTypeCB);

            nameTF = new JTextField();
            nameTF.setBounds(300, 140, 150, 30 );
            add(nameTF);

            surnameTF = new JTextField();
            surnameTF.setBounds(300, 180, 150, 30 );
            add(surnameTF);

            passportTF = new JTextField();
            passportTF.setBounds(300, 220, 150, 30);
            add(passportTF);
        }

        if (operation.equals(Operation.CHANGE_TICKET) || operation.equals(Operation.DELETE_TICKET)){
            listLabel = new JLabel("CHOOSE TICKET");
            add(listLabel);
            add(ticketsCombo);

            if (operation.equals(Operation.DELETE_TICKET)){
                listLabel.setBounds(200, 120, 200, 30);
                ticketsCombo.setBounds(200, 150, 200, 30);
                confirmBtn = new JButton("DELETE");
                confirmBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            String selectedItem = String.valueOf(ticketsCombo.getSelectedItem());
                            Long id  = Long.parseLong(selectedItem.substring(0, selectedItem.indexOf('-')).trim());
                            deleteFlight(new Ticket(id));
                        } catch (IOException | ClassNotFoundException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                });
            } else {
                listLabel.setBounds(200, 20, 200, 30);
                ticketsCombo.setBounds(200, 50, 200, 30);
                confirmBtn = new JButton("CHANGE");
                confirmBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        getInfoAndSend(Operation.CHANGE_TICKET);
                    }
                });
            }
        }else if (operation.equals(Operation.ADD_TICKET)){
            confirmBtn = new JButton("ADD");
            confirmBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    getInfoAndSend(Operation.ADD_TICKET);
                }
            });
        }
        confirmBtn.setBounds(150, 370, 100, 40 );
        add(confirmBtn);
    }

    public void addTicket(Ticket ticket) throws IOException, ClassNotFoundException {
        PackageData addPd = new PackageData();
        addPd.setTicket(ticket);
        if (Client.queryWithResponse(addPd, Operation.ADD_TICKET).getOperation().equals(Operation.OK)){
            clearForms();
        }
    }

    public void getInfoAndSend(Operation operation) {
        try {
            String selectedItem = String.valueOf(flightIdCB.getSelectedItem());
            Long idForFlight = Long.parseLong(selectedItem.substring(0, selectedItem.indexOf('-')).trim());
            Flight flight = new Flight(idForFlight);
            if (operation.equals(Operation.ADD_TICKET)) {
                addTicket(new Ticket(null, flight, nameTF.getText(), surnameTF.getText(), passportTF.getText(), String.valueOf(ticketTypeCB.getSelectedItem())));

            } else if (operation.equals(Operation.CHANGE_TICKET)) {
                String selectedItemForId = String.valueOf(ticketsCombo.getSelectedItem());
                Long id = Long.parseLong(selectedItemForId.substring(0, selectedItemForId.indexOf('-')).trim());
                changeTicket(new Ticket(id, flight, nameTF.getText(), surnameTF.getText(), passportTF.getText(), String.valueOf(ticketTypeCB.getSelectedItem())));
            }
        } catch (IOException | ClassNotFoundException | NumberFormatException ioException) {
            ioException.printStackTrace();
        }
    }

    public void changeTicket(Ticket ticket) throws IOException, ClassNotFoundException {
        PackageData changePd = new PackageData();
        changePd.setTicket(ticket);
        if (Client.queryWithResponse(changePd, Operation.CHANGE_TICKET).getOperation().equals(Operation.OK)){
            MainFrame.writeListToCombo(MainFrame.listFromDB(Operation.LIST_TICKETS), MainFrame.getChangeTicket().ticketsCombo, TypeData.TICKET);
            clearForms();
        }
    }

    public void deleteFlight(Ticket ticket) throws IOException, ClassNotFoundException {
        PackageData deletePD = new PackageData();
        deletePD.setTicket(ticket);
        if (Client.queryWithResponse(deletePD, Operation.DELETE_TICKET).getOperation().equals(Operation.OK)){
            MainFrame.writeListToCombo(MainFrame.listFromDB(Operation.LIST_TICKETS), MainFrame.getDeleteTicket().ticketsCombo, TypeData.TICKET);
        }
    }

    public void clearForms(){
        flightIdCB.setSelectedIndex(0);
        ticketTypeCB.setSelectedIndex(0);
        nameTF.setText("");
        surnameTF.setText("");
        passportTF.setText("");
    }

    public JComboBox getFlightIdCB() {
        return flightIdCB;
    }

    public JComboBox getTicketsCombo() {
        return ticketsCombo;
    }
}

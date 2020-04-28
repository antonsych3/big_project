import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MenuFrame extends Container {

    private JButton addBtn, changeBtn, deleteBtn, listBtn, backBtn;

    public MenuFrame( String str) {
        setLayout(null);
        setSize(600, 600);

        addBtn = new JButton("ADD "+str);
        addBtn.setBounds(200, 100, 200, 50 );
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (MainFrame.getTypeData().equals(TypeData.AIRCRAFT))
                        MainFrame.showWindow(MainFrame.getAddAircraft());
                    else if (MainFrame.getTypeData().equals(TypeData.CITY))
                        MainFrame.showWindow(MainFrame.getAddCity());
                    else if (MainFrame.getTypeData().equals(TypeData.FLIGHT)) {
                        MainFrame.lastFlightsInfoForAdd();
                        MainFrame.showWindow(MainFrame.getAddFlight());
                    }else if (MainFrame.getTypeData().equals(TypeData.TICKET)) {
                        MainFrame.lastTicketsInfoForAdd();
                        MainFrame.showWindow(MainFrame.getAddTicket());
                    }
                }catch (IOException | ClassNotFoundException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        add(addBtn);

        changeBtn = new JButton("CHANGE "+str);
        changeBtn.setBounds(200, 170, 200, 50 );
        changeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (MainFrame.getTypeData().equals(TypeData.AIRCRAFT)) {
                        MainFrame.showWindow(MainFrame.getChangeAircraft());
                    } else if (MainFrame.getTypeData().equals(TypeData.CITY))
                        MainFrame.showWindow(MainFrame.getChangeCity());
                    else if (MainFrame.getTypeData().equals(TypeData.FLIGHT)) {
                        MainFrame.lastFlightsInfoForChange();
                        MainFrame.showWindow(MainFrame.getChangeFlight());
                    }else if (MainFrame.getTypeData().equals(TypeData.TICKET)) {
                        MainFrame.lastTicketsInfoForChange();
                        MainFrame.showWindow(MainFrame.getChangeTicket());
                    }
                }catch (IOException | ClassNotFoundException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        add(changeBtn);

        deleteBtn = new JButton("DELETE "+str);
        deleteBtn.setBounds(200, 240, 200, 50 );
        deleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (MainFrame.getTypeData().equals(TypeData.AIRCRAFT))
                        MainFrame.showWindow(MainFrame.getDeleteAircraft());
                    else if (MainFrame.getTypeData().equals(TypeData.CITY))
                        MainFrame.showWindow(MainFrame.getDeleteCity());
                    else if (MainFrame.getTypeData().equals(TypeData.FLIGHT)) {
                        MainFrame.lastFlightsInfoForDelete();
                        MainFrame.showWindow(MainFrame.getDeleteFlight());
                    }else if (MainFrame.getTypeData().equals(TypeData.TICKET)) {
                        MainFrame.lastTicketsInfoForDelete();
                        MainFrame.showWindow(MainFrame.getDeleteTicket());
                    }
                }catch (IOException | ClassNotFoundException ioException) {
                    ioException.printStackTrace();
                }

            }
        });
        add(deleteBtn);

        listBtn = new JButton("LIST "+str+"S");
        listBtn.setBounds(200, 310, 200, 50 );
        listBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    MainFrame.writeToListFrame();
                }catch (IOException | ClassNotFoundException ioException) {
                    ioException.printStackTrace();
                }
                MainFrame.showWindow(MainFrame.getListFrame());
            }
        });
        add(listBtn);

        backBtn = new JButton("BACK");
        backBtn.setBounds(200, 380, 200, 50 );
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(str.equals("TICKET")){
                    MainFrame.showWindow(MainFrame.getAuthorization());
                } else MainFrame.showWindow(MainFrame.getAdminFrame());
            }
        });
        add(backBtn);
    }
}

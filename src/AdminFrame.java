import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminFrame extends Container {
    private JButton aircraftsBtn, citiesBtn, flightsBtn, backBtn;

    public AdminFrame () {
        setLayout(null);
        setSize(600, 600);

        aircraftsBtn = new JButton("AIRCRAFTS");
        aircraftsBtn.setBounds(200, 100, 200, 50 );
        aircraftsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame.setTypeData(TypeData.AIRCRAFT);
                MainFrame.showWindow(MainFrame.getMenuFrame());
            }
        });
        add(aircraftsBtn);

        citiesBtn = new JButton("CITIES");
        citiesBtn.setBounds(200, 170, 200, 50 );
        citiesBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame.setTypeData(TypeData.CITY);
                MainFrame.showWindow(MainFrame.getMenuFrame());
            }
        });
        add(citiesBtn);

        flightsBtn = new JButton("FLIGHTS");
        flightsBtn.setBounds(200, 240, 200, 50 );
        flightsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame.setTypeData(TypeData.FLIGHT);
                MainFrame.showWindow(MainFrame.getMenuFrame());
            }
        });
        add(flightsBtn);

        backBtn = new JButton("BACK");
        backBtn.setBounds(200, 310, 200, 50 );
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame.showWindow(MainFrame.getAuthorization());
            }
        });
        add(backBtn);
    }
}

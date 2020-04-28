import com.sun.xml.internal.bind.v2.runtime.output.Pcdata;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class AuthorizationFrame extends Container {
    private JTextField loginTf, passwordTf;
    private JLabel loginLabel, passwordLabel, wrongAuthorization;
    private JButton signIN;

    public AuthorizationFrame() {
        setLayout(null);
        setSize(600, 600);

        loginLabel = new JLabel("Login:");
        loginLabel.setBounds(200, 100, 200, 30);
        add(loginLabel);

        passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(200, 170, 200, 30);
        add(passwordLabel);

        wrongAuthorization = new JLabel("");
        wrongAuthorization.setBounds(200, 300, 200, 50);
        add(wrongAuthorization);


        loginTf = new JTextField("");
        loginTf.setBounds(200, 130, 200, 30);
        add(loginTf);

        passwordTf = new JTextField("");
        passwordTf.setBounds(200, 200, 200, 30);
        add(passwordTf);

        signIN = new JButton("SIGN IN");
        signIN.setBounds(200, 260, 200, 30 );
        signIN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    PackageData packageData = new PackageData();
                    packageData.setUser(new User(loginTf.getText(), passwordTf.getText()));
                    packageData = Client.queryWithResponse(packageData, Operation.AUTHORIZATION);
                    if (packageData.getUser().getRole() != null) {
                        wrongAuthorization.setText("");
                        if (packageData.getUser().getRole().equals(Role.ADMIN)) {
                            MainFrame.showWindow(MainFrame.getAdminFrame());
                        } else if (packageData.getUser().getRole().equals(Role.CASHIER)) {
                            MainFrame.setTypeData(TypeData.TICKET);
                            MainFrame.showWindow(MainFrame.getMenuTickets());
                        }
                    }else {
                        wrongAuthorization.setText("WRONG LOGIN OR PASSWORD!");
                    }
                    loginTf.setText("");
                    passwordTf.setText("");
                } catch (IOException | ClassNotFoundException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        add(signIN);
    }
}

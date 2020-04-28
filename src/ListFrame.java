import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ListFrame extends Container {
    private JTextArea listTA;
    private JButton backBtn;

    public ListFrame(){
        setLayout(null);
        setSize(600,600);

        listTA = new JTextArea();
        listTA.setEditable(false);
        listTA.setBounds(50, 50, 500, 400);
        add(listTA);

        backBtn = new JButton("BACK");
        backBtn.setBounds(200, 480, 200, 50);
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (MainFrame.getTypeData().equals(TypeData.TICKET))
                    MainFrame.showWindow(MainFrame.getMenuTickets());
                else MainFrame.showWindow(MainFrame.getMenuFrame());
            }
        });
        add(backBtn);
    }

    public JTextArea getListTA() {
        return listTA;
    }
}

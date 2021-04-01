package App;

import Presentation.AdminInitialGUI;
import Presentation.AdminMainGUI;
import Presentation.CustomerInitialGUI;
import Presentation.CustomerMainGUI;

import javax.swing.*;

public class Main {

    public static void main(final String[] args) throws Exception {
        JFrame customerFrame = new JFrame("Welcome");
        customerFrame.setContentPane(new CustomerInitialGUI().getPanel1());
        customerFrame.setDefaultCloseOperation((JFrame.DISPOSE_ON_CLOSE));
        customerFrame.pack();
        customerFrame.setVisible(true);

        JFrame adminFrame = new JFrame("Administrator");
        adminFrame.setContentPane(new AdminInitialGUI().getPanel1());
        adminFrame.setDefaultCloseOperation((JFrame.DISPOSE_ON_CLOSE));
        adminFrame.pack();
        adminFrame.setVisible(true);
    }
}
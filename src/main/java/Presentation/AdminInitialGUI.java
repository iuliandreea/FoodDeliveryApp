package Presentation;

import Controller.AdminController;
import Service.AdminService;

import javax.swing.*;

public class AdminInitialGUI {
    private JPanel panel1;
    private JLabel userLabel;
    private JLabel passwordLabel;
    private JTextField usernameField;
    private JTextField passwordField;
    private JButton loginButton;

    AdminController adminController = new AdminController();

    public AdminInitialGUI() {

        adminController.setAdminInitialGUI(this);

        loginButton.addActionListener(adminController);
    }

    public JPanel getPanel1(){
        return this.panel1;
    }

    public JButton getLoginButton(){
        return this.loginButton;
    }

    public JTextField getUsernameField(){
        return this.usernameField;
    }

    public JTextField getPasswordField(){
        return this.passwordField;
    }
}

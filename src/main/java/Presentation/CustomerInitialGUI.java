package Presentation;

import Controller.CustomerController;
import Service.CustomerService;

import javax.swing.*;

public class CustomerInitialGUI {
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JPanel loginWindow;
    private JPanel signupWindow;
    private JTextField emailField1;
    private JLabel emailLabel1;
    private JLabel passwordLabel1;
    private JTextField passwordField1;
    private JButton loginButton;
    private JLabel nameLabel;
    private JTextField nameField;
    private JLabel emailLabel2;
    private JTextField emailField2;
    private JTextField passwordField2;
    private JLabel passwordLabel2;
    private JTextField addressField;
    private JLabel addressLabel;
    private JButton signupButton;

    CustomerController customerController = new CustomerController();

    public CustomerInitialGUI() {

        customerController.setCustomerInitialGUI(this);

        loginButton.addActionListener(customerController);
        signupButton.addActionListener(customerController);
    }

    public JPanel getPanel1(){
        return this.panel1;
    }

    public JButton getLoginButton(){
        return this.loginButton;
    }

    public JButton getSignupButton(){
        return this.signupButton;
    }

    public JTextField getEmailField1(){
        return this.emailField1;
    }

    public JTextField getPasswordField1(){
        return this.passwordField1;
    }

    public JTextField getNameField(){
        return this.nameField;
    }

    public JTextField getEmailField2(){
        return this.emailField2;
    }

    public JTextField getPasswordField2(){
        return this.passwordField2;
    }

    public JTextField getAddressField(){
        return this.addressField;
    }
}

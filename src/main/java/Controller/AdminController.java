package Controller;

import Model.ProductEntity;
import Presentation.AdminInitialGUI;
import Presentation.AdminMainGUI;
import Service.AdminService;
import Service.CustomerService;
import Service.ProductService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminController implements ActionListener {

    private AdminInitialGUI adminInitialGUI;
    private AdminMainGUI adminMainGUI;

    private AdminService adminService;
    private ProductService productService;
    private CustomerService customerService;

    public AdminController(){
        this.adminService = new AdminService();
        this.productService = new ProductService();
        this.customerService = new CustomerService();
    }

    public void setAdminInitialGUI(AdminInitialGUI adminInitialGUI){
        this.adminInitialGUI = adminInitialGUI;
    }

    public void setAdminMainGUI(AdminMainGUI adminMainGUI){
        this.adminMainGUI = adminMainGUI;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        String validator = "";
        if(adminInitialGUI != null && source == adminInitialGUI.getLoginButton()){
            String user = adminInitialGUI.getUsernameField().getText();
            String password = adminInitialGUI.getPasswordField().getText();
            if(adminService.validateAdmin(user, password)){
                openMainAdminGUI();
            }
            else{
                JOptionPane.showMessageDialog(null, "Incorrect user data\n", "Error", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        else{
            if(source == adminMainGUI.getUpdateProduct()){
                String initialName = adminMainGUI.getMenuNameCombo();
                String finalName = adminMainGUI.getuProductNameField().getText();
                Double price =  Double.parseDouble(adminMainGUI.getuProductPriceField().getText());
                String desc = adminMainGUI.getuProductDescField().getText();
                boolean deleted = Boolean.parseBoolean(adminMainGUI.getuProductisDeletedField().getText());
                validator = productService.updateProduct(initialName, finalName, price, desc, deleted);
            }
            if(source == adminMainGUI.getDeleteProduct()){
                String name = adminMainGUI.getMenuNameCombo();
                productService.deleteProduct(name);
            }
            if(source == adminMainGUI.getAddProductButton()){
                String name = adminMainGUI.getaProductField().getText();
                Double price = Double.parseDouble(adminMainGUI.getaProductPriceField().getText());
                String desc = adminMainGUI.getaProductDescField().getText();
                validator = productService.addProduct(name, price, desc);
            }
            if(source == adminMainGUI.getuConfirmButton()){
                String initialEmail = adminMainGUI.getCustomerEmailCombo();
                String name = adminMainGUI.geteCustNameField().getText();
                String email = adminMainGUI.geteCustEmailField().getText();
                String password = adminMainGUI.geteCustPassField().getText();
                String address = adminMainGUI.geteCustAddrField().getText();
                boolean loyalty = adminMainGUI.geteCustLoyaltyBox().isSelected();
                boolean deleted = Boolean.parseBoolean(adminMainGUI.geteCustDeletedField().getText());
                validator = customerService.updateCustomer(initialEmail, name, email, password, address, loyalty, deleted);
            }
            if(source == adminMainGUI.getuDeleteButton()){
                String email = adminMainGUI.getCustomerEmailCombo();
                customerService.deleteCustomer(email);
            }
            if(source == adminMainGUI.getaCustButton()){
                String name = adminMainGUI.getaCustNameField().getText();
                String email = adminMainGUI.getaCustEmailField().getText();
                String password = adminMainGUI.getaCustPassField().getText();
                String address = adminMainGUI.getaCustAddressField().getText();
                boolean loyalty = adminMainGUI.getaCustLoyaltyBox().isSelected();
                validator = customerService.addCustomer(name, email, password, address, loyalty);
            }
        }
        if(!validator.equals("")){
            JOptionPane.showMessageDialog(null, validator, "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void openMainAdminGUI(){
        JFrame adminFrame = new JFrame("Administrator");
        adminFrame.setContentPane(new AdminMainGUI().getPanel1());
        adminFrame.setDefaultCloseOperation((JFrame.DISPOSE_ON_CLOSE));
        adminFrame.pack();
        adminFrame.setVisible(true);
    }
}

package Controller;

import Model.CartEntity;
import Model.CustomerEntity;
import Presentation.AdminMainGUI;
import Presentation.CustomerInitialGUI;
import Presentation.CustomerMainGUI;
import Service.CartItemService;
import Service.CartService;
import Service.CustomerService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerController implements ActionListener {

    private CustomerInitialGUI customerInitialGUI;
    private CustomerMainGUI customerMainGUI;

    private CustomerService customerService;
    private CartService cartService;
    private CartItemService cartItemService;

    private static int id;

    public CustomerController(){
        this.customerService = new CustomerService();
        this.cartService = new CartService();
        this.cartItemService = new CartItemService();
    }

    public void setCustomerInitialGUI(CustomerInitialGUI customerInitialGUI){
        this.customerInitialGUI = customerInitialGUI;
    }

    public void setCustomerMainGUI(CustomerMainGUI customerMainGUI){
        this.customerMainGUI = customerMainGUI;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        String validator = "";
        if(customerInitialGUI != null){
            if(source == customerInitialGUI.getLoginButton()){
                String email = customerInitialGUI.getEmailField1().getText();
                String password = customerInitialGUI.getPasswordField1().getText();
                this.id = customerService.validateCustomer(email, password);
                if(id > 0){
                    openMainCustomerGUI(id);
                }
                else{
                    JOptionPane.showMessageDialog(null, "Incorrect user data\n", "Error", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            if(source == customerInitialGUI.getSignupButton()){
                String name = customerInitialGUI.getNameField().getText();
                String email = customerInitialGUI.getEmailField2().getText();
                String password = customerInitialGUI.getPasswordField2().getText();
                String address = customerInitialGUI.getAddressField().getText();
                validator = customerService.addCustomer(name, email, password, address, false);
            }
        }
        else{
            if(source == customerMainGUI.getUpdateButton()){
                String name = customerMainGUI.getNameField().getText();
                String email = customerMainGUI.getEmailField().getText();
                String password = customerMainGUI.getPasswordField().getText();
                String address = customerMainGUI.getAddressField().getText();
                boolean loyalty = Boolean.parseBoolean(customerMainGUI.getLoyaltyField().getText());
                validator = customerService.updateCustomerId(id, name, email, password, address, loyalty, false);
            }
            if(source == customerMainGUI.getDeleteButton()){
                customerService.deleteCustomerId(id);
            }
            if(source == customerMainGUI.getOrderButton()){
                CartEntity activeCart = customerMainGUI.getActiveCart();
                String address = customerMainGUI.getDeliveryField().getText();
                String paymentMethod = customerMainGUI.getPaymentCombo();
                validator = cartService.completeOrder(activeCart, address, paymentMethod);
            }
            if(source == customerMainGUI.getAddToCartButton()){
                CartEntity activeCart = customerMainGUI.getActiveCart();
                String product = customerMainGUI.getProductNameComboUpdate();
                int quantity = Integer.parseInt(customerMainGUI.getQuantityCombo());

                if(activeCart == null){
                    CartEntity cart = new CartEntity();
                    cart.setIdclient(id);
                    cart.setDeliveryAddress("");
                    cart.setTotalPrice(0);
                    cart.setDate(null);
                    cartService.createCart(cart);
                    CartEntity newCart = cartService.getActiveCartByCustomerId(id);
                    validator = cartItemService.addToCart(newCart, product, quantity);
                }
                else{
                    validator = cartItemService.addToCart(activeCart, product, quantity);
                }
            }
            if(source == customerMainGUI.getDeleteFromCartButton()){
                CartEntity activeCart = customerMainGUI.getActiveCart();
                String product = customerMainGUI.getProductNameComboDelete();
                cartItemService.deleteFromCart(activeCart, product);
            }
            if(source == customerMainGUI.getEraseCartButton()){
                CartEntity activeCart = customerMainGUI.getActiveCart();
                System.out.println(activeCart.getIdcart());
                cartService.deleteCart(activeCart);
            }
        }
        if(!validator.equals("")){
            JOptionPane.showMessageDialog(null, validator, "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void openMainCustomerGUI(int id){
        JFrame adminFrame = new JFrame("Customer");
        adminFrame.setContentPane(new CustomerMainGUI(id).getPanel1());
        adminFrame.setDefaultCloseOperation((JFrame.DISPOSE_ON_CLOSE));
        adminFrame.pack();
        adminFrame.setVisible(true);
    }
}

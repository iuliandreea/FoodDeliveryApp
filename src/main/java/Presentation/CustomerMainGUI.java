package Presentation;

import Controller.CustomerController;
import Model.CustomerEntity;
import Service.CartService;
import Service.CartItemService;
import Service.CustomerService;
import Service.ProductService;
import Model.CartEntity;
import Model.CartItemEntity;
import Model.ProductEntity;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Iterator;
import java.util.List;

public class CustomerMainGUI {
    private JPanel panel1;
    private JTabbedPane tabbedPane1;
    private JPanel profile;
    private JPanel viewCart;
    private JPanel addToCart;
    private JPanel history;
    private JLabel name;
    private JTextField nameField;
    private JTextField emailField;
    private JLabel emailLabel;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JLabel addressLabel;
    private JTextField addressField;
    private JLabel loyaltyLabel;
    private JTextField loyaltyField;
    private JButton updateButton;
    private JButton deleteButton;
    private JTable cartTable;
    private JButton eraseCartButton;
    private JButton orderButton;
    private JLabel deliveryLabel;
    private JTextField deliveryField;
    private JLabel paymentLabel;
    private JTextField totalPriceField;
    private JComboBox paymentBox;
    private JLabel totalPriceLabel;
    private JComboBox productBox;
    private JTextField quantityField;
    private JLabel productLabel;
    private JLabel quantityLabel;
    private JLabel priceLabel;
    private JTextField priceField;
    private JLabel interPriceLabel;
    private JTextField interPriceField;
    private JButton addToCartButton;
    private JTable historyTable;
    private JPanel deleteFromCart;
    private JLabel deleteProductLabel;
    private JComboBox deleteProductBox;
    private JTextField deletePriceField;
    private JButton deleteFromCartButton;
    private JButton finalConfirmProfile;
    private JButton finalConfirmAdd;
    private JButton finalConfirmDelete;
    private JButton finalConfirmOrder;

    private CartItemService cartItemService = new CartItemService();
    private CustomerService customerService = new CustomerService();
    private ProductService productService = new ProductService();
    private CartService cartService = new CartService();
    private CustomerController customerController = new CustomerController();

    private int idcustomer;
    private String productNameComboUpdate;
    private String quantityCombo;
    private String productNameComboDelete;
    private String paymentCombo;

    private List<CartItemEntity> cartItemList;
    private CartEntity activeCart;

    public CustomerMainGUI(int idcustomer) {

        customerController.setCustomerMainGUI(this);

        this.idcustomer = idcustomer;
        viewCustomer();
        viewCart();
        viewHistory();

        viewMenuCombo();
        viewCartCombo();
        viewPaymentCombo();

        updateButton.addActionListener(customerController);
        deleteButton.addActionListener(customerController);

        productBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(productBox.getSelectedIndex() != -1){
                    productNameComboUpdate = productBox.getSelectedItem().toString();
                    ProductEntity product = productService.getByProductName(productNameComboUpdate);
                    priceField.setText(String.valueOf(product.getPrice()));
                }
            }
        });

        quantityField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quantityCombo = quantityField.getText();
                ProductEntity product = productService.getByProductName(productNameComboUpdate);
                interPriceField.setText(String.valueOf(Double.parseDouble(quantityCombo) * product.getPrice()));
            }
        });

        addToCartButton.addActionListener(customerController);

        deleteProductBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(deleteProductBox.getSelectedIndex() != -1){
                    productNameComboDelete = deleteProductBox.getSelectedItem().toString();
                    ProductEntity product = productService.getByProductName(productNameComboDelete);
                }
            }
        });

        deleteFromCartButton.addActionListener(customerController);

        paymentBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                paymentCombo = paymentBox.getSelectedItem().toString();
            }
        });

        orderButton.addActionListener(customerController);
        eraseCartButton.addActionListener(customerController);

        finalConfirmOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewCart();
                viewCartCombo();
                viewHistory();
            }
        });
        finalConfirmDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewCartCombo();
                viewCart();
            }
        });
        finalConfirmProfile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewCustomer();
            }
        });
        finalConfirmAdd.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                viewCartCombo();
                viewCart();
            }
        });
    }

    public void viewCustomer(){
        CustomerEntity customer = customerService.getCustomerById(idcustomer);
        nameField.setText(customer.getName());
        emailField.setText(customer.getEmail());
        passwordField.setText(customer.getPassword());
        addressField.setText(customer.getAddress());
        loyaltyField.setText(String.valueOf(customer.isLoyalty()));
    }

    public void viewMenuCombo(){
        productBox.removeAllItems();
        List<ProductEntity> menuList = productService.getAllProducts();
        if(!menuList.isEmpty()){
            Iterator<ProductEntity> it = menuList.iterator();
            while(it.hasNext()){
                ProductEntity p = it.next();
                if(!p.isDeleted()){
                    productBox.addItem(p.getName());
                }
            }
            productNameComboUpdate = productBox.getItemAt(0).toString();
        }
    }

    public void viewCartCombo(){
        deleteProductBox.removeAllItems();
        CartEntity activeCart = cartService.getActiveCartByCustomerId(idcustomer);
        if(activeCart != null){
            List<CartItemEntity> cartList = cartItemService.getCartItemsByCartId(activeCart.getIdcart());
            if(!cartList.isEmpty()){
                Iterator<CartItemEntity> it = cartList.iterator();
                while(it.hasNext()){
                    CartItemEntity ci = it.next();
                    if(!ci.isDeleted()){
                        String name = productService.getByProductId(ci.getIdproduct()).getName();
                        deleteProductBox.addItem(name);
                    }
                }
                productNameComboDelete = deleteProductBox.getItemAt(0).toString();
            }
        }
    }

    public void viewPaymentCombo(){
        paymentBox.addItem("CARD");
        paymentBox.addItem("CASH");
        paymentCombo = paymentBox.getItemAt(0).toString();
    }


    public void viewCart(){
        activeCart = cartService.getActiveCartByCustomerId(idcustomer);
        if(activeCart != null) {
            List<CartItemEntity> cartItems = cartItemService.getCartItemsByCartId(activeCart.getIdcart());
            DefaultTableModel model = new DefaultTableModel(null, new String[] {"ID", "Name", "Quantity", "Price"});
            String[] tableContent = new String[4];
            Iterator<CartItemEntity> it = cartItems.iterator();

            while(it.hasNext()){

                CartItemEntity ci = it.next();
                if(!ci.isDeleted()){
                    ProductEntity product = productService.getByProductId(ci.getIdproduct());

                    tableContent[0] = String.valueOf(product.getIdproduct());
                    tableContent[1] = product.getName();
                    tableContent[2] = String.valueOf(ci.getQuantity());
                    tableContent[3] = String.valueOf(ci.getInterPrice());

                    model.addRow(tableContent);
                }
            }
            cartTable.setModel(model);
            totalPriceField.setText(String.valueOf(activeCart.getTotalPrice()));
            cartItemList = cartItems;
        }
    }

    public void viewHistory(){
        List<CartEntity> pastCarts = cartService.getAllByCustomerId(idcustomer);
        if(!pastCarts.isEmpty()) {

            DefaultTableModel model = new DefaultTableModel(null, new String[] {"ID", "Address", "Date", "Products", "Total Price", "Payment Method"});
            String[] tableContent = new String[6];

            Iterator<CartEntity> itCart = pastCarts.iterator();
            while (itCart.hasNext()) {
                CartEntity cart = itCart.next();

                if(cart.getCompleted()){
                    tableContent[0] = String.valueOf(cart.getIdcart());
                    tableContent[1] = cart.getDeliveryAddress();
                    tableContent[2] = String.valueOf(cart.getDate());
                    tableContent[3] = "";
                    tableContent[4] = String.valueOf(cart.getTotalPrice());
                    tableContent[5] = String.valueOf(cart.getPaymentMethod());

                    List<CartItemEntity> cartItems = cartItemService.getCartItemsByCartId(cart.getIdcart());
                    Iterator<CartItemEntity> itCartItem = cartItems.iterator();
                    while(itCartItem.hasNext()){
                        CartItemEntity cartItem = itCartItem.next();
                        ProductEntity product = productService.getByProductId(cartItem.getIdproduct());
                        tableContent[3] += product.getName();
                    }
                }

                model.addRow(tableContent);
            }
            historyTable.setModel(model);
        }
    }

    public JPanel getPanel1(){
        return this.panel1;
    }

    public JButton getUpdateButton(){
        return this.updateButton;
    }

    public JButton getDeleteButton(){
        return this.deleteButton;
    }

    public JButton getEraseCartButton(){
        return this.eraseCartButton;
    }

    public JButton getOrderButton(){
        return this.orderButton;
    }

    public JButton getAddToCartButton(){
        return this.addToCartButton;
    }

    public JButton getDeleteFromCartButton(){
        return this.deleteFromCartButton;
    }

    public JTextField getNameField(){
        return this.nameField;
    }

    public JTextField getEmailField(){
        return this.emailField;
    }

    public JTextField getAddressField(){
        return this.addressField;
    }

    public JTextField getPasswordField(){
        return this.passwordField;
    }

    public JTextField getLoyaltyField(){
        return this.loyaltyField;
    }

    public JTextField getDeliveryField(){
        return this.deliveryField;
    }

    public JTextField getTotalPriceField(){
        return this.totalPriceField;
    }

    public JTextField getQuantityField(){
        return this.quantityField;
    }

    public JTextField getPriceField(){
        return this.priceField;
    }

    public JTextField getInterPriceField(){
        return this.interPriceField;
    }

    public JTextField getDeletePriceField(){
        return this.deletePriceField;
    }

    public String getPaymentCombo(){
        return this.paymentCombo;
    }

    public List<CartItemEntity> getCartItemList(){
        return this.cartItemList;
    }

    public CartEntity getActiveCart(){
        return this.activeCart;
    }

    public String getProductNameComboDelete(){
        return this.productNameComboDelete;
    }

    public String getProductNameComboUpdate(){
        return this.productNameComboUpdate;
    }

    public String getQuantityCombo(){
        return this.quantityCombo;
    }
}

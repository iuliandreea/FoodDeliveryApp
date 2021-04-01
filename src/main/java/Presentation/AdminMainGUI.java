package Presentation;

import Controller.AdminController;
import Model.CartEntity;
import Model.CartItemEntity;
import Service.CartItemService;
import Service.CartService;
import Service.CustomerService;
import Service.ProductService;
import Model.CustomerEntity;
import Model.ProductEntity;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;

public class AdminMainGUI {
    private JPanel panel1;
    private JPanel customers;
    private JTabbedPane tabbedPane1;
    private JPanel menu;
    private JTabbedPane tabbedPane2;
    private JPanel viewMenu;
    private JPanel updateMenu;
    private JPanel addToMenu;
    private JTabbedPane tabbedPane3;
    private JPanel viewCustomers;
    private JPanel editCustomers;
    private JPanel addCustomer;
    private JTable menuTable;
    private JLabel uProductLabel;
    private JComboBox uProductBox;
    private JLabel uProductPriceLabel;
    private JTextField uProductPriceField;
    private JLabel uProductDescLabel;
    private JTextField uProductDescField;
    private JButton updateProduct;
    private JButton deleteProduct;
    private JLabel aProductLabel;
    private JTextField aProductField;
    private JLabel aProductPriceLabel;
    private JTextField aProductPriceField;
    private JLabel aProductDescLabel;
    private JTextField aProductDescField;
    private JButton addProductButton;
    private JTable customersTable;
    private JLabel eCustNameLabel;
    private JLabel eCustEmailLabel;
    private JLabel eCustPassLabel;
    private JLabel eCustAddrLabel;
    private JLabel eCustLoyaltyLabel;
    private JTextField eCustNameField;
    private JTextField eCustEmailField;
    private JTextField eCustPassField;
    private JTextField eCustAddrField;
    private JCheckBox eCustLoyaltyBox;
    private JLabel eCustLabel;
    private JComboBox eCustBox;
    private JButton uConfirmButton;
    private JButton uDeleteButton;
    private JLabel aCustNameLabel;
    private JTextField aCustNameField;
    private JTextField aCustEmailField;
    private JLabel aCustEmailLabel;
    private JLabel aCustPassLabel;
    private JTextField aCustPassField;
    private JTextField aCustAddressField;
    private JLabel aCustAddressLabel;
    private JLabel aCustLoyaltyLabel;
    private JCheckBox aCustLoyaltyBox;
    private JButton aCustButton;
    private JLabel uProductNameLabel;
    private JTextField uProductNameField;
    private JLabel eCustDeletedLabel;
    private JTextField uProductisDeletedField;
    private JTextField eCustDeletedField;
    private JPanel report;
    private JLabel startDateLabel;
    private JLabel endDateLabel;
    private JTextField startDateField;
    private JTextField endDateField;
    private JButton showByDateButton;
    private JButton showAllButton;
    private JTable reportsTable;
    private JButton finalUpdateMenu;
    private JButton finalAddProduct;
    private JButton finalEditCustomer;
    private JButton finalAddCustomer;

    private ProductService productService = new ProductService();
    private CustomerService customerService = new CustomerService();
    private CartService cartService = new CartService();
    private CartItemService cartItemService = new CartItemService();
    private AdminController adminController = new AdminController();

    private String customerEmailCombo;
    private String menuNameCombo;

    public AdminMainGUI() {

        adminController.setAdminMainGUI(this);

        viewMenu();
        viewCustomers();

        viewCustomersCombo();
        viewMenuCombo();

        uProductBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(uProductBox.getSelectedIndex() != -1){
                    menuNameCombo = uProductBox.getSelectedItem().toString();
                    ProductEntity productEntity = productService.getByProductName(menuNameCombo);
                    uProductNameField.setText(productEntity.getName());
                    uProductPriceField.setText(String.valueOf(productEntity.getPrice()));
                    uProductDescField.setText(productEntity.getDescription());
                    uProductisDeletedField.setText(String.valueOf(productEntity.isDeleted()));
                }
            }
        });

        updateProduct.addActionListener(adminController);

        deleteProduct.addActionListener(adminController);
        eCustBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (eCustBox.getSelectedIndex() != -1) {
                    customerEmailCombo = eCustBox.getSelectedItem().toString();
                    CustomerEntity customerEntity = customerService.getCustomerByEmail(customerEmailCombo);
                    eCustNameField.setText(customerEntity.getName());
                    eCustEmailField.setText(customerEntity.getEmail());
                    eCustPassField.setText(customerEntity.getPassword());
                    eCustAddrField.setText(customerEntity.getAddress());
                    eCustLoyaltyBox.setSelected(customerEntity.isLoyalty());
                    eCustDeletedField.setText(String.valueOf(customerEntity.isDeleted()));
                }
            }
        });

        uConfirmButton.addActionListener(adminController);
        uDeleteButton.addActionListener(adminController);
        addProductButton.addActionListener(adminController);
        aCustButton.addActionListener(adminController);

        showByDateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showReports(false);
            }
        });
        showAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showReports(true);
            }
        });
        finalUpdateMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewMenuCombo();
                viewMenu();
            }
        });
        finalAddCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewCustomersCombo();
                viewCustomers();
            }
        });
        finalEditCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewCustomersCombo();
                viewCustomers();
            }
        });
        finalAddProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewMenuCombo();
                viewMenu();
            }
        });
    }

    public void viewMenuCombo(){
        uProductBox.removeAllItems();
        List<ProductEntity> menuList = productService.getAllProducts();
        if(!menuList.isEmpty()){
            Iterator<ProductEntity> it = menuList.iterator();
            while(it.hasNext()){
                ProductEntity p = it.next();
                if(!p.isDeleted()){
                    uProductBox.addItem(p.getName());
                }
            }
            menuNameCombo = uProductBox.getItemAt(0).toString();
        }
    }

    public void viewCustomersCombo(){
        eCustBox.removeAllItems();
        List<CustomerEntity> customerList = customerService.getAllCustomers();
        if(!customerList.isEmpty()){
            Iterator<CustomerEntity> it = customerList.iterator();
            while(it.hasNext()){
                CustomerEntity c = it.next();
                if(!c.isDeleted()){
                    eCustBox.addItem(c.getEmail());
                }

            }
            customerEmailCombo = eCustBox.getItemAt(0).toString();
        }
    }

    public void viewMenu(){
        List<ProductEntity> menuList = productService.getAllProducts();
        if(!menuList.isEmpty()){
            DefaultTableModel model = new DefaultTableModel(null, new String[] {"ID", "Name", "Description", "Price"});
            String[] tableContent = new String[4];
            Iterator<ProductEntity> it = menuList.iterator();

            while (it.hasNext()) {
                ProductEntity product = it.next();

                if(!product.isDeleted()){
                    tableContent[0] = String.valueOf(product.getIdproduct());
                    tableContent[1] = product.getName();
                    tableContent[2] = product.getDescription();
                    tableContent[3] = String.valueOf(product.getPrice());
                    model.addRow(tableContent);
                }
            }
            menuTable.setModel(model);
        }
    }

    public void viewCustomers(){
        List<CustomerEntity> customerList = customerService.getAllCustomers();
        if(!customerList.isEmpty()){
            DefaultTableModel model = new DefaultTableModel(null, new String[] {"ID", "Name", "Email", "Password", "Address", "Loyalty"});
            String[] tableContent = new String[6];
            Iterator<CustomerEntity> it = customerList.iterator();

            while (it.hasNext()) {
                CustomerEntity customer = it.next();

                if(!customer.isDeleted()){
                    tableContent[0] = String.valueOf(customer.getIdcustomer());
                    tableContent[1] = customer.getName();
                    tableContent[2] = customer.getEmail();
                    tableContent[3] = customer.getPassword();
                    tableContent[4] = customer.getAddress();
                    tableContent[5] = String.valueOf(customer.isLoyalty());
                    model.addRow(tableContent);
                }
            }
            customersTable.setModel(model);
        }
    }

    public void showReports(boolean all){
        List<CartEntity> carts = cartService.getAllCompletedCarts();
        if(!carts.isEmpty()){
            DefaultTableModel model = new DefaultTableModel(null, new String[] {"ID", "Email", "DeliveryAddress", "Products", "Payment Method", "Total Price"});
            String[] tableContent = new String[6];
            Iterator<CartEntity> it = carts.iterator();

            while(it.hasNext()){
                CartEntity c = it.next();

                if(!all){
                    LocalDate date = c.getDate();
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                    LocalDate start = LocalDate.parse(startDateField.getText(), dtf);
                    LocalDate end = LocalDate.parse(endDateField.getText(), dtf);

                    if(date.isBefore(start) || date.isAfter(end)){
                        continue;
                    }
                }

                tableContent[0] = String.valueOf(c.getIdcart());
                tableContent[2] = c.getDeliveryAddress();
                tableContent[4] = String.valueOf(c.getPaymentMethod());
                tableContent[5] = String.valueOf(c.getTotalPrice());

                CustomerEntity customer = customerService.getCustomerById(c.getIdclient());
                tableContent[1] = customer.getEmail();

                List<CartItemEntity> cartItems = cartItemService.getCartItemsByCartId(c.getIdcart());
                String products = "";
                Iterator<CartItemEntity> it2 = cartItems.iterator();
                while(it2.hasNext()){
                    CartItemEntity ci = it2.next();
                    ProductEntity p = productService.getByProductId(ci.getIdproduct());
                    products += (p.getName() + " ");
                }
                tableContent[3] = products;

                model.addRow(tableContent);
            }
            reportsTable.setModel(model);
        }
    }

    public JPanel getPanel1(){
        return this.panel1;
    }

    public JButton getUpdateProduct(){
        return this.updateProduct;
    }

    public JButton getDeleteProduct(){
        return this.deleteProduct;
    }

    public JButton getAddProductButton(){
        return this.addProductButton;
    }

    public JButton getuConfirmButton(){
        return this.uConfirmButton;
    }

    public JButton getuDeleteButton(){
        return this.uDeleteButton;
    }

    public JButton getaCustButton(){
        return this.aCustButton;
    }

    public JTextField getuProductPriceField(){
        return this.uProductPriceField;
    }

    public JTextField getuProductDescField(){
        return this.uProductDescField;
    }

    public JTextField getaProductField(){
        return this.aProductField;
    }

    public JTextField getaProductPriceField(){
        return this.aProductPriceField;
    }

    public JTextField getaProductDescField(){
        return this.aProductDescField;
    }

    public JTextField geteCustNameField(){
        return this.eCustNameField;
    }

    public JTextField geteCustEmailField(){
        return this.eCustEmailField;
    }

    public JTextField geteCustPassField(){
        return this.eCustPassField;
    }

    public JTextField geteCustAddrField(){
        return this.eCustAddrField;
    }

    public JTextField getaCustNameField(){
        return this.aCustNameField;
    }

    public JTextField getaCustEmailField(){
        return this.aCustEmailField;
    }

    public JTextField getaCustPassField(){
        return this.aCustPassField;
    }

    public JTextField getaCustAddressField(){
        return this.aCustAddressField;
    }

    public JTextField getuProductNameField(){
        return this.uProductNameField;
    }

    public JCheckBox geteCustLoyaltyBox(){
        return this.eCustLoyaltyBox;
    }

    public JCheckBox getaCustLoyaltyBox(){
        return this.aCustLoyaltyBox;
    }

    public JTextField getuProductisDeletedField(){
        return this.uProductisDeletedField;
    }

    public JTextField geteCustDeletedField(){
        return this.eCustDeletedField;
    }

    public String getMenuNameCombo(){
        return this.menuNameCombo;
    }

    public String getCustomerEmailCombo(){
        return this.customerEmailCombo;
    }
}

package Service;

import Model.CartEntity;
import Model.CartItemEntity;
import Model.CustomerEntity;
import Repository.CartItemRepository;
import Repository.CartRepository;
import Repository.CustomerRepository;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class CustomerService  {

    private CustomerRepository customerRepository;
    private CartRepository cartRepository;
    private CartItemRepository cartItemRepository;

    public CustomerService(){
        this.customerRepository = new CustomerRepository();
        this.cartRepository = new CartRepository();
        this.cartItemRepository = new CartItemRepository();
    }

    public int validateCustomer(String email, String password){
        CustomerEntity customerEntity = customerRepository.findByEmail(email);
        if(customerEntity != null && password.equals(customerEntity.getPassword())){
            return customerEntity.getIdcustomer();
        }
        return -1;
    }

    public List<CustomerEntity> getAllCustomers(){
        return customerRepository.findAll();
    }

    public CustomerEntity getCustomerByEmail(String email){
        return customerRepository.findByEmail(email);
    }

    public CustomerEntity getCustomerById(int id){
        return customerRepository.findById(id);
    }

    public String updateCustomerId(int id, String name, String email, String password, String address, boolean loyalty, boolean deleted){
        CustomerEntity customer = customerRepository.findById(id);
        CustomerEntity newCustomer = new CustomerEntity();
        newCustomer.setIdcustomer(customer.getIdcustomer());
        newCustomer.setName(name);
        newCustomer.setEmail(email);
        newCustomer.setPassword(password);
        newCustomer.setAddress(address);
        newCustomer.setLoyalty(loyalty);
        if(deleted){
            newCustomer.setDeleted(false);
        }

        String validator = customerRepository.validate(newCustomer);
        if (validator.equals("")) {
            customerRepository.update(newCustomer);
        }
        return validator;
    }

    public String updateCustomer(String oldEmail, String name, String email, String password, String address, boolean loyalty, boolean deleted){
        CustomerEntity customer = customerRepository.findByEmail(oldEmail);
        CustomerEntity newCustomer = new CustomerEntity();
        newCustomer.setIdcustomer(customer.getIdcustomer());
        newCustomer.setName(name);
        newCustomer.setEmail(email);
        newCustomer.setPassword(password);
        newCustomer.setAddress(address);
        newCustomer.setLoyalty(loyalty);
        if(deleted){
            newCustomer.setDeleted(false);
        }

        String validator = customerRepository.validate(newCustomer);
        if (validator.equals("")) {
            customerRepository.update(newCustomer);
        }
        return validator;
    }


    public void deleteCustomerId(int id){
        CustomerEntity customer = customerRepository.findById(id);
        customer.setDeleted(true);
        customerRepository.update(customer);

        CartEntity cart = cartRepository.findActiveByCustomerId(customer.getIdcustomer());
        cart.setDeleted(true);
        cartRepository.update(cart);

        List<CartItemEntity> cartItems = cartItemRepository.findByCartId(cart.getIdcart());
        for(CartItemEntity ci : cartItems){
            ci.setDeleted(true);
            cartItemRepository.update(ci);
        }
    }

    public void deleteCustomer(String email){
        CustomerEntity customer = customerRepository.findByEmail(email);
        customer.setDeleted(true);
        customerRepository.update(customer);

        CartEntity cart = cartRepository.findActiveByCustomerId(customer.getIdcustomer());
        cart.setDeleted(true);
        cartRepository.update(cart);

        List<CartItemEntity> cartItems = cartItemRepository.findByCartId(cart.getIdcart());
        for(CartItemEntity ci : cartItems){
            ci.setDeleted(true);
            cartItemRepository.update(ci);
        }
    }

    public String addCustomer(String name, String email, String password, String address, boolean loyalty){
        CustomerEntity customer = new CustomerEntity();
        customer.setName(name);
        customer.setEmail(email);
        customer.setPassword(password);
        customer.setAddress(address);
        customer.setLoyalty(loyalty);

        String validator = customerRepository.validate(customer);
        if (validator.equals("")) {
            customerRepository.save(customer);
        }
        return validator;
    }

}

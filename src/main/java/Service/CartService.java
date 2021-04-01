package Service;

import Model.CartEntity;
import Model.CartItemEntity;
import Model.CustomerEntity;
import Model.PaymentMethod;
import Repository.CartItemRepository;
import Repository.CartRepository;
import Repository.CustomerRepository;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

public class CartService  {

    private CartRepository cartRepository;
    private CartItemRepository cartItemRepository;
    private CustomerRepository customerRepository;

    public CartService(){
        this.cartRepository = new CartRepository();
        this.cartItemRepository = new CartItemRepository();
        this.customerRepository = new CustomerRepository();
    }

    public List<CartEntity> getAllCarts(){
        return cartRepository.findAll();
    }

    public List<CartEntity> getAllCompletedCarts(){
        return cartRepository.findAllComplete();
    }

    public List<CartEntity> getAllByCustomerId(int idcustomer){
        return cartRepository.findByCustomerId(idcustomer);
    }

    public CartEntity createCart(CartEntity cart){
        return cartRepository.save(cart);
    }

    public List<CartEntity> getAllCompletedByCustomerId(int idcustomer){
        return cartRepository.findCompleteByCustomerId(idcustomer);
    }

    public CartEntity getActiveCartByCustomerId(int idcustomer){
        return cartRepository.findActiveByCustomerId(idcustomer);
    }

    public String completeOrder(CartEntity cart, String address, String paymentMethod){
        cart.setDeliveryAddress(address);
        cart.setPaymentMethod(paymentMethod);

        CustomerEntity customer = customerRepository.findById(cart.getIdclient());
        if(customer.isLoyalty()){
            double previousPrice = cart.getTotalPrice();
            double newPrice = previousPrice - (previousPrice / 20);
            cart.setTotalPrice(newPrice);
        }

        cart.setDate(LocalDate.now());
        cart.setCompleted(true);

        String validator = cartRepository.validate(cart);
        if (validator.equals("")) {
            cartRepository.update(cart);
        }
        return validator;
    }

    public void deleteCart(CartEntity cart){
        List<CartItemEntity> cartItems = cartItemRepository.findByCartId(cart.getIdcart());
        for(CartItemEntity ci : cartItems){
            ci.setDeleted(true);
            cartItemRepository.update(ci);
        }
        cart.setDeleted(true);
        cartRepository.update(cart);
    }

}

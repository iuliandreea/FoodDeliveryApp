package Service;

import Model.CartEntity;
import Model.CartItemEntity;
import Model.CustomerEntity;
import Model.ProductEntity;
import Repository.CartItemRepository;
import Repository.CartRepository;
import Repository.ProductRepository;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CartItemService {

    private CartItemRepository cartItemRepository;
    private ProductRepository productRepository;
    private CartRepository cartRepository;

    public CartItemService(){
        this.cartItemRepository = new CartItemRepository();
        this.productRepository = new ProductRepository();
        this.cartRepository = new CartRepository();
    }

    public List<CartItemEntity> getAllCartItems(){
        return cartItemRepository.findAll();
    }

    public List<CartItemEntity> getCartItemsByCartId(int idcart){
        return cartItemRepository.findByCartId(idcart);
    }

    public String addToCart(CartEntity cart, String product, int quantity){
        CartItemEntity ci = new CartItemEntity();
        ProductEntity p = productRepository.findByName(product);

        ci.setIdcart(cart.getIdcart());
        ci.setIdproduct(p.getIdproduct());
        ci.setInterPrice(quantity * p.getPrice());
        ci.setQuantity(quantity);

        String validator = cartItemRepository.validate(ci);
        if (validator.equals("")) {
            cart.setTotalPrice(cart.getTotalPrice() + (quantity * p.getPrice()));
            cartItemRepository.save(ci);
            cartRepository.update(cart);
        }
        return validator;
    }

    public void deleteFromCart(CartEntity cart, String product){
        List<CartItemEntity> cartItems  = cartItemRepository.findByCartId(cart.getIdcart());
        ProductEntity p = productRepository.findByName(product);
        for(CartItemEntity ci : cartItems){
            if(ci.getIdproduct() == p.getIdproduct()){
                ci.setDeleted(true);
                cartItemRepository.update(ci);

                double newPrice = cart.getTotalPrice() - ci.getInterPrice();
                if(newPrice == 0){
                    cart.setDeleted(true);
                }
                cart.setTotalPrice(newPrice);
                cartRepository.update(cart);
            }
        }
    }
}

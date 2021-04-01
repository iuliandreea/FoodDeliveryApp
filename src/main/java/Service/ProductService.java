package Service;

import Model.ProductEntity;
import Repository.ProductRepository;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ProductService {

    ProductRepository productRepository;

    public ProductService(){
        this.productRepository = new ProductRepository();
    }

    public List<ProductEntity> getAllProducts(){
        return productRepository.findAll();
    }

    public ProductEntity getByProductId(int idproduct){
        return productRepository.findById(idproduct);
    }

    public ProductEntity getByProductName(String name){
        return productRepository.findByName(name);
    }

    public String updateProduct(String initialName, String finalName, Double price, String desc, boolean deleted){
        ProductEntity product = productRepository.findByName(initialName);
        ProductEntity newProduct = new ProductEntity();
        newProduct.setIdproduct(product.getIdproduct());
        newProduct.setName(finalName);
        newProduct.setPrice(price);
        newProduct.setDescription(desc);
        if(deleted){
            newProduct.setDeleted(false);
        }

        String validator = productRepository.validate(newProduct);
        if(validator.equals("")){
            productRepository.update(newProduct);
        }
        return validator;
    }

    public void deleteProduct(String name){
        ProductEntity product = productRepository.findByName(name);
        product.setDeleted(true);
        productRepository.update(product);
    }

    public String addProduct(String name, Double price, String desc){
        ProductEntity product = new ProductEntity();
        product.setName(name);
        product.setPrice(price);
        product.setDescription(desc);

        String validator = productRepository.validate(product);
        if(validator.equals("")){
            productRepository.save(product);
        }
        return validator;
    }

}

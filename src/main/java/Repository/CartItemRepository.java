package Repository;

import App.HibernateService;
import Model.CartEntity;
import Model.CartItemEntity;
import Model.CustomerEntity;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Set;

public class CartItemRepository implements Repository<CartItemEntity> {
    @Override
    public CartItemEntity save(CartItemEntity entity) {
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        databaseSession.beginTransaction();
        databaseSession.saveOrUpdate(entity);
        databaseSession.save(entity);
        databaseSession.flush();
        databaseSession.getTransaction().commit();
        return entity;
    }

    @Override
    public CartItemEntity update(CartItemEntity entity) {
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        databaseSession.beginTransaction();

        Query query = databaseSession.createQuery("UPDATE CartItemEntity X SET X.idcart = :idcart, X.idproduct = :idproduct, " +
                "X.quantity = :quantity, X.interPrice = :interPrice, X.deleted = :deleted WHERE X.idcartItem = :idcartItem");

        query.setParameter("idcartItem", entity.getIdcartItem());
        query.setParameter("idcart", entity.getIdcart());
        query.setParameter("idproduct", entity.getIdproduct());
        query.setParameter("quantity", entity.getQuantity());
        query.setParameter("interPrice", entity.getInterPrice());
        query.setParameter("deleted", entity.isDeleted());

        query.executeUpdate();
        databaseSession.flush();
        databaseSession.getTransaction().commit();
        return entity;
    }

    @Override
    public CartItemEntity findById(int idcartItem) {
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        Query query = databaseSession.createQuery("FROM CartItemEntity X WHERE X.idcartItem = :idcartItem");
        query.setParameter("idcartItem", idcartItem);
        List<CartItemEntity> result = query.list();
        if (!result.isEmpty()) {
            return result.get(0);
        }
        return null;
    }

    public List<CartItemEntity> findByCartId(int idcart) {
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        Query query = databaseSession.createQuery("FROM CartItemEntity X WHERE X.idcart = :idcart");
        query.setParameter("idcart", idcart);
        List<CartItemEntity> result = query.list();
        return result;
    }

    @Override
    public List<CartItemEntity> findAll() {
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        Query query = databaseSession.createQuery("FROM CartItemEntity");
        List<CartItemEntity> result = query.list();
        return result;
    }

    @Override
    public boolean delete(CartItemEntity entity) {
        return false;
    }

    @Override
    public String validate(CartItemEntity entity) {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<CartItemEntity>> constraintViolations = validator.validate(entity);
        String result = "";
        if(constraintViolations.size() > 0) {
            for(ConstraintViolation<CartItemEntity> message : constraintViolations){
                result += message.getMessageTemplate() + "\n";
            }
        }
        return result;
    }
}

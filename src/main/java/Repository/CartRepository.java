package Repository;

import App.HibernateService;
import Model.CartEntity;
import Model.CustomerEntity;
import Model.ProductEntity;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Set;

public class CartRepository implements Repository<CartEntity> {

    @Override
    public CartEntity save(CartEntity entity) {
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        databaseSession.beginTransaction();

        databaseSession.saveOrUpdate(entity);
        databaseSession.save(entity);
        databaseSession.flush();
        databaseSession.getTransaction().commit();
        return entity;
    }

    @Override
    public CartEntity update(CartEntity entity) {
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        databaseSession.beginTransaction();

        Query query = databaseSession.createQuery("UPDATE CartEntity X SET X.idclient = :idclient, X.deliveryAddress = :deliveryAddress, " +
                "X.totalPrice = :totalPrice, X.date = :date, X.completed = :completed, X.deleted = :deleted, X.paymentMethod = :paymentMethod WHERE X.idcart = :idcart");

        query.setParameter("idcart", entity.getIdcart());
        query.setParameter("idclient", entity.getIdclient());
        query.setParameter("deliveryAddress", entity.getDeliveryAddress());
        query.setParameter("totalPrice", entity.getTotalPrice());
        query.setParameter("date", entity.getDate());
        query.setParameter("completed", entity.getCompleted());
        query.setParameter("deleted", entity.getDeleted());
        query.setParameter("paymentMethod", entity.getPaymentMethod());

        query.executeUpdate();
        databaseSession.flush();
        databaseSession.getTransaction().commit();
        return entity;
    }

    @Override
    public CartEntity findById(int idcart) {
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        Query query = databaseSession.createQuery("FROM CartEntity X WHERE X.idcart = :idcart");
        query.setParameter("idcart", idcart);
        List<CartEntity> result = query.list();
        if (!result.isEmpty()) {
            return result.get(0);
        }
        return null;
    }

    public List<CartEntity> findByCustomerId(int idclient) {
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        Query query = databaseSession.createQuery("FROM CartEntity X WHERE X.idclient = :idclient");
        query.setParameter("idclient", idclient);
        List<CartEntity> result = query.list();
        return result;
    }

    public List<CartEntity> findCompleteByCustomerId(int idclient) {
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        Query query = databaseSession.createQuery("FROM CartEntity X WHERE X.idclient = :idclient AND X.completed = true");
        query.setParameter("idclient", idclient);
        List<CartEntity> result = query.list();
        return result;
    }

    public CartEntity findActiveByCustomerId(int idclient){
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        Query query = databaseSession.createQuery("FROM CartEntity X WHERE X.idclient = :idclient AND X.completed = false AND X.deleted = false");
        query.setParameter("idclient", idclient);
        List<CartEntity> result = query.list();
        if (!result.isEmpty()) {
            return result.get(0);
        }
        return null;
    }

    @Override
    public List<CartEntity> findAll() {
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        Query query = databaseSession.createQuery("FROM CartEntity");
        List<CartEntity> result = query.list();
        return result;
    }

    public List<CartEntity> findAllComplete() {
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        Query query = databaseSession.createQuery("FROM CartEntity X WHERE X.completed = true");
        List<CartEntity> result = query.list();
        return result;
    }

    @Override
    public boolean delete(CartEntity entity) {
        return false;
    }

    @Override
    public String validate(CartEntity entity) {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<CartEntity>> constraintViolations = validator.validate(entity);
        String result = "";
        if(constraintViolations.size() > 0) {
            for(ConstraintViolation<CartEntity> message : constraintViolations){
                result += message.getMessageTemplate() + "\n";
            }
        }
        return result;
    }
}

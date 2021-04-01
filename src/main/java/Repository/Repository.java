package Repository;


import java.util.List;

public interface Repository<T> {

    T save (T entity);
    T update(T entity);
    T findById(int id);
    List<T> findAll();
    boolean delete(T entity);
    public String validate(T entity);
}

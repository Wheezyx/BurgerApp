package burgerapp.components.generic;

import java.util.List;
import java.util.Optional;

public interface GenericDao<E, K>
{
    void add(E entity);
    
    void saveOrUpdate(E entity);
    
    void update(E entity);
    
    void remove(E entity);
    
    Optional<E> find(K key);
    
    Optional<List<E>> getAll();
}

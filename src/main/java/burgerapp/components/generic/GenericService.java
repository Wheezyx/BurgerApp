package burgerapp.components.generic;

import burgerapp.components.order.Order;

import java.util.List;
import java.util.Optional;

public interface GenericService<E, K>
{
    void saveOrUpdate(E entity);
    
    Optional<List<E>> getAll();
    
    Optional<E> get(K id);
    
    Order add(E entity);
    
    void update(E entity);
    
    void remove(E entity);
}

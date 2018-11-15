package burgerapp.components.generic;

import java.util.List;
import java.util.Optional;

public interface GenericService<E, K>
{
    boolean saveOrUpdate(E entity);
    
    Optional<List<E>> getAll();
    
    Optional<E> get(K id);
    
    boolean add(E entity);
    
    boolean update(E entity);
    
    void remove(E entity);
}

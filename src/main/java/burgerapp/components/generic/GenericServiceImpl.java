package burgerapp.components.generic;

import burgerapp.components.order.Order;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public abstract class GenericServiceImpl<E, K> implements GenericService<E, K>
{
    private GenericDao<E, K> genericDao;
    
    public GenericServiceImpl(GenericDao<E, K> genericDao)
    {
        this.genericDao = genericDao;
    }
    
    public void saveOrUpdate(E entity)
    {
        genericDao.saveOrUpdate(entity);
    }
    
    public Optional<List<E>> getAll()
    {
        return genericDao.getAll();
    }
    
    public Optional<E> get(K id)
    {
        return genericDao.find(id);
    }
    
    public Order add(E entity)
    {
        genericDao.add(entity);
        return null;
    }
    
    public void update(E entity)
    {
        genericDao.update(entity);
    }
    
    public void remove(E entity)
    {
        genericDao.remove(entity);
    }
}
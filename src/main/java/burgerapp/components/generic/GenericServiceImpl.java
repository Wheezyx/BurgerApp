package burgerapp.components.generic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public abstract class GenericServiceImpl<E, K> implements GenericService<E, K>
{
    private final GenericDao<E, K> genericDao;
    
    public GenericServiceImpl(GenericDao<E, K> genericDao)
    {
        this.genericDao = genericDao;
    }
    
    public boolean saveOrUpdate(E entity)
    {
        try
        {
            genericDao.saveOrUpdate(entity);
            return true;
        }
        catch(ConstraintViolationException | PersistenceException e)
        {
            log.error(e.getMessage());
            return false;
        }
    }
    
    public Optional<List<E>> getAll()
    {
        return genericDao.getAll();
    }
    
    @Transactional
    public Optional<E> get(K id)
    {
        return genericDao.find(id);
    }
    
    public boolean add(E entity)
    {
        try
        {
            genericDao.add(entity);
            log.info("Successful created and saved " + entity.getClass().getSimpleName());
            return true;
        }
        catch(ConstraintViolationException | PersistenceException e)
        {
            log.error(e.getMessage());
            return false;
        }
    }
    
    public boolean update(E entity)
    {
        try
        {
            genericDao.update(entity);
            return true;
        }
        catch(ConstraintViolationException | PersistenceException e)
        {
            log.error(e.getMessage());
            return false;
        }
    }
    
    public void remove(E entity)
    {
        genericDao.remove(entity);
    }
}
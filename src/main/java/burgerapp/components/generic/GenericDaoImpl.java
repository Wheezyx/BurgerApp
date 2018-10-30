package burgerapp.components.generic;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

@Repository
@SuppressWarnings("unchecked")
public abstract class GenericDaoImpl<E, K extends Serializable> implements GenericDao<E, K>
{
    private Class<? extends E> daoType;
    @PersistenceContext
    protected EntityManager entityManager;
    
    public GenericDaoImpl()
    {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        daoType = (Class) pt.getActualTypeArguments()[0];
    }
    
    @Override
    @Transactional
    public void add(E entity)
    {
        entityManager.persist(entity);
    }
    
    @Override
    @Transactional
    public void saveOrUpdate(E entity)
    {
        entityManager.merge(entity);
    }
    
    @Override
    @Transactional
    public void update(E entity)
    {
        entityManager.merge(entity);
    }
    
    @Override
    public void remove(E entity)
    {
        entityManager.remove(entity);
    }
    
    @Override
    public Optional<E> find(K key)
    {
        return Optional.ofNullable(entityManager.find(daoType, key));
    }
    
    @Override
    public Optional<List<E>> getAll()
    {
        return Optional.ofNullable(entityManager.createQuery("from " + daoType.getName()).getResultList());
    }
}

package burgerapp.components;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public abstract class AbstractJpaRepository<T extends Serializable>
{
    private Class<T> clazz;
    
    @PersistenceContext
    protected EntityManager entityManager;
    
    public void setClazz(Class<T> clazzToSet)
    {
        this.clazz = clazzToSet;
    }
    
    public Optional<T> findOne(Long id)
    {
        return Optional.ofNullable(entityManager.find(clazz, id));
    }
    
    public List<T> findAll()
    {
        return entityManager.createQuery("SELECT b FROM " + clazz.getSimpleName() + " b", clazz).getResultList();
    }
    
    public void save(T entity)
    {
        entityManager.persist(entity);
    }
    
    public void update(T entity)
    {
        entityManager.merge(entity);
    }
    
    public void delete(T entity)
    {
        entityManager.remove(entity);
    }
    
    public void deleteById(Long entityId)
    {
        Optional<T> entity = findOne(entityId);
        entity.ifPresent(this::delete);
    }
}

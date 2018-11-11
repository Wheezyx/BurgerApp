package burgerapp.components.burger;

import burgerapp.components.generic.GenericDaoImpl;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class BurgerDaoImpl extends GenericDaoImpl<Burger, Long> implements BurgerDao
{
    public Optional<Burger> findByName(String name)
    {
        TypedQuery<Burger> query = entityManager.createQuery("SELECT b FROM Burger b WHERE b.name LIKE :name", Burger.class);
        return Optional.ofNullable(DataAccessUtils.singleResult(query.setParameter("name", name).getResultList()));
    }
}

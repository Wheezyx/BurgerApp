package burgerapp.components.burger;

import burgerapp.components.generic.GenericDao;
import burgerapp.components.generic.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BurgerServiceImpl extends GenericServiceImpl<Burger, Long> implements BurgerService
{
    private BurgerDao burgerDao;
    
    public BurgerServiceImpl(
        @Qualifier("burgerDaoImpl") GenericDao<Burger, Long> genericDao)
    {
        super(genericDao);
        this.burgerDao = (BurgerDao) genericDao;
    }
    
    @Override
    public Optional<Burger> findByName(String name)
    {
        return burgerDao.findByName(name);
    }
}

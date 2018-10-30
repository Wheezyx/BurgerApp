package burgerapp.components.burger;

import burgerapp.components.generic.GenericDao;

import java.util.Optional;

public interface BurgerDao extends GenericDao<Burger, Long>
{
    Optional<Burger> findByName(String name);
}

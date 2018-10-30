package burgerapp.components.burger;

import burgerapp.components.generic.GenericService;

import java.util.Optional;

public interface BurgerService extends GenericService<Burger, Long>
{
    Optional<Burger> findByName(String name);
}

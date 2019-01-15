package burgerapp.components.user;

import burgerapp.components.generic.GenericDao;

import java.util.List;
import java.util.Optional;

public interface UserDao extends GenericDao<User, Long>
{
    Optional<User> findByEmail(String email);
    
    UserRole findByRole(String role);

    Optional<List<User>> findAllByRole(String role);
}

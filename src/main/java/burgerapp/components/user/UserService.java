package burgerapp.components.user;

import burgerapp.components.generic.GenericService;

public interface UserService extends GenericService<User, Long>
{
    boolean addWithDefaultRole(User user);
}

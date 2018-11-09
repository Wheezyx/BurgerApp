package burgerapp.components.user;

import burgerapp.components.generic.GenericService;

public interface UserService extends GenericService<User, Long>
{
    void addWithDefaultRole(User user);
}

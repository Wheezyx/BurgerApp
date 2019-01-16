package burgerapp.components.user;

import burgerapp.components.generic.GenericService;

import java.util.List;
import java.util.Optional;

public interface UserService extends GenericService<User, Long> {
    boolean addWithDefaultRole(User user);

    Optional<List<User>> findWithDefaultRole(String role);

    boolean changeUserEnabledById(String id);
}

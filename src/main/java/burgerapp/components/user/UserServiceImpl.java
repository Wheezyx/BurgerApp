package burgerapp.components.user;

import burgerapp.components.generic.GenericDao;
import burgerapp.components.generic.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl extends GenericServiceImpl<User, Long> implements UserService {
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private static final String DEFAULT_ROLE = "ROLE_USER";

    public UserServiceImpl(
            @Qualifier("userDaoImpl") GenericDao<User, Long> genericDao, PasswordEncoder passwordEncoder) {
        super(genericDao);
        this.userDao = (UserDao) genericDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean addWithDefaultRole(User user) {
        UserRole userRole = userDao.findByRole(DEFAULT_ROLE);
        user.getRoles().add(userRole);
        user.setEnabled(true);
        String passwordHash = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordHash);
        try {
            userDao.add(user);
            return true;
        } catch (ConstraintViolationException | PersistenceException e) {
            return false;
        }
    }

    @Override
    public Optional<List<User>> findWithDefaultRole(String role) {
        role = "ROLE_" + role.toUpperCase();
        return userDao.findAllByRole(role);
    }

    @Override
    public boolean changeUserEnabledById(String id) {
        try {
            Long idl = Long.valueOf(id);
            Optional<User> optionalUser = this.userDao.find(idl);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                user.setEnabled(!user.isEnabled());
                this.userDao.update(user);
                return true;
            } else {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

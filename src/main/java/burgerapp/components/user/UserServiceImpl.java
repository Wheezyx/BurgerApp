package burgerapp.components.user;

import burgerapp.components.generic.GenericDao;
import burgerapp.components.generic.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;

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

}

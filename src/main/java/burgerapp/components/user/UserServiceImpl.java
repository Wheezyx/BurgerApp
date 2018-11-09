package burgerapp.components.user;

import burgerapp.components.generic.GenericDao;
import burgerapp.components.generic.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends GenericServiceImpl<User, Long> implements UserService
{
    private UserDao userDao;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private static final String DEFAULT_ROLE = "ROLE_ADMIN";
    
    public UserServiceImpl(
        @Qualifier("userDaoImpl") GenericDao<User, Long> genericDao)
    {
        super(genericDao);
        this.userDao = (UserDao) genericDao;
    }
    
    @Override
    public void addWithDefaultRole(User user)
    {
        UserRole userRole = userDao.findByRole(DEFAULT_ROLE);
        user.getRoles().add(userRole);
        String passwordHash = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordHash);
        userDao.add(user);
    }
}
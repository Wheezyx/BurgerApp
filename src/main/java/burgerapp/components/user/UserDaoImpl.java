package burgerapp.components.user;

import burgerapp.components.generic.GenericDaoImpl;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class UserDaoImpl extends GenericDaoImpl<User, Long> implements UserDao
{
    
    @Override
    public Optional<User> findByEmail(String email)
    {
        TypedQuery<User> query = this.entityManager.createQuery("SELECT u FROM User u WHERE u.email LIKE :email", User.class);
        return Optional.ofNullable(DataAccessUtils.singleResult(query.setParameter("email", email).getResultList()));
    }
    
    @Override
    public UserRole findByRole(String role)
    {
        TypedQuery<UserRole> query = this.entityManager.createQuery("SELECT ur FROM UserRole ur WHERE ur.role LIKE :role", UserRole.class);
        return query.setParameter("role", role).getSingleResult();
    }
    
}

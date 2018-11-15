package burgerapp.components.user;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Component
public class CustomUserDetailsService implements UserDetailsService
{
    private UserDao userDao;
    
    @Override
    public UserDetails loadUserByUsername(String username)
    {
        Optional<User> optionalUser = userDao.findByEmail(username);
        if(!optionalUser.isPresent())
        {
            throw new UsernameNotFoundException("User not found");
        }
        User user = optionalUser.get();
        return new org.springframework.security.core.userdetails.User(
            user.getEmail(),
            user.getPassword(),
            user.isEnabled(),
            true,
            true,
            user.isEnabled(),
            convertAuthorities(user.getRoles())
        );
    }
    
    private List<GrantedAuthority> convertAuthorities(List<UserRole> userRoles)
    {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for(UserRole ur : userRoles)
        {
            authorities.add(new SimpleGrantedAuthority(ur.getRole()));
        }
        return authorities;
    }
}

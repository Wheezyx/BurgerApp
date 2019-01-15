package burgerapp.components.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
//TODO RENAME ENTITIES TO CORRECT/PROPER NAMES
public
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    @Column(unique = true)
    private String email;

    private boolean enabled;
    @NotEmpty
    private String password;
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private List<UserRole> roles = new ArrayList<>();

    public User(@NotEmpty String email, @NotEmpty String password) {
        this.email = email;
        this.password = password;
    }
}

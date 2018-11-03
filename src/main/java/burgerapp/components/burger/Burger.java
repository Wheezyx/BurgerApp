package burgerapp.components.burger;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
public class Burger implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private Double price;
    @NotNull
    @NotBlank
    private String name;
    @Column(name = "short_description")
    @NotNull
    private String shortDescription;
    @Column(length = 512)
    private String description;
}

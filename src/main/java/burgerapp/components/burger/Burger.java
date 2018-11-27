package burgerapp.components.burger;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
public class Burger implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @NotNull(message = "Nie może być puste")
    @Min(value = 1, message = "Wartość musi być większa niż 1")
    @Max(value = 1000, message = "Wartość musi być mniejsza niz 1000")
    private Double price;
    @NotBlank(message = "Nazwa nie może być pusta")
    @Column(unique = true)
    @Size(min = 3, max = 25)
    private String name;
    @Size(max = 90, min = 10, message = "Krótki opis musi zawierac od 10 do 90 znaków")
    @Column(name = "short_description")
    private String shortDescription;
    @Size(max = 90, min = 10, message = "Długi opis musi zawierac od 10 do 512 znaków")
    @Column(length = 512)
    private String description;
}

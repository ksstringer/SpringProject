package org.example.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
public class Seller {
    private String name;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @OneToMany
    @JoinColumn(name="seller_fk")
    public List<Product> products;

    @Override
    public String toString() {
        return "Seller{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", products=" + products +
                '}';
    }
}

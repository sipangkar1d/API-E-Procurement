package com.enigma.procurement.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jdk.jfr.Enabled;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "m_product")
public class Product extends BaseEntity<String> {
    @Id
    @GenericGenerator(strategy = "uuid2", name = "uuid")
    @GeneratedValue(generator = "uuid")
    @Column(name = "product_id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "product_code")
    private String productCode;

    @ManyToOne()
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<ProductPrice> productPrices;

    @Column(name = "is_active")
    private Boolean isActive;
}

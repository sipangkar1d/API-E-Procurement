package com.enigma.procurement.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "m_warehouse")
public class Warehouse {
    @Id
    @GenericGenerator(strategy = "uuid2", name = "uuid")
    @GeneratedValue(generator = "uuid")
    @Column(name = "warehouse_id")
    private String id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Product product;
    private Integer quantity;
}

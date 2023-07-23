package com.enigma.procurement.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_order_detail")
public class OrderDetail {
    @Id
    @GenericGenerator(strategy = "uuid2" , name = "uuid")
    @GeneratedValue(generator = "uuid")
    @Column(name = "order_detail_id")
    private String id;

    @ManyToOne()
    @JoinColumn(name = "product_price_id")
    private ProductPrice productPrice;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonManagedReference
    private Order order;


    @Column(name = "quantity")
    private Integer quantity;
}

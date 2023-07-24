package com.enigma.procurement.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


@Builder(toBuilder = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "m_admin")
public class Admin extends BaseEntity<String>{
    @Id
    @GenericGenerator(strategy = "uuid2" , name = "uuid")
    @GeneratedValue(generator = "uuid")
    @Column(name = "admin_id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @OneToOne
    @JoinColumn(name = "user_credential_id")
    private UserCredential userCredential;
}

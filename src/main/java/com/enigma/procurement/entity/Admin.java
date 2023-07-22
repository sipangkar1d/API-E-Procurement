package com.enigma.procurement.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "m_admin")
public class Admin {
    @Id
    @GenericGenerator(strategy = "uuid2" , name = "uuid")
    @GeneratedValue(generator = "uuid")
    @Column(name = "admin_id")
    private String id;

    @Column(name = "name")
    String name;
    @Column(name = "email")
    String email;

    @OneToOne
    @JoinColumn(name = "user_credential_id")
    private UserCredential userCredential;


}

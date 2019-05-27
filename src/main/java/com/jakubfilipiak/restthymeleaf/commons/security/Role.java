package com.jakubfilipiak.restthymeleaf.commons.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Jakub Filipiak on 09.05.2019.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private int roleId;

    private String role;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private Set<UserApp> users = new HashSet<>();
}

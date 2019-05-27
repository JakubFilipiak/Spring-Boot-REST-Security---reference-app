package com.jakubfilipiak.restthymeleaf.commons.security;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Jakub Filipiak on 09.05.2019.
 */
@NoArgsConstructor
@Getter
@Entity
@Table(name = "user", schema = "public")
// jeśli nie da się utworzyć tabeli user, bo już istnieje
// to po przecinku trzeba podać schema = "public" i będzie ok
public class UserApp {

    // Nazywamy tak, a nie User, bo SpringSecurity posiada swoją klasę User i się zesra

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;

    private String name;
    private String password;
    // nie robimy booleana, bo postgres i tak przyjmuje 0 i 1
    // później łatwiej z przesiadką na mysql chociażby, int jest uniwersalny
    private int active;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    // Kaskadowość to połączenie encji ze sobą poprzez klucze obce.
    // Co się dzieje z głównym to się dieje też z tym, co ma klucz obcy.
    // fetch - ładowanie pliku: lazy - leniwie - ładowane są tylko te pola,
    // które są wymagane. eager - zachłannie - cały użytkownik na raz
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public UserApp(UserApp userApp) {
        this.name = userApp.getName();
        this.password = userApp.getPassword();
        this.active = userApp.getActive();
        this.roles = userApp.getRoles();
    }
}

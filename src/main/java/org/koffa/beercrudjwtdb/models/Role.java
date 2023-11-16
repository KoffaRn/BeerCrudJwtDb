package org.koffa.beercrudjwtdb.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
@Entity
@Data
@NoArgsConstructor
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue
    @Column(name = "role_id")
    private Long id;
    private String authority;
    public Role(String authority) {
        this.authority = authority;
    }
}
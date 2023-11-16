package org.koffa.beercrudjwtdb.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Beer {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String type;
    private String brewery;
}
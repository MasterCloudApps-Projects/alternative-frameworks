package org.acme.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Student extends PanacheEntityBase {

    @Id
    private String id;
    private String name;
    private Long studentNumber;
    private String email;

}

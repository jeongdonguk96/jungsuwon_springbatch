package io.spring.springbatch.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Customer2 {
    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private String birthDate;
}

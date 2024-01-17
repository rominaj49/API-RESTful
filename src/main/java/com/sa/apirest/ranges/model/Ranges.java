package com.sa.apirest.ranges.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "ranges")
public class Ranges extends Base{
    
    @NotNull(message = "El rango  no puede estar en blanco")
    private String ranges;

}

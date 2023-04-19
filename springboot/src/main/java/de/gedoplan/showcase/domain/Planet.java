package de.gedoplan.showcase.domain;


import de.gedoplan.baselibs.entity.StringIdEntity;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Planet extends StringIdEntity {

    private String name;
    private double mass;


    public Planet(String id, String name, double mass) {
        super(id);
        this.name = name;
        this.mass = mass;
    }

}

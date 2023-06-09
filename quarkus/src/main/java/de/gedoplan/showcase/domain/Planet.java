package de.gedoplan.showcase.domain;

import javax.json.bind.annotation.JsonbCreator;
import javax.persistence.Entity;

import de.gedoplan.baselibs.panache.entity.StringIdPanacheEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class Planet extends StringIdPanacheEntity {
  
  private String name;
  private double mass;

  @JsonbCreator
  public Planet(String id, String name, double mass) {
    super(id);
    this.name = name;
    this.mass = mass;
  }

}

package de.gedoplan.baselibs.panache.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Basisklasse für Entities mit einer generierten Integer-Id.
 *
 * Entities im Sinne dieser Klasse haben genau ein Integer-Schlüsselattribut,
 * dessen Wert vom EntityManager automatisch vergeben wird.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class GeneratedIntegerIdPanacheEntity extends SingleIdPanacheEntity<Integer> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected Integer id;

  @Override
  public Integer getId() {
    return this.id;
  }

  /**
   * Id setzen.
   *
   * Eine bereits gesetzte Id (d. h. <code>!=null</code>) darf nicht überschrieben werden.
   *
   * @param id
   *          Id-Wert
   */
  public void setId(Integer id) {
    if (this.id != null) {
      if (this.id.equals(id)) {
        return;
      }

      throw new IllegalStateException("Generated id must not be changed");
    }

    this.id = id;
  }
}

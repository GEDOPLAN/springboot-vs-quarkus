package de.gedoplan.baselibs.panache.entity;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

/**
 * Basisklasse für Entities mit einer generierten Long-Id.
 *
 * Entities im Sinne dieser Klasse haben genau ein Long-Schlüsselattribut,
 * dessen Wert vom EntityManager automatisch vergeben wird.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class GeneratedLongIdPanacheEntity extends SingleIdPanacheEntity<Long> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected Long id;

  @Override
  public Long getId() {
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
  public void setId(Long id) {
    if (this.id != null) {
      if (this.id.equals(id)) {
        return;
      }

      throw new IllegalStateException("Generated id must not be changed");
    }

    this.id = id;
  }
}

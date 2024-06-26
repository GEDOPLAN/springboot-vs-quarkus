package de.gedoplan.baselibs.panache.entity;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

/**
 * Basisklasse für Entities mit einer String-Id.
 *
 * Entities im Sinne dieser Klasse haben genau ein String-basiertes Schlüsselattribut.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class StringIdPanacheEntity extends SingleIdPanacheEntity<String> {

  /**
   * Id.
   */
  @Id
  protected String id;

  /**
   * Konstruktor.
   */
  protected StringIdPanacheEntity() {
  }

  /**
   * Konstruktor.
   *
   * @param id
   *          Id.
   */
  protected StringIdPanacheEntity(String id) {
    this.id = id;
  }

  /**
   * Id liefern.
   *
   * @return Id.
   */
  @Override
  public String getId() {
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
  public void setId(String id) {
    if (this.id != null) {
      if (this.id.equals(id)) {
        return;
      }

      throw new IllegalStateException("Generated id must not be changed");
    }

    this.id = id;
  }
}

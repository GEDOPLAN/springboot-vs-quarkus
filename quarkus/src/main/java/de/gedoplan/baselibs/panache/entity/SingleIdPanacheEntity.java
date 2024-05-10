package de.gedoplan.baselibs.panache.entity;

import de.gedoplan.baselibs.utils.exception.BugException;
import de.gedoplan.baselibs.utils.util.ClassUtil;
import de.gedoplan.baselibs.utils.util.MethodUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.Objects;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jakarta.persistence.*;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

/**
 * Basisklasse für Entities mit einem einzelnen ID-Attribut.
 *
 * @param <I>
 *          Typ des ID-Attributs
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class SingleIdPanacheEntity<I> extends PanacheEntityBase {

  /**
   * Id liefern.
   *
   * @return Id.
   */
  public abstract I getId();

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    return Objects.hash(getId());
  }

  /**
   * Vergleich dieses Objektes mit einem anderen.
   *
   * Zwei Objekte vom Typ SingleKeyEntity werden als gleich eingestuft, wenn sie gleichen Typs sind und ihre Ids gesetzt und
   * gleich sind.
   *
   * Achtung: Ist die Id mindestens eines der beiden Objekte <code>null</code>, werden die Objekte als 'ungleich' erachtet. Die
   * Annahme ist, dass die Id einer Entity nur dann <code>null</code> ist, wenn sie eine generierte Id besitzt und noch nicht
   * persistiert wurde. In diesem Fall wird wahrscheinlich kein Vergleich mit einem inhaltsgleichen Objekt durchgeführt, so dass
   * <code>false</code> als Ergebnis sinnvoll erscheint.
   *
   * @param obj
   *          Vergleichsobjekt
   * @return <code>true</code> bei Gleichheit
   *
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      if (ClassUtil.getProxiedClass(getClass()) != ClassUtil.getProxiedClass(obj.getClass())) {
        return false;
      }
    }

    @SuppressWarnings("unchecked")
    final SingleIdPanacheEntity<I> other = (SingleIdPanacheEntity<I>) obj;

    I thisId = getId();
    return thisId != null && Objects.equals(thisId, other.getId());
  }

  /**
   * Objekt in String ausgeben in der Form type{key=value,...}.
   *
   * Diese Conveniance-Methode entspricht {@link #toString(boolean) toString(false}.
   */
  @Override
  public String toString() {
    return toString(false);
  }

  /**
   * Objekt in String ausgeben in der Form type{key=value,...}.
   *
   * <p>
   * Es wird ein String der Form <code>type{key=value,...}</code> erzeugt, wobei <code>type</code> der einfache Klassenname ist. Die
   * ID-Attribute werden - soweit vorhanden - als erstes angegeben. Ist <code>idOnly</code> <code>false</code>, folgen die weiteren
   * Attribute des Objektes.
   *
   * <p>
   * Es werden nur persistente (JPA-) Attribute dieser Datentypen berücksichtigt: Primitive Typen und ihre Wrapper, temporale Typen, Strings und Embeddables.
   *
   * <p>
   * Die Attributwerte werden per Reflection ermittelt. Transiente oder mit <code>@Transient</code> markierte Attribute werden nicht berücksichtigt.
   *
   * @param idOnly
   *          <code>true</code>, wenn nur ID-Attribute berücksichtigt werden sollen
   * @return String-Repräsentation des Objektes
   */
  public String toString(boolean idOnly) {

    SortedMap<String, PropertyMeta> propertyMap = CLASS_PROPERTY_MAP.computeIfAbsent(getClass(), SingleIdPanacheEntity::createPropertyMap);

    Stream<Map.Entry<String, PropertyMeta>> entryStream = propertyMap
        .entrySet()
        .stream()
        .filter(e -> e.getValue().isId());

    if (!idOnly) {
      Stream<Map.Entry<String, PropertyMeta>> nonIdEntryStream = propertyMap
          .entrySet()
          .stream()
          .filter(e -> !e.getValue().isId());

      entryStream = Stream.concat(entryStream, nonIdEntryStream);
    }

    return entryStream
        .map(e -> e.getKey() + (e.getValue().isId() ? "==" : "=") + e.getValue().getValue(this))
        .collect(Collectors.joining(", ", getClass().getSimpleName() + "{", "}"));
  }

  private static class PropertyMeta {
    private Member member;
    private boolean id;

    public PropertyMeta(Member member, boolean isId) {
      this.member = member;
      this.id = isId;
    }

    protected boolean isId() {
      return this.id;
    }

    public Object getValue(Object obj) {
      try {
        if (this.member instanceof Field) {
          Field field = (Field) this.member;
          field.setAccessible(true);
          return field.get(obj);
        }

        if (this.member instanceof Method) {
          Method method = (Method) this.member;
          method.setAccessible(true);
          return method.invoke(obj, (Object[]) null);
        }
      } catch (Exception e) { // CHECKSTYLE:IGNORE
        return e;
      }

      throw new BugException("Only Field or Method members are supported");
    }
  }

  private static final ConcurrentMap<Class<?>, SortedMap<String, PropertyMeta>> CLASS_PROPERTY_MAP = new ConcurrentHashMap<>();

  private static SortedMap<String, PropertyMeta> createPropertyMap(Class<?> clazz) {
    SortedMap<String, PropertyMeta> propertyMap = new TreeMap<>();
    AccessType accessType = AccessType.FIELD;
    do {
      accessType = getAccessType(clazz, accessType);

      switch (accessType) {
      case FIELD:
        for (Field field : clazz.getDeclaredFields()) {
          if (((field.getModifiers() & (Modifier.TRANSIENT | Modifier.STATIC)) == 0)
              && !field.isAnnotationPresent(Transient.class)
              && isType4PropertyMap(field.getType())) {
            propertyMap.put(field.getName(), new PropertyMeta(field, field.isAnnotationPresent(Id.class) || field.isAnnotationPresent(EmbeddedId.class)));
          }
        }
        break;

      case PROPERTY:
        for (Method method : clazz.getDeclaredMethods()) {
          if (((method.getModifiers() & (Modifier.ABSTRACT | Modifier.PRIVATE | Modifier.STATIC)) == 0)
              && !method.isAnnotationPresent(Transient.class)
              && method.getParameterCount() == 0
              && isType4PropertyMap(method.getReturnType())) {
            String propertyName = MethodUtil.getPropertyName(method.getName());
            if (propertyName != null) {
              propertyMap.put(propertyName, new PropertyMeta(method, method.isAnnotationPresent(Id.class) || method.isAnnotationPresent(EmbeddedId.class)));
            }
          }
        }
        break;

      default:
        throw new BugException("Unsupported AccessType " + accessType);
      }

      clazz = clazz.getSuperclass();
    } while (clazz != null);

    return propertyMap;

  }

  private static AccessType getAccessType(Class<?> clazz, AccessType defaultValue) {
    Access access = clazz.getAnnotation(Access.class);
    if (access != null) {
      return access.value();
    }

    if (defaultValue != null) {
      return defaultValue;
    }

    throw new IllegalArgumentException("ToStringable class must be annotated with @Access: " + clazz);
  }

  private static boolean isType4PropertyMap(Class<?> clazz) {
    return clazz.isPrimitive() || clazz.isEnum() || ClassUtil.isPrimitiveWrapper(clazz) || ClassUtil.isTemporal(clazz) || clazz.equals(String.class) || clazz.isAnnotationPresent(Embeddable.class);
  }

}

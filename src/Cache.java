public class Cache {
  public int id;
  public int size;

  public Cache(int id, int size) {
    this.id = id;
    this.size = size;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Cache cache = (Cache) o;

    return id == cache.id;
  }

  @Override
  public int hashCode() {
    return id;
  }


}

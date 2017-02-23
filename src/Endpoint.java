import java.util.HashMap;
import java.util.Map;

public class Endpoint {
  public int id;
  public Map<Cache,Integer> caches;
  public int ld;

  public Endpoint(int id, int ld) {
    this.id = id;
    this.ld = ld;
    this.caches = new HashMap<>();
  }

  public void addCache(Cache c, int lc){
    this.caches.put(c,lc);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Endpoint endpoint = (Endpoint) o;

    return id == endpoint.id;
  }

  @Override
  public int hashCode() {
    return id;
  }
}

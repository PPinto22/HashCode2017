public class PairVideoPoints implements Comparable<PairVideoPoints>{

  public Video video;
  public float points;

  public PairVideoPoints(Video video, float points) {
    this.video = video;
    this.points = points;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    PairVideoPoints that = (PairVideoPoints) o;

    return video != null ? video.equals(that.video) : that.video == null;
  }

  @Override
  public int hashCode() {
    return video != null ? video.hashCode() : 0;
  }


  @Override
  public int compareTo(PairVideoPoints pairVideoPoints) {
    if(this.points > pairVideoPoints.points)
      return -1;
    else if(this.points < pairVideoPoints.points)
      return 1;
    else
      return 0;
  }
}


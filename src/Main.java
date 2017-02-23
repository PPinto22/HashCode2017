import java.io.*;
import java.util.*;


public class Main {

//  private final static String inputFile = "input/me_at_the_zoo.in";
//  private final static String outputFile = "results/me_at_the_zoo.txt";

  private final static String inputFile = "input/kittens.in";
  private final static String outputFile = "results/kittens.txt";

//  private final static String inputFile = "input/trending_today.in";
//  private final static String outputFile = "results/trending_today.txt";
//
//  private final static String inputFile = "input/videos_worth_spreading.in";
//  private final static String outputFile = "results/videos_worth_spreading.txt";

  private List<Requests> requests;
  private List<Video> videos;
  private List<Endpoint> endpoints;
  private List<Cache> caches;

  private Map<Cache, ArrayList<Endpoint>> cacheEndpoints;
  private Map<Endpoint, Map<Video,Integer>> endpointVideoRequests;

  private int V,E,R,C,X;
  HashMap<Cache, List<PairVideoPoints>> matrizFinal;

  public Main() {
    this.requests = new ArrayList<>();
    this.videos = new ArrayList<>();
    this.endpoints = new ArrayList<>();
    this.caches = new ArrayList<>();

    this.cacheEndpoints = new HashMap<>();
    this.endpointVideoRequests = new HashMap<>();
  }

  public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
    new Main().start();
  }

  private void start() throws FileNotFoundException, UnsupportedEncodingException {
    System.out.println("Comecou");
    readInput();
    System.out.println("Ja li");
    solver();
    System.out.println("ja resolvi");
    printResult();
  }

  private void printResult() throws FileNotFoundException, UnsupportedEncodingException {
    PrintWriter pw = new PrintWriter(new FileOutputStream(new File(outputFile)),true);
    pw.println(matrizFinal.size());
    for (Cache c : caches) {
      if(matrizFinal.containsKey(c)) {
        pw.print(c.id);
        for (PairVideoPoints p : matrizFinal.get(c)) {
          pw.print(" " + p.video.id);
        }
        pw.println("");
      }
    }
  }

  private void solver() {
    HashMap<Cache, List<PairVideoPoints>> matriz = new HashMap<>();
    matrizFinal = new HashMap<>();

    for(Cache cache: this.caches)
      matriz.put(cache, new ArrayList<>());

//    for(Cache cache: this.caches){
//      for(Video video: this.videos){
//        if(video.cached < 1) {
//          float pontos = getPoints(cache, video);
//          pontos *= (1 - (float) video.size / cache.size);
//          matriz.get(cache).add(new PairVideoPoints(video, pontos));
//          video.cached++;
//        }
//      }
//    }

    Random r = new Random();
    for(Video video: this.videos){
      for(Cache cache: this.caches){
          float pontos = getPoints(cache, video);
          pontos *= (1 - (float) video.size / cache.size);
          pontos /= (video.cached++) * r.nextInt(5)/5;
          matriz.get(cache).add(new PairVideoPoints(video, pontos));

      }
    }

    for(Cache c: matriz.keySet()){
      List<PairVideoPoints> listP = matriz.get(c);
      Collections.sort(listP);
      int i;
      for(i = 0; i < listP.size(); i++) {
        PairVideoPoints p = listP.get(i);
        if (c.size - p.video.size < 0)
          break;
        c.size -= p.video.size;
      }

      if(i > 0) {
        matrizFinal.put(c, new ArrayList<>(listP.subList(0, i)));
      }
    }
  }

  private float getPoints(Cache cache, Video video) {
    int nTotal = 0;
    int latTotal = 0;

    for(Endpoint endpoint: this.cacheEndpoints.get(cache)){
      Integer Ni = this.endpointVideoRequests.get(endpoint).get(video);
      if(Ni == null)
        continue;

      int N = Ni;

      int delta = endpoint.ld - endpoint.caches.get(cache);

      nTotal += N;
      latTotal += N*delta;
    }

    if(nTotal == 0)
      return 0;

    return (float)latTotal;//nTotal;
  }

  private void readInput() {
    String line;
    String[] words;
    try(BufferedReader br = new BufferedReader(new FileReader(inputFile))){
      line = br.readLine();
      words = line.split(" ");
      this.V = Integer.parseInt(words[0]);
      this.E = Integer.parseInt(words[1]);
      this.R = Integer.parseInt(words[2]);
      this.C = Integer.parseInt(words[3]);
      this.X = Integer.parseInt(words[4]);

      for(int i = 0; i<C; i++) {
        Cache cache = new Cache(i,X);
        this.caches.add(cache);
        this.cacheEndpoints.put(cache,new ArrayList<>());
      }

      line = br.readLine();
      words = line.split(" ");
      for(int i = 0; i<V; i++){
        int size = Integer.parseInt(words[i]);
        Video video = new Video(i,size);
        videos.add(video);
      }

      for(int i = 0; i<E; i++){
        line = br.readLine();
        words = line.split(" ");
        int ld = Integer.parseInt(words[0]);
        int k = Integer.parseInt(words[1]);
        Endpoint endpoint = new Endpoint(i,ld);
        for(int j = 0; j<k; j++){
          line = br.readLine();
          words = line.split(" ");
          int idC = Integer.parseInt(words[0]);
          int lc = Integer.parseInt(words[1]);
          endpoint.addCache(this.caches.get(idC),lc);
          cacheEndpoints.get(this.caches.get(idC)).add(endpoint);
        }
        this.endpoints.add(endpoint);
        this.endpointVideoRequests.put(endpoint,new HashMap<>());
      }

      for(int i = 0; i<R; i++){
        line = br.readLine();
        words = line.split(" ");
        int idV = Integer.parseInt(words[0]);
        int idE = Integer.parseInt(words[1]);
        int rN = Integer.parseInt(words[2]);
        Requests r = new Requests(this.videos.get(idV),this.endpoints.get(idE),rN);
        this.requests.add(r);
        this.endpointVideoRequests.get(this.endpoints.get(idE)).put(this.videos.get(idV),rN);
      }

    } catch (Exception e){
      e.printStackTrace();
    }
  }
}

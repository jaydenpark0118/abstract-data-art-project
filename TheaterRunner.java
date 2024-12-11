import org.code.theater.*;
import java.util.Scanner;

public class TheaterRunner {
  public static void main(String[] args) {

    /*
    creates lists for each text file
      */
    String[] albumList = FileReader.toStringArray("album.txt");
    String[] artistList = FileReader.toStringArray("artist.txt");
    int[] yearList = FileReader.toIntArray("year.txt");
    String[] genreList = FileReader.toStringArray("genre.txt");

    DataScene bob = new DataScene(albumList, artistList, yearList, genreList);

    //scanner for user input
    Scanner x = new Scanner(System.in);
    System.out.println("Choose a random album(1-500)");
    int y = x.nextInt();
    x.close();
    
    bob.drawResults(y-1);

    Theater.playScenes(bob);
  }
}
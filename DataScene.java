import org.code.theater.*;

public class DataScene extends Scene {

  //instance variables
  private String[] albums;
  private String[] artists;
  private int[] years;
  private String[] genres;

  //parameterized constructor
  public DataScene(String[] albums, String[] artists, int[] years, String[] genres) {
    this.albums = albums;
    this.artists = artists;
    this.years = years;
    this.genres = genres;
  }

  //seperates the genres from the string into a list
  public String[] sepGenres(String genresS) {
    int count = 1;
    char t = ',';
    //gets the amount of words in each string
    for(int i = 0; i < genresS.length(); i++) {
      if(genresS.charAt(i) == t) {
        count++;
      }
    }

    //if only one word, y will be empty
    int temp = 0;
    if(count == 1) {
      temp = 1;
    } else {
      temp = count-1;
    }
    int[] y = new int[temp];
    String[] x = new String[count];

    
    int amt = 0;
    
    //gets the position of each comma
    for(int j = 0; j < genresS.length(); j++) {
      if(genresS.charAt(j) == t) {
        y[amt] = j;
        amt++;
      }
    }

    boolean one = false;
    //if there is only one genre
    if(y[0] == 0) {
      one = true;
    }
    
    int b = 0;
    //makes each index of x = to a genre
    for(int a = 0; a < count; a++) {
      if(a == 0) {
        if(one == true) {
        x[a] = genresS.substring(0, genresS.length());
        } else {
          x[a] = genresS.substring(0, y[0]);
          b++;
        }
      } else if(a > 0 && a < count-1) {
        x[a] = genresS.substring(y[b-1]+2, y[b]);
        b++;
      } else {
        x[a] = genresS.substring(y[b-1]+2, genresS.length());
      }
    }

    return x;
  }

  /*
  gets a list of emojis that correspond to genres
  uses filtGenres list
  */
  public String[] getE(int i, String[] filtGenres) {
    String[] emojis = new String[filtGenres.length];

    //creates lists for emojis and filtered genres
    String[] emojiList = FileReader.toStringArray("emojis.txt");
    String[] corrGenresList = FileReader.toStringArray("corrGenres.txt");
    
    for(int s = 0; s < filtGenres.length; s++) {
      for(int d = 0; d < corrGenresList.length; d++) {
        if(filtGenres[s].equals(corrGenresList[d])) {
          emojis[s] = emojiList[d];
        }
      }
    }

    return emojis;
  }

  /*
  gets sizes for each image
  decrease if there are more images
  */
  public int[] getSize(String[] filtGenres) {
    int[] sizes = new int[filtGenres.length];

    int r = 200;
    for(int t = 0; t < filtGenres.length; t++) {
      sizes[t] = r;
      r -= 25;
    }
    
    return sizes;
  }

  //prints everything
  public void drawResults(int index) {
    String[] filtGenres = sepGenres(genres[index]);
    String[] emojis = getE(index, filtGenres);

    clear("white");

    setTextColor("black");

    String half1 = "";
    String half2 = "";
    int pos = 0;
    char tar = ' ';
    String album = albums[index];
    String year = " (" + years[index] + ")";
    String title = albums[index] + " (" + years[index] + ")";
    
    setTextHeight(20);

    int futureG = 0;

    //if title goes off screen
    if(title.length() >= 35) {
      for(int i = 35; i > 0; i--) {
        //ends the string at the last word that fits on screen
        if(title.charAt(i) == tar) {
          pos = i;
          break;
        }
      }

      /*
      makes the first string into the first part that fits on screen
      makes the second into the rest of the string
      */
      half1 = title.substring(0, pos);
      half2 = title.substring(pos+1, title.length());

      //draws all text
      drawText(half1, 30, 40);
      drawText(half2, 30, 65);
      drawText("By: " +artists [index], 30, 90);

      drawText("Genres represented: ", 30, 120);
      futureG = 155;
    } else {

      //if it already fits, it will print normally
      drawText(title, 30, 40);
      drawText("By: " +artists [index], 30, 65);
      
      drawText("Genres represented: ", 30, 95);
      futureG = 120;
    }

    //prints all genres
    for(int e = 0; e < filtGenres.length; e++) {
      drawText(filtGenres[e], 30, futureG);
      futureG += 25;
    }

    //plays a piano note
    playNote(Instrument.PIANO, 70, 0.2);

    //for every genre
    for(int p = 0; p < filtGenres.length; p++) {
      //gets a random position
      int x = (int)((Math.random() * 320) + 30);
      int y = (int)((Math.random() * 200) + 150);

      //uses the sizes to draw images
      int size[] = getSize(filtGenres);
      drawImage(emojis[p], x, y, size[p]);
    }
  }
}
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


public class Playlist implements Serializable {

    private ArrayList<StoreSongs> songList = new ArrayList<>();

    public static int menuChoice() {

        int choice;

        Scanner input = new Scanner(System.in);

        System.out.println("1. Add a new song");
        System.out.println("2. List all songs");
        System.out.println("3. Delete an existing song");
        System.out.println("4. Search for a song");
        System.out.println("5. Display total playtime");
        System.out.println("6. exit");
        System.out.print("Please enter your choice: ");
        choice = input.nextInt();

        return choice;
    }

    public StoreSongs enterSong() {

        Scanner input = new Scanner(System.in);

        String name, artist;
        int length;

        System.out.println("\nTitle of the song: ");
        name = input.nextLine();
        System.out.println("Name of the Artist: ");
        artist = input.nextLine();
        System.out.println("Duration (seconds): ");
        length = input.nextInt();
        System.out.println();

        return new StoreSongs(name, artist, length);

    }

    public void showPlaylist() throws IOException, ClassNotFoundException {
        songList = readInPlaylist();
        if(songList.size() == 0) {
            System.out.println("\nPlaylist is empty\n");
        } else {
            System.out.println();
            for (int i = 0; i < songList.size(); i++) {
                System.out.println("Track " + (i + 1));
                System.out.println("Title: " + songList.get(i).getName());
                System.out.println("Artist: " + songList.get(i).getArtist());
                System.out.println("Duration(seconds): " + songList.get(i).getLength());
                System.out.println();
            }
        }
    }

    public void deleteSong() throws IOException, ClassNotFoundException {
        Scanner input = new Scanner(System.in);

        songList = readInPlaylist();

        if(songList.size() == 0) {
            System.out.print("\nPlaylist is empty\n");
            System.out.println();
        } else {
            System.out.println();
            for (int i = 0; i < songList.size(); i++) {
                System.out.println((i + 1) + ": " + songList.get(i).getName());
            }
            System.out.print("\nWhich song would you like to delete? (input the index number): ");
            int deleteChoice = input.nextInt();
            if (songList.size() == 1) {
                System.out.println("\n" + songList.remove(0).getName() + " is removed from your library\n");
            } else {
                System.out.println(songList.remove((deleteChoice - 1)).getName() + " is removed from your library\n");
            }
        }
        savePlaylist();
    }

    public void findSongTitle() throws IOException, ClassNotFoundException {
        Scanner input = new Scanner(System.in);

        songList = readInPlaylist();

        System.out.print("Please input the title of the song: ");
        String findSong = input.nextLine();
        System.out.println();
        System.out.println("The song is found in your library, here are the details:\n");
        for(int i = 0; i<songList.size(); i++) {
            if (findSong.equals(songList.get(i).getName())) {
                System.out.println("Track " + (i + 1));
                System.out.println("Title: " + songList.get(i).getName());
                System.out.println("Artist: " + songList.get(i).getArtist());
                System.out.println("Duration(seconds): " + songList.get(i).getLength());
                System.out.println();
            }
        }
    }

    public void totalDuration() throws IOException, ClassNotFoundException {

        songList = readInPlaylist();

        int totalDuration = 0;
        for (StoreSongs storeSongs : songList) {
            totalDuration += storeSongs.getLength();
            //totalDuration += totalDuration;
        }
        System.out.print("the total playtime for " + songList.size() + " songs (");
        for(int i = 0; i<songList.size(); i++) {
            if(i == 0) {
                System.out.print(songList.get(i).getName());
            } else {
                System.out.print(", " + songList.get(i).getName());
            }
        }
        System.out.println(") is: " + totalDuration);
    }
   public void savePlaylist() throws IOException {
       FileOutputStream fos = new FileOutputStream("playlist");
       ObjectOutputStream oos = new ObjectOutputStream(fos);
       oos.writeObject(songList);
       oos.close();
    }

    public ArrayList<StoreSongs> readInPlaylist() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("playlist");
        ObjectInputStream ois = new ObjectInputStream(fis);
        ArrayList<StoreSongs> songList = (ArrayList<StoreSongs>) ois.readObject();
        ois.close();
        return songList;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Playlist playlist = new Playlist();

        boolean exit = false;
        int choice;

        System.out.println("Welcome to my playlist!\n");
        do {
            choice = menuChoice();
            switch (choice) {
                case 1:
                    playlist.songList = playlist.readInPlaylist();
                    playlist.songList.add(playlist.enterSong());
                    playlist.savePlaylist();
                    break;
                case 2:
                    playlist.showPlaylist();
                    break;
                case 3:
                    playlist.deleteSong();
                    break;
                case 4:
                    playlist.findSongTitle();
                    break;
                case 5:
                    playlist.totalDuration();
                    break;
                case 6:
                    exit = true;
                    break;
            }
        } while(!exit);
    }

}

class StoreSongs implements Serializable{
    private final String name;
    private final String artist;
    private final int length;

    public StoreSongs(String name, String artist, int length) {
        this.name = name;
        this.artist = artist;
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public int getLength() {
        return length;
    }
 }

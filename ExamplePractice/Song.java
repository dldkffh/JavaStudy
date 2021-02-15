package ch4CheckTime;

public class Song {
	static String title, artist, album, composer[];
	static int year, trak;
	
	public Song(){}
	public Song(String title, String artist, String album, String composer[],
			int year, int trak){
		this.title = title;
		this.artist = artist;
		this.album = album;
		this.composer = composer;
		this.year = year;
		this.trak =trak;
	}
	
	public void show(String title){
		System.out.println("곡이름:"+ title +" 아티스트:"+ artist +" 앨범이름:"+ album);
		System.out.println("작곡가:");
		for (int i=0; i<composer.length; i++) {
			 System.out.print(composer[i]);
			 if (i+1 == composer.length)
				 System.out.println();
			 else
				 System.out.print(",");
		}
		System.out.print("발표연도: "+ year +" 트랙번호:"+ trak);
	}
	
	public static void main(String[] args){
		Song Song = new Song("Dansing Queen", "ABBA", "Arrival",
				  new String[]{"Benny Andersson","Bjorn Ulvaeus"},
				  1977, 2);

		Song.show("Dancing Queen");
	}
}

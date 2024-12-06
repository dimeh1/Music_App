package com.example.music_app;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.music_app.model.Album;
import com.example.music_app.model.Artist;
import com.example.music_app.model.Song;
import com.example.music_app.model.Status;
import com.example.music_app.model.User;
import com.example.music_app.repository.AlbumRepository;
import com.example.music_app.repository.ArtistRepository;
import com.example.music_app.repository.SongRepository;
import com.example.music_app.repository.UserRepository;

@SpringBootApplication
public class MusicApplication implements ApplicationRunner{

	public static void main(String[] args) {
		SpringApplication.run(MusicApplication.class, args);
	}
	@Autowired
	ArtistRepository artistRepository;
	@Autowired
	AlbumRepository albumRepository;
	@Autowired
	SongRepository songRepository;
	@Autowired
	UserRepository userRepository;
	@Override
    public void run(ApplicationArguments args) throws Exception {

		//Ajouter des comptes admin
		User admin1=new User();
		admin1.setUsername("admin1");
		admin1.setEmail("admin1@gmail.com");
		admin1.setPassword("a");
		admin1.setStatus(Status.ADMIN);
		
		userRepository.save(admin1);
		
		
        // Ajouter des artistes
        Artist pnl = new Artist();
        pnl.setName("PNL");
        pnl.setImage("/img/artists/pnl.JPG");
        Artist laylow = new Artist();
        laylow.setName("Laylow");
        laylow.setImage("/img/artists/laylow.jpeg");        
        Artist damso = new Artist();
        damso.setName("Damso");
        damso.setImage("/img/artists/damso.jpeg");      
        Artist drake = new Artist();
        drake.setName("Drake");
        drake.setImage("/img/artists/drake.jpeg");        
        Artist djSnake = new Artist();
        djSnake.setName("DJ Snake");
        djSnake.setImage("/img/artists/djSnake.jpeg");
        Artist selenaG = new Artist();
        selenaG.setName("Selena Gomez");
        selenaG.setImage("/img/artists/selenaG.jpeg");
        Artist rihanna = new Artist();
        rihanna.setName("Rihanna");
        rihanna.setImage("/img/artists/rihanna.jpeg");
        Artist theweekend = new Artist();
        theweekend.setName("The weekend");
        theweekend.setImage("/img/artists/theweekend.jpeg");
        Artist aliciaKeys = new Artist();
        aliciaKeys.setName("Alicia Keys");
        aliciaKeys.setImage("/img/artists/aliciaKeys.jpeg");
        Artist amyW = new Artist();
        amyW.setName("Amy Winehouse");
        amyW.setImage("/img/artists/amyWinehouse.jpeg");
        Artist adele = new Artist();
        adele.setName("Adele");
        adele.setImage("/img/artists/adele.jpeg");
        Artist sade = new Artist();
        sade.setName("Sade");
        sade.setImage("/img/artists/sade.jpeg");
        
        artistRepository.saveAll(Arrays.asList(pnl, laylow, damso, drake, djSnake, selenaG, rihanna,theweekend, aliciaKeys, amyW, adele, sade));

        // Ajouter des albums
        Album albumPnl = new Album();
        albumPnl.setNom("Deux frères");
        albumPnl.setImage("/img/albums/deux_freres_PNL.JPG");
        albumPnl.setDate_sortie(java.time.LocalDate.of(2020, 5, 10));
        albumPnl.setArtists(Arrays.asList(pnl));
        Album albumLaylow = new Album();
        albumLaylow.setNom("L'Etrange Histoire de Mr.Anderson");
        albumLaylow.setImage("/img/albums/l_etrange_histoire_Laylow.JPG");
        albumLaylow.setDate_sortie(java.time.LocalDate.of(2021, 8, 15));
        albumLaylow.setArtists(Arrays.asList(laylow));
        Album albumDamso = new Album();
        albumDamso.setNom("Ipséité");
        albumDamso.setImage("/img/albums/ipseite_Damso.JPG");
        albumDamso.setDate_sortie(java.time.LocalDate.of(2021, 8, 15));
        albumDamso.setArtists(Arrays.asList(damso));
        Album albumDrake = new Album();
        albumDrake.setNom("Views");
        albumDrake.setImage("/img/albums/views_Drake.JPG");
        albumDrake.setDate_sortie(java.time.LocalDate.of(2021, 8, 15));
        albumDrake.setArtists(Arrays.asList(drake));
        Album albumDJSnake = new Album();
        albumDJSnake.setNom("Album 2");
        albumDJSnake.setImage("/img/albums/carte_blanche_DJSnake.JPG");
        albumDJSnake.setDate_sortie(java.time.LocalDate.of(2021, 8, 15));
        albumDJSnake.setArtists(Arrays.asList(djSnake));
        Album albumSelenaG = new Album();
        albumSelenaG.setNom("Rare");
        albumSelenaG.setImage("/img/albums/rare_SelenaGomez.JPG");
        albumSelenaG.setDate_sortie(java.time.LocalDate.of(2021, 8, 15));
        albumSelenaG.setArtists(Arrays.asList(selenaG));
        Album albumRihanna = new Album();
        albumRihanna.setNom("Unapologetic (Deluxe)");
        albumRihanna.setImage("/img/albums/unapologetic_Rihanna.JPG");
        albumRihanna.setDate_sortie(java.time.LocalDate.of(2021, 8, 15));
        albumRihanna.setArtists(Arrays.asList(rihanna));
        Album albumTheweekend = new Album();
        albumTheweekend.setNom("After Hours");
        albumTheweekend.setImage("/img/albums/after_hours_TheWeekend.JPG");
        albumTheweekend.setDate_sortie(java.time.LocalDate.of(2021, 8, 15));
        albumTheweekend.setArtists(Arrays.asList(theweekend));
        Album albumAliciaKeys = new Album();
        albumAliciaKeys.setNom("The diary of alicia keys");
        albumAliciaKeys.setImage("/img/albums/the_diary_of_alicia_keys_AliciaKeys.JPG");
        albumAliciaKeys.setDate_sortie(java.time.LocalDate.of(2021, 8, 15));
        albumAliciaKeys.setArtists(Arrays.asList(aliciaKeys));
        Album albumAmyW = new Album();
        albumAmyW.setNom("Back to black");
        albumAmyW.setImage("/img/albums/back_to_black_AmyWinehouse.JPG");
        albumAmyW.setDate_sortie(java.time.LocalDate.of(2021, 8, 15));
        albumAmyW.setArtists(Arrays.asList(amyW));
        Album albumAdele = new Album();
        albumAdele.setNom("21");
        albumAdele.setImage("/img/albums/21_Adele.JPG");
        albumAdele.setDate_sortie(java.time.LocalDate.of(2021, 8, 15));
        albumAdele.setArtists(Arrays.asList(adele));
        Album albumSade = new Album();
        albumSade.setNom("The best of Sade");
        albumSade.setImage("img/albums/the_best_of_sade_Sade.JPG");
        albumSade.setDate_sortie(java.time.LocalDate.of(2021, 8, 15));
        albumSade.setArtists(Arrays.asList(sade));

        albumRepository.saveAll(Arrays.asList(albumPnl, albumLaylow, albumDamso, albumDrake, albumDJSnake, albumSelenaG,
        		albumRihanna, albumTheweekend, albumAliciaKeys, albumAmyW, albumAdele, albumSade));

        // Ajouter des chansons
        Song songRyuk = new Song();
        songRyuk.setTitre("Ryuk");
        songRyuk.setImage("/img/albums/deux_freres_PNL.JPG");
        songRyuk.setDuree(180);
        songRyuk.setAlbum(albumPnl);
        songRyuk.setArtists(Arrays.asList(pnl));
        songRyuk.setGenre(com.example.music_app.model.Genre.RAP);
        Song songUneHistoire = new Song();
        songUneHistoire.setTitre("UNE HISTOIRE ETRANGE");
        songUneHistoire.setImage("/img/albums/l_etrange_histoire_Laylow.JPG");
        songUneHistoire.setDuree(200);
        songUneHistoire.setAlbum(albumLaylow);
        songUneHistoire.setArtists(Arrays.asList(laylow));
        songUneHistoire.setGenre(com.example.music_app.model.Genre.RAP);
        Song songE_Signaler = new Song();
        songE_Signaler.setTitre("E. Signaler");
        songE_Signaler.setImage("/img/albums/ipseite_Damso.JPG");
        songE_Signaler.setDuree(200);
        songE_Signaler.setAlbum(albumDamso);
        songE_Signaler.setArtists(Arrays.asList(damso));
        songE_Signaler.setGenre(com.example.music_app.model.Genre.RAP);
        Song songTooGood = new Song();
        songTooGood.setTitre("Too Good");
        songTooGood.setImage("/img/albums/views_Drake.JPG");
        songTooGood.setDuree(200);
        songTooGood.setAlbum(albumDrake);
        songTooGood.setArtists(Arrays.asList(drake, rihanna));
        songTooGood.setGenre(com.example.music_app.model.Genre.POP);
        Song songTakiTaki = new Song();
        songTakiTaki.setTitre("Taki Taki");
        songTakiTaki.setImage("/img/albums/carte_blanche_DJSnake.JPG");
        songTakiTaki.setDuree(200);
        songTakiTaki.setAlbum(albumDJSnake);
        songTakiTaki.setArtists(Arrays.asList(djSnake, selenaG));
        songTakiTaki.setGenre(com.example.music_app.model.Genre.POP);
        Song songSouvenir = new Song();
        songSouvenir.setTitre("Souvenir");
        songSouvenir.setImage("/img/albums/rare_SelenaGomez.JPG");
        songSouvenir.setDuree(200);
        songSouvenir.setAlbum(albumSelenaG);
        songSouvenir.setArtists(Arrays.asList(selenaG));
        songSouvenir.setGenre(com.example.music_app.model.Genre.POP);
        Song songStay = new Song();
        songStay.setTitre("Stay");
        songStay.setImage("/img/albums/unapologetic_Rihanna.JPG");
        songStay.setDuree(200);
        songStay.setAlbum(albumRihanna);
        songStay.setArtists(Arrays.asList(rihanna));
        songStay.setGenre(com.example.music_app.model.Genre.RNB);
        Song songEyes = new Song();
        songEyes.setTitre("Eyes");
        songEyes.setImage("/img/albums/after_hours_TheWeekend.JPG");
        songEyes.setDuree(200);
        songEyes.setAlbum(albumTheweekend);
        songEyes.setArtists(Arrays.asList(theweekend));
        songEyes.setGenre(com.example.music_app.model.Genre.RNB);
        Song songFallin = new Song();
        songFallin.setTitre("Fallin'");
        songFallin.setImage("/img/albums/the_diary_of_alicia_keys_AliciaKeys.JPG");
        songFallin.setDuree(200);
        songFallin.setAlbum(albumAliciaKeys);
        songFallin.setArtists(Arrays.asList(aliciaKeys));
        songFallin.setGenre(com.example.music_app.model.Genre.RNB);
        Song songRehab = new Song();
        songRehab.setTitre("Rehab");
        songRehab.setImage("/img/albums/back_to_black_AmyWinehouse.JPG");
        songRehab.setDuree(200);
        songRehab.setAlbum(albumAmyW);
        songRehab.setArtists(Arrays.asList(amyW));
        songRehab.setGenre(com.example.music_app.model.Genre.JAZZ);
        Song songSomeoneLike = new Song();
        songSomeoneLike.setTitre("Someone Like You");
        songSomeoneLike.setImage("/img/albums/21_Adele.JPG");
        songSomeoneLike.setDuree(200);
        songSomeoneLike.setAlbum(albumAdele);
        songSomeoneLike.setArtists(Arrays.asList(adele));
        songSomeoneLike.setGenre(com.example.music_app.model.Genre.JAZZ);
        Song songLikeaToo = new Song();
        songLikeaToo.setTitre("Like a Tatoo");
        songLikeaToo.setImage("img/albums/the_best_of_sade_Sade.JPG");
        songLikeaToo.setDuree(200);
        songLikeaToo.setAlbum(albumSade);
        songLikeaToo.setArtists(Arrays.asList(sade));
        songLikeaToo.setGenre(com.example.music_app.model.Genre.JAZZ);

        songRepository.saveAll(Arrays.asList(songRyuk, songUneHistoire, songE_Signaler, songTooGood, songTakiTaki,
        		songSouvenir, songStay, songEyes, songFallin, songRehab, songSomeoneLike, songLikeaToo));
    }
	
	
}

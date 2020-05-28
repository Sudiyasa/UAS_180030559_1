package com.bh183.sudiyasa;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    private final static int DATABASE_VERSION = 3;
    private final static String DATABASE_NAME = "db_film";
    private final static String TABLE_FILM ="t_film";
    private final static String KEY_ID_FILM = "ID_Film";
    private final static String KEY_JUDUL = "Judul";
    private final static String KEY_TGL = "Release_Date";
    private final static String KEY_COVER = "Cover";
    private final static String KEY_GENRE = "Genre";
    private final static String KEY_DURATION = "Duration";
    private final static String KEY_RATING = "Rating";
    private final static String KEY_SYNOPSIS = "Synopsis";
    private Context context;


    public DatabaseHandler(Context ctx){
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = ctx;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_FILM = "CREATE TABLE " + TABLE_FILM
                + "(" + KEY_ID_FILM + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_JUDUL + " TEXT, " + KEY_TGL + " TEXT, "
                + KEY_COVER + " TEXT, " + KEY_GENRE + " TEXT, "
                + KEY_DURATION + " TEXT, " + KEY_RATING + " TEXT, " + KEY_SYNOPSIS + " TEXT);";

        db.execSQL(CREATE_TABLE_FILM);
        inisialisasiFilmAwal(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_FILM;
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public void tambahFilm(Film dataFilm){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataFilm.getJudul());
        cv.put(KEY_TGL, dataFilm.getRilis());
        cv.put(KEY_COVER, dataFilm.getCover());
        cv.put(KEY_GENRE, dataFilm.getGenre());
        cv.put(KEY_DURATION, dataFilm.getDurasi());
        cv.put(KEY_RATING, dataFilm.getRating());
        cv.put(KEY_SYNOPSIS, dataFilm.getSynopsis());


        db.insert(TABLE_FILM, null, cv);
        db.close();
    }

    public void tambahFilm(Film dataFilm, SQLiteDatabase db){
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataFilm.getJudul());
        cv.put(KEY_TGL, dataFilm.getRilis());
        cv.put(KEY_COVER, dataFilm.getCover());
        cv.put(KEY_GENRE, dataFilm.getGenre());
        cv.put(KEY_DURATION, dataFilm.getDurasi());
        cv.put(KEY_RATING, dataFilm.getRating());
        cv.put(KEY_SYNOPSIS, dataFilm.getSynopsis());
        db.insert(TABLE_FILM, null, cv);
    }

    public void editFilm(Film dataFilm){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataFilm.getJudul());
        cv.put(KEY_TGL, dataFilm.getRilis());
        cv.put(KEY_COVER, dataFilm.getCover());
        cv.put(KEY_GENRE, dataFilm.getGenre());
        cv.put(KEY_DURATION, dataFilm.getDurasi());
        cv.put(KEY_RATING, dataFilm.getRating());
        cv.put(KEY_SYNOPSIS, dataFilm.getSynopsis());

        db.update(TABLE_FILM, cv, KEY_ID_FILM + "=?", new String[]{String.valueOf(dataFilm.getIdFilm())});
        db.close();
    }

    public void hapusFilm (int idFilm){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_FILM, KEY_ID_FILM + "=?", new String[]{String.valueOf(idFilm)});
        db.close();
    }

    public ArrayList<Film> getAllFilm(){
        ArrayList<Film> dataFilm = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_FILM;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor csr = db.rawQuery(query, null);
        if(csr.moveToFirst()){
            do {
                Film tempFilm = new Film(
                        csr.getInt(0),
                        csr.getString(1),
                        csr.getString(2),
                        csr.getString(3),
                        csr.getString(4),
                        csr.getString(5),
                        csr.getString(6),
                        csr.getString(7)

                );

                dataFilm.add(tempFilm);
            } while (csr.moveToNext());
        }

        return dataFilm;
    }

    private String storeImageFile(int id) {
        String location;
        Bitmap image = BitmapFactory.decodeResource(context.getResources(), id);
        location = InputActivity.saveImageToInternalStorage(image, context);
        return location;
    }

    private void inisialisasiFilmAwal(SQLiteDatabase db){
        int idFilm = 0;

        // Menambahkan data film ke 1
        Film film1 = new Film(
                idFilm,
                "JOKER (2019)",
                "02 October 2019",
                storeImageFile(R.drawable.film1),
                "Crime, Drama, Thriller",
                "2hours 2minutes",
                "8,5 - IMDb version",
                "Arthur Fleck is a wannabe stand-up comic who suffers from many mental illnesses, including one which causes him to laugh uncontrollably when he is nervous, and often gets him into bad situations. Arthur's mental health causes almost all people in society to reject and look down upon him, even though all he wants is to be accepted by others. After being brutally beaten, having his medication cut off, Arthur's life begins to spiral downward out-of-control into delusions, violence, and anarchy until he eventually transforms into Gotham's infamous Clown-Prince of Crime."
        );

        tambahFilm(film1, db);
        idFilm++;

        // Menambahkan data film ke 2
        Film film2 = new Film(
                idFilm,
                "Avengers: Endgame",
                "24 April 2019",
                storeImageFile(R.drawable.film2),
                "Action, Adventure, Drama",
                "3hours 2minutes",
                "8,4 - IMDb version",
                "Avengers: Endgame picks up after the events of Avengers: Infinity War, which saw the Avengers divided and defeated. Thanos won the day and used the Infinity Stones to snap away half of all life in the universe. Only the original Avengers - Iron Man, Captain America, Thor, Hulk, Black Widow, and Hawkeye remain, along with some key allies like War Machine, Ant-Man, Rocket Raccoon, Nebula, and Captain Marvel. Each of the survivors deals with the fallout from Thanos' decimation in different ways, but when an opportunity presents itself to potentially save those who vanished, they all come together and set out to defeat Thanos, once and for all."
        );

        tambahFilm(film2, db);
        idFilm++;

        // Menambahkan data film ke 3
        Film film3 = new Film(
                idFilm,
                "Fast And Furious 7",
                "03 April 2015",
                storeImageFile(R.drawable.film3),
                "Action, Adventure, Crime",
                "2hours 17minutes",
                "7,2 - IMDb version",
                "Dominic and his crew thought they'd left the criminal mercenary life behind. They'd defeated international terrorist Owen Shaw and went their separate ways. But now, Shaw's brother, Deckard Shaw, is out killing the crew one by one for revenge. Worse, a Somalian terrorist called Jakarde and a shady government official called \"Mr. Nobody\" are both competing to steal a computer terrorism program called \"God's Eye,\" that can turn any technological device into a weapon. Torretto must reconvene with his team to stop Shaw and retrieve the God's Eye program while caught in a power struggle between the terrorist and the United States government."
        );

        tambahFilm(film3, db);
        idFilm++;

        // Menambahkan data film ke 4
        Film film4 = new Film(
                idFilm,
                "Black Phanter",
                "16 February 2018",
                storeImageFile(R.drawable.film4),
                "Action, Adventure, Sci-Fi",
                "2hours 14minutes",
                "7,3 - IMDb version",
                "After the events of Captain America: Civil War, Prince T'Challa returns home to the reclusive, technologically advanced African nation of Wakanda to serve as his country's new king. However, T'Challa soon finds that he is challenged for the throne from factions within his own country. When two foes conspire to destroy Wakanda, the hero known as Black Panther must team up with C.I.A. agent Everett K. Ross and members of the Dora Milaje, Wakandan special forces, to prevent Wakanda from being dragged into a world war."
        );

        tambahFilm(film4, db);
        idFilm++;

        Film film5 = new Film(
                idFilm,
                "The Conjuring",
                "01 July 2013",
                storeImageFile(R.drawable.film5),
                "Horror, Mystery, Thriller",
                "1hours 52minutes",
                "7,5 - IMDb version",
                "In 1971, Carolyn and Roger Perron move their family into a dilapidated Rhode Island farm house and soon strange things start happening around it with escalating nightmarish terror. In desperation, Carolyn contacts the noted paranormal investigators, Ed and Lorraine Warren, to examine the house. What the Warrens discover is a whole area steeped in a satanic haunting that is now targeting the Perron family wherever they go. To stop this evil, the Warrens will have to call upon all their skills and spiritual strength to defeat this spectral menace at its source that threatens to destroy everyone involved."
        );

        tambahFilm(film5, db);
        idFilm++;

    }
}

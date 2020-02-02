package com.example.movieapp.ui.dbmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.movieapp.ui.model.MovieResponse;
import com.example.movieapp.ui.model.Movies;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "movies_db";
    private static final int DATABASE_VERSION = 3;


    public static final String TABLE_ALL_MOVIES = "all_movies";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_JSON_OBJECT = "json_object";
    public static final String COLUMN_CATEGORY = "category";


    public static final String SQL_CREATE_ALL_MOVIES_TABLE =
            "CREATE TABLE " + TABLE_ALL_MOVIES + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_JSON_OBJECT + " TEXT, " +
                    COLUMN_CATEGORY + " TEXT " +
                    "); ";


    public int insertAllMovies(JsonObject jsonObject, String category){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cValues = new ContentValues();
        cValues.put(COLUMN_JSON_OBJECT, jsonObject.toString());
        cValues.put(COLUMN_CATEGORY, category);
        long newRowId = db.insert(TABLE_ALL_MOVIES,null, cValues);
        db.close();
        if (newRowId > 0) {
            return 1;
        }else {
            return 0;
        }
    }


    public ArrayList<MovieResponse> getAllMoviesByCategory(String category){
        ArrayList<MovieResponse> movieResponses = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_JSON_OBJECT, COLUMN_CATEGORY};
        String sortOrder = COLUMN_FAV_ID + " ASC";
        Cursor cursor = db.query(TABLE_ALL_MOVIES, columns, COLUMN_CATEGORY+" =?", new String[]{category}, null, null, sortOrder);
        if (cursor.moveToFirst()){
            do {
                String json = cursor.getString(cursor.getColumnIndex(COLUMN_JSON_OBJECT));
                MovieResponse movieResponse = new Gson().fromJson(json, new TypeToken<MovieResponse>() {}.getType());
                movieResponses.add(movieResponse);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return movieResponses;
    }


    public void deleteAllByCategory(String category) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ALL_MOVIES, COLUMN_CATEGORY+ " =?", new String[]{category});
    }


    public static final String TABLE_FAV = "favorite";
    public static final String COLUMN_FAV_ID = "_id";
    public static final String COLUMN_MOVIE_ID = "movie_id";
    public static final String COLUMN_FAV_TYPE = "type";
    public static final String COLUMN_FAV_TITLE = "title";
    public static final String COLUMN_FAV_POSTER_PATH = "poster_path";
    public static final String COLUMN_FAV_RELEASE_DATE = "release_date";
    public static final String COLUMN_FAV_RATING = "vote_average";
    public static final String COLUMN_FAV_OVERVIEW = "overview";
    public static final String COLUMN_FAV_BACKDROP_PATH = "backdrop_path";


    public static final String SQL_CREATE_FAVORITE_TABLE =
            "CREATE TABLE " + TABLE_FAV + " (" +
            COLUMN_FAV_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_MOVIE_ID + " INTEGER, " +
            COLUMN_FAV_TYPE + " TEXT, " +
            COLUMN_FAV_TITLE + " TEXT, " +
            COLUMN_FAV_POSTER_PATH + " TEXT, " +
            COLUMN_FAV_RELEASE_DATE + " TEXT, " +
            COLUMN_FAV_RATING + " REAL, " +
            COLUMN_FAV_OVERVIEW + " TEXT, " +
            COLUMN_FAV_BACKDROP_PATH + " TEXT" +
            "); ";


    public int insertFavoriteMovie(Movies favMovie){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cValues = new ContentValues();

        cValues.put(COLUMN_MOVIE_ID, favMovie.getId());
        cValues.put(COLUMN_FAV_TYPE, favMovie.getType());
        cValues.put(COLUMN_FAV_TITLE, favMovie.getTitle());
        cValues.put(COLUMN_FAV_POSTER_PATH, favMovie.getPosterPath());
        cValues.put(COLUMN_FAV_RELEASE_DATE, favMovie.getReleaseDate());
        cValues.put(COLUMN_FAV_RATING, favMovie.getRating());
        cValues.put(COLUMN_FAV_OVERVIEW, favMovie.getOverview());
        cValues.put(COLUMN_FAV_BACKDROP_PATH, favMovie.getBackdrop());

        long newRowId = db.insert(TABLE_FAV,null, cValues);
        db.close();
        if (newRowId > 0) {
            return 1;
        }else {
            return 0;
        }
    }

    public ArrayList<Movies> getFavMovies(){
        ArrayList<Movies> favoriteList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {COLUMN_FAV_ID, COLUMN_MOVIE_ID, COLUMN_FAV_TYPE, COLUMN_FAV_TITLE, COLUMN_FAV_POSTER_PATH,
                COLUMN_FAV_RELEASE_DATE, COLUMN_FAV_RATING, COLUMN_FAV_OVERVIEW, COLUMN_FAV_BACKDROP_PATH
        };
        String sortOrder = COLUMN_FAV_ID + " ASC";
        Cursor cursor = db.query(TABLE_FAV, columns, null, null, null, null, sortOrder);
        if (cursor.moveToFirst()){
            do {
                Movies movie = new Movies();
                movie.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_MOVIE_ID))));
                movie.setType(cursor.getString(cursor.getColumnIndex(COLUMN_FAV_TYPE)));
                movie.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_FAV_TITLE)));
                movie.setPosterPath(cursor.getString(cursor.getColumnIndex(COLUMN_FAV_POSTER_PATH)));
                movie.setReleaseDate(cursor.getString(cursor.getColumnIndex(COLUMN_FAV_RELEASE_DATE)));
                movie.setRating((float) Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_FAV_RATING))));
                movie.setOverview(cursor.getString(cursor.getColumnIndex(COLUMN_FAV_OVERVIEW)));
                movie.setBackdrop(cursor.getString(cursor.getColumnIndex(COLUMN_FAV_BACKDROP_PATH)));
                favoriteList.add(movie);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return favoriteList;
    }


    public int deleteFavorite(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_FAV, COLUMN_MOVIE_ID+ "=" + id, null);
    }


    public Boolean isExist(String searchItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] projection = {COLUMN_FAV_ID, COLUMN_MOVIE_ID, COLUMN_FAV_TYPE, COLUMN_FAV_TITLE, COLUMN_FAV_POSTER_PATH,
                COLUMN_FAV_RELEASE_DATE, COLUMN_FAV_RATING, COLUMN_FAV_OVERVIEW, COLUMN_FAV_BACKDROP_PATH
        };
        String selection = COLUMN_FAV_TITLE + " =?";
        String[] selectionArgs = { searchItem };
        String limit = "1";

        Cursor cursor = db.query(TABLE_FAV, projection, selection, selectionArgs,null,null,null, limit);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }


    public DBHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ALL_MOVIES_TABLE);
        db.execSQL(SQL_CREATE_FAVORITE_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALL_MOVIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAV);
        onCreate(db);
    }
}

package com.mediaplayer.zoro.mediaplayer;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;

public class SongsManager {
	// SDCard Path
	final String MEDIA_PATH = new String("/sdcard/");
	private ArrayList<Song> songList = new ArrayList<Song>();
	private Activity activity;
	
	// Constructor
	public SongsManager(Activity activity){
		this.activity = activity;
	}
	
	/**
	 * Function to read all mp3 files from sdcard
	 * and store the details in ArrayList
	 * */
	public ArrayList<Song> getPlayList(){

		String[] STAR = { "*" };
		ContentResolver musicResolver = activity.getContentResolver();
		Uri musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
		String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
		Cursor cursor = musicResolver.query(musicUri, STAR, selection, null, null);

		if (cursor != null) {
			if (cursor.moveToFirst()) {
				do {
					String song_name = cursor
							.getString(cursor
									.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
					int song_id = cursor.getInt(cursor
							.getColumnIndex(MediaStore.Audio.Media._ID));

					String fullpath = cursor.getString(cursor
							.getColumnIndex(MediaStore.Audio.Media.DATA));


					String album_name = cursor.getString(cursor
							.getColumnIndex(MediaStore.Audio.Media.ALBUM));
					int album_id = cursor.getInt(cursor
							.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));

					String artist_name = cursor.getString(cursor
							.getColumnIndex(MediaStore.Audio.Media.ARTIST));
					int artist_id = cursor.getInt(cursor
							.getColumnIndex(MediaStore.Audio.Media.ARTIST_ID));
					Song song = new Song(song_id, song_name, artist_name, fullpath);
					songList.add(song);
				} while (cursor.moveToNext());

			}
			cursor.close();
		}

		return songList;
	}
	
	/**
	 * Class to filter files which are having .mp3 extension
	 * */
	class FileExtensionFilter implements FilenameFilter {
		public boolean accept(File dir, String name) {
			return (name.endsWith(".mp3") || name.endsWith(".MP3"));
		}
	}
}

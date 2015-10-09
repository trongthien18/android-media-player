package com.mediaplayer.zoro.mediaplayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class PlayListActivity extends ListActivity {
	// Songs list
	public ArrayList<Song> songsList = new ArrayList<Song>();
	private ListView songView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.playlist);
		songView = (ListView)getListView();

		SongsManager plm = new SongsManager(this);
		// get all songs from sdcard
		this.songsList = plm.getPlayList();

		Collections.sort(this.songsList, new Comparator<Song>() {
			public int compare(Song a, Song b) {
				return a.getTitle().compareTo(b.getTitle());
			}
		});

		SongAdapter songAdt = new SongAdapter(this, songsList);
		songView.setAdapter(songAdt);

	}

	public void songPicked(View view){
		int songIndex = Integer.parseInt(view.getTag().toString());
		Intent in = new Intent(getApplicationContext(),
				AndroidBuildingMusicPlayerActivity.class);
		// Sending songIndex to PlayerActivity
		in.putExtra("songIndex", songIndex);
		setResult(100, in);
	}
}

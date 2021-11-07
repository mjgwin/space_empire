package com.gdx.game.utils;

import java.util.ArrayList;
import java.util.Arrays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class DiskLoader {

	public static ArrayList<String> loadLinesFromFile(String fileName) {
		FileHandle handle = Gdx.files.internal("game_data/" + fileName);
		ArrayList<String> lines = new ArrayList<String>();
		String text = handle.readString();
		String wordsArray[] = text.split("\\r?\\n");
		for (String word : wordsArray) {
			lines.add(word);
		}
		return lines;
	}
	
}

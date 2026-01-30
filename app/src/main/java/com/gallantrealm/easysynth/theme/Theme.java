package com.gallantrealm.easysynth.theme;

import com.gallantrealm.easysynth.R;

public class Theme {
	public String font;
	public int themeSongId;
	public int themeBackgroundId;
	public int buttonStyleId;
	public Theme() {
		font = "ThemeFont.ttf";
		themeSongId = R.raw.theme_song;
		themeBackgroundId = R.raw.theme_background;
		buttonStyleId = 0;
	}
}


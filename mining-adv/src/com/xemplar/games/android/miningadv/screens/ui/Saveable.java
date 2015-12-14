package com.xemplar.games.android.miningadv.screens.ui;

public interface Saveable {
	public String getSaveState();
	public String getKey();
	public void setFromSave(String state);
}

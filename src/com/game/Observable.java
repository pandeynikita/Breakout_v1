package com.game;

import com.game.Observer;

public interface Observable
{
	public void registerObserver(Observer observer);
    public void removeObserver(Observer observer);
    public void notifyObservers(boolean toggle);
}
package de.moja.bier_v06;

import java.util.Observable;

public class STATElistener extends Observable {
    private int someVariable = 0;

    public void set(int someVariable) {
        synchronized (this) {
            this.someVariable = someVariable;
        }
        setChanged();
        notifyObservers();
    }

    public synchronized int get() {
        return someVariable;
    }
}
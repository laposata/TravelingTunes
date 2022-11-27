package com.dreamtea.music.util;

import java.util.function.Consumer;

public abstract class Peeker<T> implements Consumer<T> {
  protected boolean outcome;

  public boolean result(){
    return outcome;
  }

  public abstract void accept(T testing);
}

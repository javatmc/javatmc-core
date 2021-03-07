package org.jtmc.core.signal;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * CompositeSignal contains multiple time correlated signals.
 */
public class CompositeSignal<T extends Comparable<T>> {

  private String id;

  private Set<Signal<T>> signals = new HashSet<>();

  public CompositeSignal(String id) {
    this.id = id;
  }

  public CompositeSignal(Signal<T> signal) {
    this(signal.getId());
    this.signals.add(signal);
  }

  protected void add(Signal<T> signal) {
    signals.add(signal);
  }

  public Optional<Signal<T>> getSignal(String id) {
    return signals.stream().filter(signal -> signal.getId().equals(id)).findFirst();
  }

  public Set<Signal<T>> getSignals() {
    return signals;
  }

  public String getId() {
    return id;
  }

}

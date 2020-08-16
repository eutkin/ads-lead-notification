package com.github.roman1306.lead.source.facebook.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Body implements Iterable<FacebookLead> {

  private List<Entry> entries = new ArrayList<>();

  @Override
  public Iterator<FacebookLead> iterator() {
    return this.entries.stream()
        .map(Entry::getChanges)
        .flatMap(Collection::stream)
        .filter(Changes::isLeadgen)
        .map(Changes::getValue)
        .filter(Objects::nonNull)
        .iterator();
  }

  @Getter
  @Setter
  public static class Entry {

    private List<Changes> changes = new ArrayList<>();
  }

  @Getter
  @Setter
  public static class Changes {

    private String field;
    private FacebookLead value;

    public boolean isLeadgen() {
      return "leadgen".equalsIgnoreCase(field);
    }
  }

}

package io.cenet.datastore.entity;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
@Cache
public class NamedEntity {

  @Id
  public String name;

  NamedEntity() {}

  public NamedEntity(final String name) {
    this.name = name;
  }
}

package io.cenet.datastore.entity;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
@Cache
public class IdEntity {

  @Id
  public Long id;
  public String searchResult;
}

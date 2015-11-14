package io.cenet.datastore.entity;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
@Cache
public class ListEntity {

  @Id
  public Long id;
  public List<String> admins = new ArrayList<String>();
}

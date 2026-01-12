package com.example.logic.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Restaurant {
  @Id private String id;
  private String name;
  private String tags;

  public Restaurant() {}
  public Restaurant(String id, String name, String tags){ this.id=id; this.name=name; this.tags=tags; }

  public String getId(){return id;}  public void setId(String id){this.id=id;}
  public String getName(){return name;} public void setName(String name){this.name=name;}
  public String getTags(){return tags;} public void setTags(String tags){this.tags=tags;}
}

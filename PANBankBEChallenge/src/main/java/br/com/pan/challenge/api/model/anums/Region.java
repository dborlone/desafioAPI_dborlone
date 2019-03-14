package br.com.pan.challenge.api.model.anums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


public enum Region {
    NORTE("Norte"),
    
    NORDESTE("Nordeste"),
    
    SUDESTE("Sudeste"),
    
    SUL("Sul"),
    
    CENTRO_OESTE("Centro-Oeste"),
    
    OESTE("Oeste");

    private String value;

    Region(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static Region fromValue(String text) {
      for (Region b : Region.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
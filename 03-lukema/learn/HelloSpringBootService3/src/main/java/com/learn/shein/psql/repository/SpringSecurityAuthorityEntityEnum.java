package com.learn.shein.psql.repository;


public enum SpringSecurityAuthorityEntityEnum {

  Admin("ROLE_ADMIN"), User("ROLE_USER");

  private String value;

  SpringSecurityAuthorityEntityEnum(String vale) {
    this.value = vale;
  }

  public String toString() {
    return value;
  }

}

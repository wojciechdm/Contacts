package com.wojciechdm.contacts;

import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
class Customer {

  private Long id;
  private String name;
  private String surname;
  private Integer age;
  private String city;
  private List<Contact> contacts;
}

package com.wojciechdm.contacts;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class Contact {

  private Long id;
  private Long customerId;
  private Integer type;
  private String contact;
}

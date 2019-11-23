package com.luizalabs.entities;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Client {

  @Id private UUID id;
  private String name;
  private String email;

  @ManyToMany
  @JoinTable(name="client_product", joinColumns=
      {@JoinColumn(name="client_id")}, inverseJoinColumns=
      {@JoinColumn(name="product_id")})
  private Set<Product> products;
}

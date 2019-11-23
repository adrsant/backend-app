package com.luizalabs.entities;

import java.util.Set;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
@EqualsAndHashCode(of = "email")
public class Client {
  @Setter @Id private UUID id;
  @NotBlank private String name;
  @NotBlank private String email;

  @ManyToMany
  @JoinTable(
      name = "client_product",
      joinColumns = {@JoinColumn(name = "client_id")},
      inverseJoinColumns = {@JoinColumn(name = "product_id")})
  private Set<Product> products;
}

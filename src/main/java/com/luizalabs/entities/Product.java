package com.luizalabs.entities;

import java.math.BigDecimal;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
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
public class Product {

  @Id private UUID id;
  private String title;
  private String brand;
  private String image;
  private BigDecimal price;
  private Double reviewScore;
}

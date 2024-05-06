package com.example.tiemgomnhala.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "wishlist")
@Getter
@Setter
public class Wishlist {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String wishlistId;

    @OneToMany
    private List<Product> wishlist;


}

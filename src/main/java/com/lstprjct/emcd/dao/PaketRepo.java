package com.lstprjct.emcd.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lstprjct.emcd.entity.Paket;
import com.lstprjct.emcd.entity.Product;

public interface PaketRepo extends JpaRepository<Paket, Integer> {

	Optional<Product> findByPaketname(String paketname);

}

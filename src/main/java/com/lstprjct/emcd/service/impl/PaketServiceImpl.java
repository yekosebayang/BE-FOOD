package com.lstprjct.emcd.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lstprjct.emcd.dao.PaketRepo;
import com.lstprjct.emcd.dao.ProductRepo;
import com.lstprjct.emcd.entity.Paket;
import com.lstprjct.emcd.service.PaketService;

@Service
// jangan lupa di dedclare dia implemen dari mana
public class PaketServiceImpl implements PaketService {
	@Autowired
	private PaketRepo paketRepo;
	
	@Autowired
	private ProductRepo productRepo;

	@Override
	public Paket addPaket(Paket paket) {
		paket.setId(0);
		return paketRepo.save(paket);
	}
	
	@Override
	public Iterable<Paket> readPaket() {
		return paketRepo.findAll();
	}

	@Override
	public void deletePaketById(int paketId) {
		Paket findPaket = paketRepo.findById(paketId).get();
		
		findPaket.getProducts().forEach(product -> {
			product.getPaket().remove(findPaket);
			productRepo.save(product);
		});
		
		findPaket.setProducts(null);
		paketRepo.deleteById(paketId);
	}

	@Override
	public Paket editPaketById(Paket paket, int paketId) {
		paket.setId(paketId);
		Optional<Paket> findPaket = paketRepo.findById(paketId);
		if (findPaket.toString() == "Optional.empty")
			throw new RuntimeException("Id not exist, ID: " + paketId);
		return paketRepo.save(paket);
	}
}

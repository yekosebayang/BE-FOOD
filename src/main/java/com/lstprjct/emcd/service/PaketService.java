package com.lstprjct.emcd.service;

import com.lstprjct.emcd.entity.Paket;

public interface PaketService {

	Paket addPaket(Paket paket);

	Iterable<Paket> readPaket();

	Paket editPaketById(Paket paket, int paketId);

	void deletePaketById(int paketId);
	
}



package com.lstprjct.emcd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lstprjct.emcd.entity.Paket;
import com.lstprjct.emcd.service.PaketService;

@RestController // dia kontroller
@RequestMapping("/Paket")
@CrossOrigin
public class PaketController {
	@Autowired
	private PaketService paketService;
	
	@PostMapping //dia post command
	@RequestMapping("/new")
	public Paket addPaket(@RequestBody Paket paket) {
		return paketService.addPaket(paket); 	
	}
	
	@GetMapping
	public Iterable<Paket> readPaket(){
		return paketService.readPaket();
	}
		
	@PutMapping("/edit/{paketId}")
	public Paket editPaketById(@RequestBody Paket paket, @PathVariable int paketId) {
		return paketService.editPaketById(paket, paketId);
	}
		
	@DeleteMapping("{paketId}")
		public void deletePaketById(@PathVariable int paketId) {
	paketService.deletePaketById(paketId);
	}

}

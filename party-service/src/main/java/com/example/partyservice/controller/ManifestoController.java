package com.example.partyservice.controller;

import com.example.partyservice.model.Manifesto;
import com.example.partyservice.service.ManifestoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/manifesto")
public class ManifestoController {

    @Autowired
    private ManifestoService manifestoService;

    // Endpoint to create a new Manifesto
    @PostMapping("/member")
    public ResponseEntity<Manifesto> createManifesto(@RequestParam String nic,@RequestBody Manifesto manifesto) {
        try{
            Manifesto createdManifesto = manifestoService.createManifesto(nic, manifesto);
            return ResponseEntity.ok(createdManifesto);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint to retrieve a Manifesto by its ID
    @GetMapping("/{id}")
    public ResponseEntity<Manifesto> getManifestoById(@PathVariable Integer id) {
        Optional<Manifesto> manifesto = manifestoService.getManifestoById(id);
        return manifesto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint to retrieve all Manifestos
    @GetMapping
    public ResponseEntity<List<Manifesto>> getAllManifestos() {
        List<Manifesto> manifestos = manifestoService.getAllManifestos();
        return ResponseEntity.ok(manifestos);
    }

    // Endpoint to update an existing Manifesto
    @PutMapping("/{id}")
    public ResponseEntity<Manifesto> updateManifesto(@PathVariable Integer id, @RequestBody Manifesto manifesto) {
        try {
            manifesto.setManifestoId(id);
            Manifesto updatedManifesto = manifestoService.updateManifesto(manifesto);
            return ResponseEntity.ok(updatedManifesto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint to delete a Manifesto by its ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteManifesto(@PathVariable Integer id) {
        try {
            manifestoService.deleteManifesto(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}

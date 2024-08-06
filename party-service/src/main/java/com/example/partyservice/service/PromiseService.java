package com.example.partyservice.service;

import com.example.partyservice.model.Manifesto;
import com.example.partyservice.model.Promise;
import com.example.partyservice.repository.ManifestoRepository;
import com.example.partyservice.repository.PromiseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PromiseService {

    @Autowired
    private PromiseRepository promiseRepository;

    @Autowired
    private ManifestoRepository manifestoRepository;

    //Method to create a new promise
    public Promise createPromise(Integer manifestoId, Promise promise) throws Exception {
        Manifesto manifesto = manifestoRepository.findById(manifestoId)
                .orElseThrow(() -> new Exception("Manifesto not found"));

        promise.setManifesto(manifesto);

        return promiseRepository.save(promise);
    }

}

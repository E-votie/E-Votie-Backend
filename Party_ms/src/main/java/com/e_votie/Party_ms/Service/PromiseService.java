package com.e_votie.Party_ms.Service;

import com.e_votie.Party_ms.Model.Manifesto;
import com.e_votie.Party_ms.Model.Promise;
import com.e_votie.Party_ms.Repository.ManifestoRepository;
import com.e_votie.Party_ms.Repository.PromiseRepository;
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
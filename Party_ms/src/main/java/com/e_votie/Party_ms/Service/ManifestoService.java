package com.e_votie.Party_ms.Service;

import com.e_votie.Party_ms.Model.Manifesto;
import com.e_votie.Party_ms.Model.PartyMember;
import com.e_votie.Party_ms.Repository.ManifestoRepository;
import com.e_votie.Party_ms.Repository.PartyMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManifestoService {

//    @Autowired
//    private ManifestoRepository manifestoRepository;
//
//    @Autowired
//    private PartyMemberRepository partyMemberRepository;
//
//    // Method to create a new Manifesto
//    public Manifesto createManifesto(String nic, Manifesto manifesto) throws Exception {
//
//        Optional<PartyMember> partyMemberOptional = partyMemberRepository.findByNIC(nic);
//
//        if(partyMemberOptional.isPresent()) {
//            PartyMember partyMember = partyMemberOptional.get();
//
//            manifesto.setPartyMember(partyMember);
//            Manifesto savedManifesto = manifestoRepository.save(manifesto);
//
//            partyMember.setManifesto(manifesto);
//            partyMemberRepository.save(partyMember);
//
//            return savedManifesto;
//        }else {
//            throw new Exception("Party Member with NIC " + manifesto.getPartyMember().getNIC() + " not found");
//        }
//
//    }
//
//    // Method to retrieve a Manifesto by its ID
//    public Optional<Manifesto> getManifestoById(Integer manifestoId) {
//        return manifestoRepository.findById(manifestoId);
//    }
//
//    // Method to retrieve all Manifestos
//    public List<Manifesto> getAllManifestos() {
//        return manifestoRepository.findAll();
//    }
//
//    // Method to update an existing Manifesto
//    public Manifesto updateManifesto(Manifesto manifesto) throws Exception {
//        Optional<Manifesto> existingManifestoOptional = manifestoRepository.findById(manifesto.getManifestoId());
//        if (existingManifestoOptional.isPresent()) {
//            Manifesto existingManifesto = existingManifestoOptional.get();
//            existingManifesto.setProgress(manifesto.getProgress());
//            return manifestoRepository.save(existingManifesto);
//        } else {
//            throw new Exception("Manifesto with ID " + manifesto.getManifestoId() + " not found");
//        }
//    }
//
//    // Method to delete a Manifesto by its ID
//    public void deleteManifesto(Integer manifestoId) throws Exception {
//        if (manifestoRepository.existsById(manifestoId)) {
//            manifestoRepository.deleteById(manifestoId);
//        } else {
//            throw new Exception("Manifesto with ID " + manifestoId + " not found");
//        }
//    }
}
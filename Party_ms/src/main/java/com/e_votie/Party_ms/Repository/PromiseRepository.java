package com.e_votie.Party_ms.Repository;

import com.e_votie.Party_ms.Model.Promise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromiseRepository extends JpaRepository<Promise, Integer> {
}

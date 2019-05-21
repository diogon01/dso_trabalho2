package com.trocas.dso2.repository;

import com.trocas.dso2.domain.AnuncioHistorico;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AnuncioHistorico entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnuncioHistoricoRepository extends JpaRepository<AnuncioHistorico, Long> {

}

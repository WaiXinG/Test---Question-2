package com.assessment.klsestock.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.assessment.klsestock.entity.KlseStock;


public interface IKlseStockRepo extends JpaRepository<KlseStock, String>, JpaSpecificationExecutor<KlseStock>{

}

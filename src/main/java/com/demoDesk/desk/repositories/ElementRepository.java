package com.demoDesk.desk.repositories;

import com.demoDesk.desk.models.nomenclature.Element;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface ElementRepository extends JpaRepository<Element, Long>, JpaSpecificationExecutor<Element> {

}

package com.demoDesk.desk.services;

import com.demoDesk.desk.models.Department;
import com.demoDesk.desk.models.Element;
import com.demoDesk.desk.models.Product;
import com.demoDesk.desk.repositories.DepartmentRepository;
import com.demoDesk.desk.repositories.ElementRepository;
import com.demoDesk.desk.repositories.ProductRepository;
import com.demoDesk.desk.repositories.specifications.ElementSpec;
import com.demoDesk.desk.repositories.specifications.ProductSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ElementService {

    private ElementRepository elementRepository;
    private DepartmentRepository departmentRepository;
    @Autowired
    public void setElementRepository(ElementRepository elementRepository) {
        this.elementRepository = elementRepository;
    }
    @Autowired
    public void setDepartmentRepository(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<Element> getElementsWithFiltering(Specification<Element> specification) {
        return elementRepository.findAll(specification);
    }

    public List<Element> getAllElements() {
        return elementRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Element getElementById(Long id) {
        return elementRepository.findOne(ElementSpec.findById(id)).orElse(null);
    }

    @Transactional
    public void deleteById(Long id) {
        elementRepository.deleteById(id);
    }

    @Transactional
    public void saveElement(Element element) {
        elementRepository.save(element);
    }

    @Transactional(readOnly = true)
    public String getDepartmentTitle(Element element) {
        return departmentRepository.findById(element.getDepartmentId()).get().getTitle();
    }
}

package com.demoDesk.desk.services;

import com.demoDesk.desk.dto.elementDto.ElementEditOrAddDto;
import com.demoDesk.desk.models.nomenclature.Element;
import com.demoDesk.desk.models.personnel.Department;
import com.demoDesk.desk.repositories.DepartmentRepository;
import com.demoDesk.desk.repositories.ElementRepository;
import com.demoDesk.desk.repositories.specifications.ElementSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ElementService {

    private final ElementRepository elementRepository;
    private final DepartmentRepository departmentRepository;

    public List<Element> getElementsWithFiltering(Specification<Element> specification) {
        return elementRepository.findAll(specification);
    }

    public List<Element> getAllElements() {
        return elementRepository.findAll();
    }

    public Element getElementById(Long id) {
        return elementRepository.findOne(ElementSpec.findById(id)).orElse(null);
    }

    @Transactional
    public void deleteById(Long id) {
        elementRepository.deleteById(id);
    }


    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    @Transactional
    public boolean editOrAddElementConfirm(ElementEditOrAddDto elementEditOrAddDto) {
        Element element = elementEditOrAddDto.getElement();
        Element incomingElement = elementRepository.findById(element.getId()).orElse(new Element());
        incomingElement.setArt(element.getArt());
        incomingElement.setDepartment(departmentRepository.getReferenceById(elementEditOrAddDto.getDepartmentId()));
        incomingElement.setTitle(element.getTitle());
        incomingElement.setDescription(element.getDescription());
        elementRepository.save(incomingElement);
        return true;
    }
}

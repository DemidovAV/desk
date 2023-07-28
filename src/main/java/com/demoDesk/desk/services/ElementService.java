package com.demoDesk.desk.services;

import com.demoDesk.desk.dto.elementDto.ElementEditOrAddDto;
import com.demoDesk.desk.models.nomenclature.Element;
import com.demoDesk.desk.models.nomenclature.Product;
import com.demoDesk.desk.models.personel.Department;
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

    @Transactional
    public void saveElement(Element element) {
        elementRepository.save(element);
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    /**
     * Тут по примеру с TicketController можно совместить с /editElement/confirm, нет смысла городить кучу точек входа
     * вот пример, тут производится  поиск записи в базе, если в базе нет, то создается новая, затем мы ее заполняем
     * данными пришедшими с фронта и сохраняем/обновляем запись в бд
     * InsuranceCompanyEntity entity = jpaInsuranceCompanyRepository.findById(insuranceCompany.getId())
     *             .orElse(new InsuranceCompanyEntity());
     *         entity.setId(insuranceCompany.getId());
     *         entity.setName(insuranceCompany.getName());
     *
     *         jpaInsuranceCompanyRepository.save(entity);
     */

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

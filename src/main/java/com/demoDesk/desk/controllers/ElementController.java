package com.demoDesk.desk.controllers;

import com.demoDesk.desk.dto.elementDto.ShowElementsDto;
import com.demoDesk.desk.models.nomenclature.Element;
import com.demoDesk.desk.models.personel.Department;
import com.demoDesk.desk.repositories.specifications.ElementSpec;
import com.demoDesk.desk.services.ElementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/elements")
public class ElementController {
    private ElementService elementService;

    @Autowired
    public void setElementService(ElementService elementService) {
        this.elementService = elementService;
    }

    private Specification<Element> filtration(String filter, String art) {
        Specification<Element> spec = Specification.where(null);

        if(filter != null) {
            spec = spec.and(ElementSpec.titleContains(filter));
        }
        if (art != null) {
            spec = spec.and(ElementSpec.artContains(art));
        }
        return spec;

    }

    @GetMapping
    public ShowElementsDto showElements(@RequestParam(value = "filter", required = false) String filter,
                                        @RequestParam(value = "art", required = false) String art) {
        ShowElementsDto showElementsDto = new ShowElementsDto();
        showElementsDto.setElementList(elementService.getElementsWithFiltering(filtration(filter, art)));
        showElementsDto.setFilter(filter);
        showElementsDto.setArt(art);

        return showElementsDto;
    }

    @PostMapping("/reset")
    public ShowElementsDto showElementListReset(Model model) {
        ShowElementsDto showElementsDtoReset = new ShowElementsDto();
        showElementsDtoReset.setElementList(elementService.getAllElements());
        showElementsDtoReset.setFilter(null);
        showElementsDtoReset.setArt(null);
        return showElementsDtoReset;
    }
    @GetMapping("/editElement/{id}")
    public Element showEditElement(@PathVariable(value="id") Long id){
        return elementService.getElementById(id);
    }

    @PostMapping("/editElement/confirm")
    public boolean editElementConfirm(@RequestBody Element element) {
        Element editableElement = elementService.getElementById(element.getId());
        editableElement.setArt(element.getArt());
        editableElement.setDepartment(element.getDepartment());
        editableElement.setTitle(element.getTitle());
        editableElement.setDescription(element.getDescription());
        elementService.saveElement(editableElement);
        return true;
    }


    @GetMapping("/addElement")
    public List<Department> addNewElement() {
        return elementService.getAllDepartments();
    }
    //
    @PostMapping("/addElement/confirm")
    public boolean addElementConfirm(@RequestBody Element element) {
        elementService.saveElement(element);
        return true;
    }

    @GetMapping("/showElement/{id}")
    public Element showOneElement(@PathVariable(value="id") Long id) {
        return elementService.getElementById(id);
    }

    @GetMapping("/deleteElement/{id}")
    public boolean deleteElement(@PathVariable(value="id") Long id) {
        elementService.deleteById(id);
        return true;
    }
}

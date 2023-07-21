package com.demoDesk.desk.controllers;

import com.demoDesk.desk.dto.elementDto.ShowElementsDto;
import com.demoDesk.desk.models.nomenclature.Element;
import com.demoDesk.desk.models.personel.Department;
import com.demoDesk.desk.repositories.specifications.ElementSpec;
import com.demoDesk.desk.services.ElementService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/elements")
@RequiredArgsConstructor
public class ElementController {
    private final ElementService elementService;

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
    public ShowElementsDto showElementListReset() {
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

    @GetMapping("/addElement")
    public List<Department> addNewElement() {
        return elementService.getAllDepartments();
    }

    @PostMapping("/editOrAddElementConfirm")
    public boolean editOrAddElementConfirm(@RequestBody Element element) {
        return elementService.editOrAddElementConfirm(element);
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

package com.demoDesk.desk.controllers;

import com.demoDesk.desk.dto.elementDto.ShowElements;
import com.demoDesk.desk.models.nomenclature.Element;
import com.demoDesk.desk.models.nomenclature.Product;
import com.demoDesk.desk.repositories.specifications.ElementSpec;
import com.demoDesk.desk.repositories.specifications.ProductSpec;
import com.demoDesk.desk.services.ElementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
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
    public ShowElements showElements(@RequestParam(value = "filter", required = false) String filter,
                                     @RequestParam(value = "art", required = false) String art) {
        ShowElements showElements = new ShowElements();
        showElements.setElementList(elementService.getElementsWithFiltering(filtration(filter, art)));
        showElements.setFilter(filter);
        showElements.setArt(art);

        return showElements;
    }

    @PostMapping("/reset")
    public ShowElements showElementListReset(Model model) {
        ShowElements showElementsReset = new ShowElements();
        showElementsReset.setElementList(elementService.getAllElements());
        showElementsReset.setFilter(null);
        showElementsReset.setArt(null);
        return showElementsReset;
    }
    @GetMapping("/editElement/{id}")
    public Element showEditElement(@PathVariable(value="id") Long id){
        return elementService.getElementById(id);
    }

    @PostMapping("/editElement/confirm")
    public boolean editElementConfirm(@ModelAttribute(value="element") Element element) {
        elementService.saveElement(element);
        return true;
    }


    @GetMapping("/addElement")
    public Element addElement(Model model) {
        return new Element();
    }
    //
    @PostMapping("/addElement/confirm")
    public boolean addElementConfirm(@ModelAttribute(value="element") Element element) {
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

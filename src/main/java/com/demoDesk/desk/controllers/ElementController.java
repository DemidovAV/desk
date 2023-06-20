package com.demoDesk.desk.controllers;

import com.demoDesk.desk.models.Element;
import com.demoDesk.desk.models.Product;
import com.demoDesk.desk.repositories.specifications.ElementSpec;
import com.demoDesk.desk.repositories.specifications.ProductSpec;
import com.demoDesk.desk.services.ElementService;
import com.demoDesk.desk.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/elements")
public class ElementController {
    private ElementService elementService;

    @Autowired
    public void setElementService(ElementService elementService) {
        this.elementService = elementService;
    }

    @GetMapping
    public String showElements(Model model,
                               @RequestParam(value = "filter", required = false) String filter) {
        Element element = new Element();
        Specification<Element> spec = Specification.where(null);

        if(filter != null) {
            spec = spec.and(ElementSpec.titleContains(filter));
        }
        List<Element> filteredElements = elementService.getElementsWithFiltering(spec);

        model.addAttribute("element", element);
        model.addAttribute("elements", filteredElements);
        model.addAttribute("filter", filter);
        return "elements";
    }

    @PostMapping("/reset")
    public String showElementListReset(Model model) {
        Element element = new Element();
        String filter = null;
        model.addAttribute("elements", elementService.getAllElements());
        model.addAttribute("element", element);
        model.addAttribute("filter", filter);
        return "redirect:/elements";
    }
    @GetMapping("/editElement/{id}")
    public String showEditElement(Model model, @PathVariable(value="id") Long id){
        Element element = elementService.getElementById(id);
        model.addAttribute("element", element);
        return "edit-element";
    }

    @PostMapping("/editElement/confirm")
    public String editElementConfirm(@ModelAttribute(value="element") Element element) {
        elementService.saveElement(element);
        return "redirect:/elements";
    }


    @GetMapping("/addElement")
    public String addElement(Model model) {
        Element element = new Element();
        model.addAttribute("element", element);
        return "add-element";
    }
    //
    @PostMapping("/addElement/confirm")
    public String addElementConfirm(@ModelAttribute(value="element") Element element) {
        elementService.saveElement(element);
        return "redirect:/elements";
    }

    @GetMapping("/showElement/{id}")
    public String showOneElement(Model model, @PathVariable(value="id") Long id) {
        Element element = elementService.getElementById(id);
        model.addAttribute("element", element);
        return "show-element";
    }

    @GetMapping("/deleteElement/{id}")
    public String deleteElement(@PathVariable(value="id") Long id) {
        elementService.deleteById(id);
        return "redirect:/elements";
    }
}

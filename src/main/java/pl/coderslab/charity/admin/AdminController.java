package pl.coderslab.charity.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.charity.category.Category;
import pl.coderslab.charity.category.CategoryService;
import pl.coderslab.charity.institution.Institution;
import pl.coderslab.charity.institution.InstitutionService;

import javax.validation.Valid;

@RequestMapping("/admin")
@Controller
public class AdminController {

    private final CategoryService categoryService;
    private final InstitutionService institutionService;

    private static final String CATEGORIES = "categories";
    private static final String INSTITUTIONS = "institutions";

    public AdminController(CategoryService categoryService, InstitutionService institutionService) {
        this.categoryService = categoryService;
        this.institutionService = institutionService;
    }

    @RequestMapping("")
    public String initAdminPanel(){
        return "/admin/panel";
    }

    @RequestMapping("/categories")
    public String initCategoriesView(Model model){
        model.addAttribute(CATEGORIES, categoryService.findAll());
        return "/admin/categories";
    }

    @RequestMapping("/institutions")
    public String initInstitutionView(Model model){
        model.addAttribute(INSTITUTIONS, institutionService.findAll());
        return "/admin/institutions";
    }

    @PostMapping("/category/edit")
    public String editCategory(Category category){
        categoryService.save(category);
        return "redirect:/admin/categories";
    }

    @PostMapping("/institution/edit")
    public String editInstitution(Institution institution){
        institutionService.save(institution);
        return "redirect:/admin/institutions";
    }
}

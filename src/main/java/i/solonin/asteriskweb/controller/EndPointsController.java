package i.solonin.asteriskweb.controller;

import i.solonin.asteriskweb.converter.EntityConverter;
import i.solonin.asteriskweb.model.AoR;
import i.solonin.asteriskweb.model.Auth;
import i.solonin.asteriskweb.model.EndPoint;
import i.solonin.asteriskweb.model.Identify;
import i.solonin.asteriskweb.model.filter.EndPointFilter;
import i.solonin.asteriskweb.repository.AorRepository;
import i.solonin.asteriskweb.repository.AuthRepository;
import i.solonin.asteriskweb.repository.IdentifyRepository;
import i.solonin.asteriskweb.service.EndPointService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static i.solonin.asteriskweb.controller.EndPointsController.FILTER_NAME;

@Slf4j
@Controller
@RequiredArgsConstructor
@SessionAttributes(FILTER_NAME)
@RequestMapping("/endpoints")
public class EndPointsController {
    static final String FILTER_NAME = "END_POINTS_FILTER";

    private final EndPointService endPointService;
    private final AorRepository aorRepository;
    private final AuthRepository authRepository;
    private final IdentifyRepository identifyRepository;

    @ModelAttribute(FILTER_NAME)
    public EndPointFilter filter() {
        return new EndPointFilter();
    }

    @InitBinder
    public void binder(WebDataBinder binder) {
        binder.registerCustomEditor(EndPoint.class, new EntityConverter<>(endPointService::findById));
        binder.registerCustomEditor(AoR.class, new EntityConverter<>(s -> aorRepository.findById(s).orElse(null)));
        binder.registerCustomEditor(Auth.class, new EntityConverter<>(s -> authRepository.findById(s).orElse(null)));
        binder.registerCustomEditor(Identify.class, new EntityConverter<>(s -> identifyRepository.findById(s).orElse(null)));
    }

    @RequestMapping(params = "reset")
    public String reset(SessionStatus status) {
        status.setComplete();
        return "redirect:/endpoints";
    }

    @RequestMapping
    public String list(@ModelAttribute(FILTER_NAME) EndPointFilter filter,
                       @RequestParam(value = "o", defaultValue = "id") String order,
                       @RequestParam(value = "a", defaultValue = "ASC") Sort.Direction asc,
                       @RequestParam(value = "p", defaultValue = "1") int page,
                       Model model) {
        model.addAttribute("result", endPointService.findAll(filter, order, asc.equals(Sort.Direction.DESC), page - 1));
        model.addAttribute("o", order);
        model.addAttribute("asc", asc);
        model.addAttribute("page", page);
        model.addAttribute("filter", filter);
        return "endpoint/list";
    }

    @RequestMapping("/add")
    public String add(Model model) {
        model.addAttribute("endPoint", new EndPoint());
        return "endpoint/add";
    }

    @RequestMapping("/{id}/edit")
    public String edit(@PathVariable("id") EndPoint endPoint, Model model) {
        endPoint.setNew(false);
        model.addAttribute("endPoint", endPoint);
        return "endpoint/edit";
    }

    @RequestMapping(value = {"/add", "/{id}/edit", "/copy"}, params = "save", method = RequestMethod.POST)
    public String save(@Valid @ModelAttribute("endPoint") EndPoint endPoint, BindingResult br, RedirectAttributes ra) {
        if (validate(endPoint, br))
            return "endpoint/add";
        else
            endPointService.save(endPoint);
        ra.addFlashAttribute("successMessage", "Изменения применены");
        return "redirect:/endpoints";
    }

    @RequestMapping(value = "{id}/delete", method = RequestMethod.POST)
    public String delete(@PathVariable("id") EndPoint endPoint, RedirectAttributes ra) {
        endPointService.delete(endPoint);
        ra.addFlashAttribute("successMessage", "Аккаунт " + endPoint.getId() + " удалён");
        return "redirect:/endpoints";
    }

    private boolean validate(EndPoint endPoint, BindingResult br) {
        if (endPoint.isNew() && endPointService.findById(endPoint.getId()) != null)
            br.rejectValue("id", "NotFound", "Такой ID уже существует");
        if (br.hasErrors()) endPoint.setId(null);
        return br.hasErrors();
    }
}

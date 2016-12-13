package nl.unionsoft.sysstate.web.mvc.controller;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.Valid;

import nl.unionsoft.sysstate.common.dto.TextDto;
import nl.unionsoft.sysstate.common.logic.TextLogic;
import nl.unionsoft.sysstate.domain.Text;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller()
public class TextController {
    @Inject
    @Named("textLogic")
    private TextLogic textLogic;

    @RequestMapping(value = "/text/index", method = RequestMethod.GET)
    public ModelAndView list() {
        final ModelAndView modelAndView = new ModelAndView("list-text-manager");
        modelAndView.addObject("texts", textLogic.getTexts());
        return modelAndView;
    }

    @RequestMapping(value = "/text/create", method = RequestMethod.GET)
    public ModelAndView getCreate() {
        final ModelAndView modelAndView = new ModelAndView("create-update-text-manager");
        modelAndView.addObject("text", new Text());
        return modelAndView;
    }

    @RequestMapping(value = "/text/{name}/update", method = RequestMethod.GET)
    public ModelAndView getUpdate(@PathVariable("name") final String name) {
        final ModelAndView modelAndView = new ModelAndView("create-update-text-manager");
        modelAndView.addObject("text", textLogic.getText(name));
        return modelAndView;
    }

    @RequestMapping(value = "/text/{name}/delete", method = RequestMethod.GET)
    public ModelAndView getDelete(@PathVariable("name") final String name) {
        final ModelAndView modelAndView = new ModelAndView("delete-text-manager");
        modelAndView.addObject("text", textLogic.getText(name));
        return modelAndView;
    }

    @RequestMapping(value = "/text/{name}/delete", method = RequestMethod.POST)
    public ModelAndView handleDelete(@PathVariable("name") final String name) {
        textLogic.delete(name);
        return new ModelAndView("redirect:/filter/index.html");
    }

    @RequestMapping(value = "/text/create", method = RequestMethod.POST)
    public ModelAndView handleFormCreate(@Valid @ModelAttribute("text") final TextDto text, final BindingResult bindingResult) {

        ModelAndView modelAndView = null;
        if (bindingResult.hasErrors()) {
            modelAndView = new ModelAndView("create-update-text-manager");
        } else {
            textLogic.createOrUpdateText(text);
            modelAndView = new ModelAndView("redirect:/filter/index.html");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/text/{textId}/update", method = RequestMethod.POST)
    public ModelAndView handleFormUpdate(@Valid @ModelAttribute("text") final TextDto text, final BindingResult bindingResult) {
        return handleFormCreate(text, bindingResult);
    }
}

package nl.unionsoft.sysstate.web.mvc.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import nl.unionsoft.sysstate.Constants;
import nl.unionsoft.sysstate.common.dto.FilterDto;
import nl.unionsoft.sysstate.common.dto.TemplateDto;
import nl.unionsoft.sysstate.common.dto.ViewDto;
import nl.unionsoft.sysstate.common.logic.EnvironmentLogic;
import nl.unionsoft.sysstate.common.logic.ProjectLogic;
import nl.unionsoft.sysstate.common.logic.TemplateLogic;
import nl.unionsoft.sysstate.logic.FilterLogic;
import nl.unionsoft.sysstate.logic.PluginLogic;
import nl.unionsoft.sysstate.logic.ViewLogic;

@Controller()
public class ViewController {

    @Inject
    @Named("viewLogic")
    private ViewLogic viewLogic;

    @Inject
    @Named("projectLogic")
    private ProjectLogic projectLogic;

    @Inject
    @Named("environmentLogic")
    private EnvironmentLogic environmentLogic;

    @Inject
    @Named("filterLogic")
    private FilterLogic filterLogic;

    @Inject
    @Named("templateLogic")
    private TemplateLogic templateLogic;

    @Inject
    @Named("pluginLogic")
    private PluginLogic pluginLogic;


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public void renderSlash(HttpServletResponse response, HttpServletRequest request) {
        renderIndex(response, request);
    }

    
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public void renderIndex(HttpServletResponse response, HttpServletRequest request) {

        Properties viewConfiguration = pluginLogic.getPluginProperties(Constants.SYSSTATE_PLUGIN_NAME);

        String defaultViewProperty = viewConfiguration.getProperty("defaultView");
        ViewDto view = null;
        if (StringUtils.isNotEmpty(defaultViewProperty)) {
            Optional<ViewDto> optView = viewLogic.getView(defaultViewProperty);
            if (optView.isPresent()){
                view = optView.get();    
            }
        }
        if (view == null){
            view = viewLogic.getBasicView();
        }
        writeTemplateForView(response, request, view);
    }

    @RequestMapping(value = "/view/{name}/index.html", method = RequestMethod.GET)
    public void renderIndexView(@PathVariable("name") String name, HttpServletRequest request, HttpServletResponse response) {
        final Optional<ViewDto> optView = viewLogic.getView(name);
        if (optView.isPresent()){
            writeTemplateForView(response, request, optView.get());
        } else {
            writeTemplateForView(response, request, viewLogic.getBasicView());    
        }
    }

    private void writeTemplateForView(HttpServletResponse response, HttpServletRequest request, final ViewDto view) {
        TemplateDto template = view.getTemplate();
        try {

            response.addHeader("Content-Type", template.getContentType());
            Map<String, Object> context = new HashMap<String, Object>();
            if (template.getIncludeViewResults()){
                context.put("viewResult", viewLogic.getViewResults(view));    
            }
            context.put("contextPath", request.getContextPath());
            context.put("view", view);
            templateLogic.writeTemplate(template, context, response.getWriter());
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/view/index", method = RequestMethod.GET)
    public ModelAndView index() {
        final ModelAndView modelAndView = new ModelAndView("list-view-manager");
        modelAndView.addObject("views", viewLogic.getViews());
        return modelAndView;
    }

    @RequestMapping(value = "/view/create", method = RequestMethod.GET)
    public ModelAndView getCreate() {
        final ModelAndView modelAndView = new ModelAndView("create-update-view-manager");
        final ViewDto view = new ViewDto();
        view.setFilter(new FilterDto());
        modelAndView.addObject("view", view);
        addCommons(modelAndView);
        return modelAndView;
    }

    private void addCommons(final ModelAndView modelAndView) {
        modelAndView.addObject("templates", templateLogic.getTemplates());
        modelAndView.addObject("filters", filterLogic.getFilters());
    }

    @RequestMapping(value = "/view/{name}/update", method = RequestMethod.GET)
    public ModelAndView getUpdate(@PathVariable("name") final String name) {
        final ModelAndView modelAndView = new ModelAndView("create-update-view-manager");
        modelAndView.addObject("view", viewLogic.getView(name));
        addCommons(modelAndView);
        return modelAndView;
    }

    @RequestMapping(value = "/view/{name}/delete", method = RequestMethod.GET)
    public ModelAndView getDelete(@PathVariable("name") final String name) {
        final ModelAndView modelAndView = new ModelAndView("delete-view-manager");
        modelAndView.addObject("view", viewLogic.getView(name));
        return modelAndView;
    }

    @RequestMapping(value = "/view/{name}/delete", method = RequestMethod.POST)
    public ModelAndView handleDelete(@PathVariable("name") final String name) {
        viewLogic.delete(name);
        return new ModelAndView("redirect:/view/index.html");
    }

    @RequestMapping(value = "/view/create", method = RequestMethod.POST)
    public ModelAndView handleFormCreate(@Valid @ModelAttribute("view") final ViewDto view, final BindingResult bindingResult) {

        ModelAndView modelAndView = null;
        if (bindingResult.hasErrors()) {
            modelAndView = new ModelAndView("create-update-view-manager");
            addCommons(modelAndView);
        } else {
            viewLogic.createOrUpdateView(view);
            modelAndView = new ModelAndView("redirect:/view/index.html");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/view/{viewId}/update", method = RequestMethod.POST)
    public ModelAndView handleFormUpdate(@Valid @ModelAttribute("view") final ViewDto view, final BindingResult bindingResult) {
        return handleFormCreate(view, bindingResult);
    }

}

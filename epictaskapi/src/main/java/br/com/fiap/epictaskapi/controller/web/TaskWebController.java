package br.com.fiap.epictaskapi.controller.web;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.fiap.epictaskapi.model.Task;
import br.com.fiap.epictaskapi.service.TaskService;

@Controller
@RequestMapping("/task")
public class TaskWebController {

    @Autowired
    TaskService service;
    
    @GetMapping
    public ModelAndView index(){
        ModelAndView mav = new ModelAndView("/task/undone");
        mav.addObject("tasks", service.listAll());
        return mav;
    }

    @GetMapping("new")
    public ModelAndView createForm(){
        return new ModelAndView("/task/form").addObject("task", new Task());
    }

    @PostMapping
    public String create(@Valid Task task, BindingResult binding, RedirectAttributes redirect){
        if (binding.hasErrors()) return "/task/form";
        service.save(task);
        redirect.addFlashAttribute("message", "Tarefa cadastrada com sucesso");
        return "redirect:/task";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirect){
        service.remove(id);
        redirect.addFlashAttribute("message", "Tarefa apagada com sucesso");
        return "redirect:/task";
    }

    @GetMapping("update/{id}")
    public ModelAndView update(@PathVariable Long id){
        Optional<Task> optional = service.get(id);
        if (optional.isPresent()){
            return new ModelAndView("task/form")
                .addObject("task", optional.get());
        }
        // TODO tratar o erro
        return new ModelAndView("/task/undone");
    }

}

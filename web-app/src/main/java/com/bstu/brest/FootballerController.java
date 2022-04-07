package com.bstu.brest;

import com.bstu.brest.model.Footballer;
import com.bstu.brest.service.FootballerDtoService;
import com.bstu.brest.service.FootballerService;
import com.bstu.brest.service.TeamService;
import com.bstu.brest.validators.FootballerValidator;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class FootballerController {

    private FootballerService footballerService;
    private TeamService teamService;
    private  FootballerValidator footballerValidator;
    private FootballerDtoService footballerDtoService;

    public FootballerController(FootballerService footballerService,TeamService teamService,FootballerValidator footballerValidator,FootballerDtoService footballerDtoService) {
        this.footballerService=footballerService;
        this.teamService=teamService;
        this.footballerValidator=footballerValidator;
        this.footballerDtoService=footballerDtoService;
    }

    /*@GetMapping(value = "/footballers")
    public String footballers(Model model) {
        model.addAttribute("footballers",footballerDtoService.findAllWithTeamName());
        return "footballers";
    }*/

    @GetMapping(value = "/footballers")
    public String footballers(@RequestParam(value = "fromDate", required = false)
                                  @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
                              @RequestParam(value = "toDate", required = false)
                                  @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate, Model model) {
        model.addAttribute("footballers",footballerDtoService.findAllWithTeamNameWithDateFilter(fromDate,toDate));
        model.addAttribute("fromDate",fromDate);
        model.addAttribute("toDate",toDate);
        return "footballers";
    }

    @GetMapping(value = "/footballer/{id}")
    public String gotoEditFootballerById(@PathVariable Integer id, Model model) {
        model.addAttribute("isNew",false);
        model.addAttribute("teams",teamService.findAll());
        model.addAttribute("footballer",footballerService.getFootballerById(id));
        return "footballer";
    }
    @GetMapping(value = "/footballer")
    public String gotoAddNewFootballer( Model model) {
        model.addAttribute("isNew",true);
        model.addAttribute("teams",teamService.findAll());
        model.addAttribute("footballer",new Footballer());
        return "footballer";
    }
    @PostMapping(value = "/footballer")
    public String addNewFootballer(Footballer footballer, BindingResult result,Model model) {

        footballerValidator.validate(footballer,result);
        if (result.hasErrors()) {
            model.addAttribute("teams",teamService.findAll());
            return "footballer";
        }

        this.footballerService.create(footballer);
        return "redirect:/footballers";
    }
    @PostMapping(value = "/footballer/{id}")
    public String editTeam(Footballer footballer, BindingResult result,Model model) {

        footballerValidator.validate(footballer,result);
        if (result.hasErrors()) {
            model.addAttribute("teams",teamService.findAll());
            return "footballer";
        }

        this.footballerService.update(footballer);
        return "redirect:/footballers";
    }
    @GetMapping(value = "/footballer/{id}/delete")
    public final String deleteFootballerById(@PathVariable Integer id, Model model) {

        //logger.debug("delete({},{})", id, model);
        footballerService.delete(id);
        return "redirect:/footballers";
    }
}

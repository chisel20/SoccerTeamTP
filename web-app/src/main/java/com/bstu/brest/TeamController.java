package com.bstu.brest;

import com.bstu.brest.model.Team;
import com.bstu.brest.service.TeamDtoService;
import com.bstu.brest.service.TeamService;
import com.bstu.brest.validators.TeamValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Hello MVC controller.
 */
@Controller
public class TeamController {

    private final TeamService teamService;
    private final TeamDtoService teamDtoService;
    private final TeamValidator teamValidator;

    public TeamController(TeamService teamService, TeamDtoService teamDtoService, TeamValidator teamValidator) {
        this.teamService = teamService;
        this.teamDtoService = teamDtoService;
        this.teamValidator = teamValidator;
    }

    @GetMapping(value = "/team/{id}")
    public final String gotoEditTeam(@PathVariable Integer id, Model model) {
        //logger.debug("gotoEditDepartmentPage(id:{},model:{})", id, model);
        model.addAttribute("isNew", false);
        model.addAttribute("team", teamService.getTeamById(id));
        return "team";
    }
    @GetMapping(value = "/team")
    public final String gotoAddNewTeam(Model model) {
        //logger.debug("gotoEditDepartmentPage(id:{},model:{})", id, model);
        model.addAttribute("isNew", true);
        model.addAttribute("team", new Team());
        return "team";
    }
    @PostMapping(value = "/team")
    public String addNewTeam(Team team, BindingResult result,Model model) {

       teamValidator.validate(team, result);

       if (result.hasErrors()) {
           return "team";
       }

        try{
            this.teamService.create(team);
        }catch (IllegalArgumentException e){
            result.rejectValue("teamName","teamName.sameNameExist");
            return "team";
        }
        return "redirect:/teams";
    }
    @PostMapping(value = "/team/{id}")
    public String editTeam(Team team, BindingResult result) {

        teamValidator.validate(team, result);

        if (result.hasErrors()) {
            return "team";
        }

        this.teamService.update(team);
        return "redirect:/teams";
    }
    @GetMapping(value = "/teams")
    public String teams(Model model) {
        model.addAttribute("teams", teamDtoService.findAllWithNumberOfPlayersAndAvrSalarySql());
        return "teams";
    }
    @GetMapping(value = "/team/{id}/delete")
    public final String deleteTeamById(@PathVariable Integer id, Model model) {

        //logger.debug("delete({},{})", id, model);
        teamService.delete(id);
        return "redirect:/teams";
    }
}
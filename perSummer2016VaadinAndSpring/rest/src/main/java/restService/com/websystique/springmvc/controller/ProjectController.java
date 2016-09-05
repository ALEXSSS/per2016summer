package restService.com.websystique.springmvc.controller;


import com.psc.model.*;
import com.psc.repository.BranchRepository;
import com.psc.repository.CommandHistRepository;
import com.psc.repository.CommandStatusRepository;
import com.psc.repository.RegionRepository;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.orm.jpa.SharedEntityManagerCreator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import restService.com.websystique.springmvc.dontUse.LogInOut;
import restService.com.websystique.springmvc.model.Box;
import restService.com.websystique.springmvc.services.GetService;
import restService.com.websystique.springmvc.services.PostService;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


@Controller
public class ProjectController {
    @Inject
    GetService getService;
    @Inject
    PostService postService;

    @RequestMapping(value = "/commandHist", method = RequestMethod.GET)
    public ResponseEntity<Box<CommandHist>> getCommandHist(@RequestParam(value = "id", required = false) Long id,
                                                           HttpServletRequest request) {
        return getService.getBoxCommandHistFromId(id);
    }

    @RequestMapping(value = "/commandHist/{id}", method = RequestMethod.GET)
    public ResponseEntity<Box<CommandHist>> getCommandHistWithId(@PathVariable("id") Long id, HttpServletRequest request) {
        return getService.getBoxCommandHistFromId(id);
    }



    @RequestMapping(value = {"/commandHist/", "/commandHist"}, method = RequestMethod.POST)
    public ResponseEntity<Box<CommandHist>> createCommandHist(@RequestBody CommandHist commandHist) {
        return postService.createCommandHistPost(commandHist);
    }


}

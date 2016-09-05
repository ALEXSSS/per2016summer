package restService.com.websystique.springmvc.controller;


import com.psc.model.RoleTree;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import restService.com.websystique.springmvc.model.Box;
import restService.com.websystique.springmvc.services.GetService;
import restService.com.websystique.springmvc.services.PostService;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;


/**
 * Created by Alexander.Luchko on 27.07.2016.
 */
@Controller
public class RoleTreeController {

    @Inject
    GetService getService;
    @Inject
    PostService postService;

    @RequestMapping(value = "/roleTree", method = RequestMethod.GET)
    public ResponseEntity<Box<RoleTree>> getRoleTree(@RequestParam(value = "id_master", required = false) Long idMaster,
                                                     @RequestParam(value = "id_slaver", required = false) Long idSlaver,
                                                     HttpServletRequest request) {
        return getService.getBoxRoleTreeFromId(idMaster,idSlaver);
    }
    @RequestMapping(value = "/roleTree/{id}", method = RequestMethod.GET)
    public ResponseEntity<Box<RoleTree>> getRoleTreeWithId(@PathVariable("id") Long id, HttpServletRequest request) {
        return getService.getBoxRoleTreeFromId(id,null);
    }

    @RequestMapping(value = {"/roleTree/", "/roleTree"}, method = RequestMethod.POST)
    public ResponseEntity<Box<RoleTree>> createRoleTree(@RequestBody RoleTree roleTree) {
       return postService.createRoleTree(roleTree);
    }
}

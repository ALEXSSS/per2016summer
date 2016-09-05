package restService.com.websystique.springmvc.controller;


import com.psc.model.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import restService.com.websystique.springmvc.model.Box;
import restService.com.websystique.springmvc.services.GetService;
import restService.com.websystique.springmvc.services.PostService;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;



@Controller
public class RoleController {

    @Inject
    GetService getService;
    @Inject
    PostService postService;

    @RequestMapping(value = "/role", method = RequestMethod.GET)
    public ResponseEntity<Box<Role>> getRole(@RequestParam(value = "id", required = false) Long id,
                                                 HttpServletRequest request) {
        return getService.getBoxRoleFromId(id);
    }

    @RequestMapping(value = "/role/{id}", method = RequestMethod.GET)
    public ResponseEntity<Box<Role>> getRoleWithId(@PathVariable("id") Long id, HttpServletRequest request) {
        return getService.getBoxRoleFromId(id);
    }



    @RequestMapping(value = {"/role/", "/role"}, method = RequestMethod.POST)
    public ResponseEntity<Box<Role>> createRole(@RequestBody Role role) {
    return postService.createRole(role);
    }

}

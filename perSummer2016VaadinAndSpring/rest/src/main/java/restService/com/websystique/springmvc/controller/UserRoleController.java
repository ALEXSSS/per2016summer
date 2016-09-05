package restService.com.websystique.springmvc.controller;


import com.psc.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import restService.com.websystique.springmvc.model.Box;
import restService.com.websystique.springmvc.services.GetService;
import restService.com.websystique.springmvc.services.PostService;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;


@Controller
public class UserRoleController {

    @Inject
    GetService getService;
    @Inject
    PostService postService;

    @RequestMapping(value = "/userRole", method = RequestMethod.GET)
    public ResponseEntity<Box<UserRole>> getUserRole(@RequestParam(value = "user_id", required = false) Long idUser,
                                                     @RequestParam(value = "role_id", required = false) Long idRole,
                                                     HttpServletRequest request) {
        return getService.getBoxUserRoleFromId(idUser, idRole);
    }

    @RequestMapping(value = "/userRole/{id}", method = RequestMethod.GET)
    public ResponseEntity<Box<UserRole>> getUserRoleWithId(@PathVariable("id") Long id, HttpServletRequest request) {
        return getService.getBoxUserRoleFromId(id, null);
    }


    @RequestMapping(value = {"/userRole/", "/userRole"}, method = RequestMethod.POST)
    public ResponseEntity<Box<UserRole>> createUserRole(@RequestBody UserRole userRole) {
        return postService.createUserRole(userRole);
    }

}
/*
{
        "def" : "259",
        "delDate" : "228",
        "naviDate" : "2014-09-11",
        "naviUser" : "adminFromAusterlic",
        "displayDef" : "MYUser1",
        "def":"MyUser1",
        "phone" : "89217826543",
        "pwd" : "admin",
        "brncBrncId" : "0",
        "curatorUserId" : "308",
        "email" : "eee@er.com"
        }*/


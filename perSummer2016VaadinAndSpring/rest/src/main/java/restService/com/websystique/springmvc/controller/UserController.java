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
public class UserController {

    @Inject
    GetService getService;
    @Inject
    PostService postService;

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity<Box<User>> getUsers(@RequestParam(value = "id", required = false) Long id,
                                                 HttpServletRequest request) {
        return getService.getBoxUserFromId(id);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<Box<User>> getUsersWithId(@PathVariable("id") Long id, HttpServletRequest request) {
        return getService.getBoxUserFromId(id);
    }

    @RequestMapping(value = {"/user/", "/user"}, method = RequestMethod.POST)
    public ResponseEntity<Box<User>> createUser(@RequestBody User user) {
        return postService.createUser(user);
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

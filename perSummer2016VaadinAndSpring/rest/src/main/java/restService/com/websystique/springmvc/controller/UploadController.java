package restService.com.websystique.springmvc.controller;


import com.psc.model.*;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import restService.com.websystique.springmvc.dontUse.LogInOut;
import restService.com.websystique.springmvc.model.Box;
import restService.com.websystique.springmvc.services.GetService;
import restService.com.websystique.springmvc.services.PostService;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Alexander.Luchko on 24.08.2016.
 */
@Controller
public class UploadController {
    @Inject
    GetService getService;
    @Inject
    PostService postService;

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public ResponseEntity<Box<Upload>> getUpload(@RequestParam(value = "id", required = false) Long id,
                                                 HttpServletRequest request) {
        return getService.getBoxUploadFromId(id);
    }

    @RequestMapping(value = "/upload/{id}", method = RequestMethod.GET)
    public ResponseEntity<Box<Upload>> getUploadWithId(@PathVariable("id") Long id, HttpServletRequest request) {
        return getService.getBoxUploadFromId(id);
    }

    @RequestMapping(value = {"/upload/", "/upload"}, method = RequestMethod.POST)
    public ResponseEntity<Box<Upload>> createUpload(@RequestBody Upload upload) {
        return postService.createUpload(upload);
    }

}

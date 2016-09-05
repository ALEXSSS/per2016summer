package restService.com.websystique.springmvc.controller;


import com.psc.model.Region;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import restService.com.websystique.springmvc.model.Box;
import restService.com.websystique.springmvc.services.GetService;
import restService.com.websystique.springmvc.services.PostService;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;



@Controller
public class RegionController {
    @Inject
    GetService getService;
    @Inject
    PostService postService;

    @RequestMapping(value = "/region", method = RequestMethod.GET)
    public ResponseEntity<Box<Region>> getRegions(@RequestParam(value = "id", required = false) Long id,
                                                   HttpServletRequest request) {
        return getService.getBoxRegionFromId(id);
    }

    @RequestMapping(value = "/region/{id}", method = RequestMethod.GET)
    public ResponseEntity<Box<Region>> getRegionsWithId(@PathVariable("id") Long id, HttpServletRequest request) {
        return getService.getBoxRegionFromId(id);
    }

    @RequestMapping(value = {"/region/", "/region"}, method = RequestMethod.POST)
    public ResponseEntity<Box<Region>> createRegion(@RequestBody Region region) {
    return postService.createRegion(region);
    }

}
/*for post(example)
{
        "def" : "Simple1",
        "defShort" : "Проще",
        "orderNum" : "86",
        "isAvailable" : "Y",
        "naviUser" : "myUser",
        "naviDate" : "2014-09-11"
        }*/

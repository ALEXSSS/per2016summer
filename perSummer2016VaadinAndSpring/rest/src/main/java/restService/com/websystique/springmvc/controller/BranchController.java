package restService.com.websystique.springmvc.controller;


import com.psc.model.Branch;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import restService.com.websystique.springmvc.model.Box;
import restService.com.websystique.springmvc.services.GetService;


import javax.inject.Inject;

import javax.servlet.http.HttpServletRequest;


@Controller
@Api(value = "branch", description = "Endpoint for branch management")
public class BranchController {
    @Inject
    GetService getService;

    @ApiOperation(value = "Returns branch details wrapped in the Box", notes = "Returns a complete list of branch details.", response = Branch.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful retrieval of branch detail", response = Branch.class),
            @ApiResponse(code = 500, message = "Do not worry. It is not your problem")}
    )
    @RequestMapping(value = "/branch", method = RequestMethod.GET)
    public ResponseEntity<Box<Branch>> getBranches(@RequestParam(value = "id", required = false) Long id,
                                                   HttpServletRequest request) {
        return getService.getBoxBranchFromId(id);
    }
    @ApiOperation(value = "Returns branch details wrapped in the Box", notes = "Returns a complete list of branch details.", response = Branch.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful retrieval of branch detail", response = Branch.class),
            @ApiResponse(code = 500, message = "Do not worry. It is not your problem")}
    )
    @RequestMapping(value = "/branch/{id}", method = RequestMethod.GET)
    public ResponseEntity<Box<Branch>> getBranchesWithId(@PathVariable("id") Long id, HttpServletRequest request) {
        return getService.getBoxBranchFromId(id);
    }
}


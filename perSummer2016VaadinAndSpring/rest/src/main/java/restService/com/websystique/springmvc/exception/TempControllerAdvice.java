package restService.com.websystique.springmvc.exception;

/**
 * Created by Alexander.Luchko on 11.08.2016.
 */

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import restService.com.websystique.springmvc.model.Box;
//Temporary only for hiding internal state of server
@ControllerAdvice
public class TempControllerAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Box<String>> exception(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<Box<String>>(new Box<String>("some internal server error!"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

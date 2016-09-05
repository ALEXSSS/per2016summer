package restService.com.websystique.springmvc.services;

import com.psc.model.Region;
import com.psc.model.Region_;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.bind.annotation.PathVariable;
import restService.com.websystique.springmvc.model.Box;

/**
 * Created by Alexander.Luchko on 16.08.2016.
 */
public class DeleteService {
 /*   public ResponseEntity<Box<Region>> deleteRegionById(@PathVariable("id") long id) {
        try {
            System.out.println("Fetching & Deleting User with id " + id);
            Specifications<Region> specifications = Specifications.<Region>where((root, cq, cb) -> cb.equal(root.get(Region_.regId), id));
            if (repositoryRegion.findAll(specifications).isEmpty()) {
                System.out.println("Unable to delete. Region with id " + id + " not found");
                return new ResponseEntity<Box<Region>>(new Box<Region>("regions").
                        setRestErrorAndGetThis("There isn't this region."), HttpStatus.CONFLICT);
            }
            repositoryRegion.delete(id);
            return new ResponseEntity<Box<Region>>(new Box<Region>("regions").
                    setRestErrorAndGetThis("Region has been deleted."), HttpStatus.NO_CONTENT);
        } catch (JpaSystemException exception) {
            return new ResponseEntity<Box<Region>>(new Box<Region>("regions").
                    setRestErrorAndGetThis("There are others ties with this object. Delete not allowed here!"), HttpStatus.CONFLICT);
        }
    }*/
}

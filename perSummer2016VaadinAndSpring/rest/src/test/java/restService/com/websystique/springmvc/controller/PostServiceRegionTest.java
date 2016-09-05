package restService.com.websystique.springmvc.controller;

import com.psc.model.Branch;
import com.psc.model.Region;
import com.psc.repository.BranchRepository;
import com.psc.repository.RegionRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.jpa.domain.Specification;
import restService.com.websystique.springmvc.services.GetService;
import restService.com.websystique.springmvc.services.PostService;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Alexander.Luchko on 19.08.2016.
 */

//test is only for internal logic of the PostService because other objects is correct by default, such as RepositoryBranch
//more easier, there are only  returned item's structure tests
//because services should have your own tests
public class PostServiceRegionTest {
    @Mock(name = "repositoryRegion")
    RegionRepository repositoryRegion;
    @Mock(name = "repositoryBranch")
    BranchRepository repositoryBranch;
    @InjectMocks
    PostService postService;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this); // initialize all the @Mock objects
    }

    //проверяем, что был вызван save, то есть регион сохранятб
    // хорошо бы встроить валидатор
    @Test
    public void saveMethod() {
        List<Region> listRegion = new ArrayList<Region>();
        Region region = new Region(1L);
        listRegion.add(region);
        region.setNaviDate(new Timestamp(222));
        region.setNaviUser("admin");
        region.setDef("test");
        when(repositoryRegion.findAll(any(Specification.class))).thenReturn(new ArrayList<Region>());
        when(repositoryRegion.save(any(Region.class))).thenReturn(region);
        List<Branch> branches=new ArrayList<>();
        branches.add(new Branch(1L));
        when(repositoryBranch.findAll(any(Specification.class))).thenReturn(branches);
        postService.createRegion(region);
        verify(repositoryRegion).save(any(Region.class));
    }

}

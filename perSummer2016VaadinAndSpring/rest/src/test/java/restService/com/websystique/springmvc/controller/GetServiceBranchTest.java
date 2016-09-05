package restService.com.websystique.springmvc.controller;

import com.psc.model.Branch;
import com.psc.repository.BranchRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.jpa.domain.Specification;
import restService.com.websystique.springmvc.services.GetService;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Created by Alexander.Luchko on 19.08.2016.
 */

//test is only for internal logic of the GetService because other objects is correct by default, such as RepositoryBranch
//more easier, there are only  returned item's structure tests
//because services should have your own tests
public class GetServiceBranchTest {
    @Mock(name = "repositoryBranch")
    BranchRepository repositoryBranch;
    @InjectMocks
    GetService getService;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this); // initialize all the @Mock objects
    }
    //проверяем имя возвращаемой таблицы
    @Test
    public void tableName(){
        when(repositoryBranch.findAll()).thenReturn(new ArrayList<Branch>());
        assertEquals("branch",getService.getBoxBranchFromId(null).getBody().getNameTable());
    }

    //проверяем количество возвращенных значений
    @Test
    public void returnCorrectListSizeInResponse(){
        List<Branch> branches=new ArrayList<>();
        for (int i=0; i<= (new Random()).nextInt(15);i++) {
            branches.add(new Branch(new Long(i)));
        }
        when(repositoryBranch.findAll()).thenReturn(branches);
        assertEquals(branches.size(),getService.getBoxBranchFromId(null).getBody().getListOfItems().size());
        branches=new ArrayList<>();
        branches.add(new Branch(1L));
        when(repositoryBranch.findAll(any(Specification.class))).thenReturn(branches);
        assertEquals(branches.size(),getService.getBoxBranchFromId(1L).getBody().getListOfItems().size());

    }
}

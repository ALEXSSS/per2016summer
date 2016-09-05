package restService.com.websystique.springmvc.services;

import com.psc.model.*;
import com.psc.repository.*;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import restService.com.websystique.springmvc.dontUse.LogInOut;
import restService.com.websystique.springmvc.model.Box;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by Alexander.Luchko on 16.08.2016.
 */
@Service
public class GetService {
    @Inject
    RegionRepository repositoryRegion;
    @Inject
    BranchRepository repositoryBranch;
    @Inject
    CommandHistRepository repositoryCommandHist;
    @Inject
    RoleRepository repositoryRole;
    @Inject
    RoleTreeRepository repositoryRoleTree;
    @Inject
    UserRepository repositoryUser;
    @Inject
    UserRoleRepository repositoryUserRole;
    @Inject
    CommandStatusRepository repositoryCommandStatus;
    @Inject
    UploadRepository repositoryUpload;
    @Inject
    CommandRepository repositoryCommand;

    public ResponseEntity<Box<Branch>> getBoxBranchFromId(Long id) {
        List<Branch> branches;
        try {
            System.out.println(id);
            System.out.println("log: fisrt");
            if (id != null) {
                Specifications<Branch> specifications = Specifications.<Branch>where((root, cq, cb) -> cb.equal(root.get(Branch_.brncId), id));
                branches = repositoryBranch.findAll(specifications);
                System.out.println("log: id=" + id);
                return new ResponseEntity<Box<Branch>>(new Box<Branch>(branches, "branch"), HttpStatus.OK);
            } else {
                System.out.println("log: not term");
                return new ResponseEntity<Box<Branch>>(new Box<Branch>(repositoryBranch.findAll(), "branch"), HttpStatus.OK);
            }
        } catch (Exception dbException) {
            return new ResponseEntity<Box<Branch>>(new Box<Branch>("branch").setRestErrorAndGetThis("internal problem with db"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Box<CommandHist>> getBoxCommandHistFromId(Long id) {
        List<CommandHist> commandHist;
        try {
            LogInOut.write("getBoxCommandHistFromId", id);
            if (id != null) {
                Specifications<CommandHist> specifications =
                        Specifications.<CommandHist>where((root, cq, cb) -> cb.equal(root.get(CommandHist_.numberHistory), id));
                commandHist = repositoryCommandHist.findAll(specifications);
                return new ResponseEntity<Box<CommandHist>>(new Box<CommandHist>(commandHist, "CommandHist"), HttpStatus.OK);
            } else {
                return new ResponseEntity<Box<CommandHist>>(new Box<CommandHist>(repositoryCommandHist.findAll(), "CommandHist"), HttpStatus.OK);
            }
        } catch (Exception dbException) {
            return new ResponseEntity<Box<CommandHist>>(new Box<CommandHist>("CommandHist").setRestErrorAndGetThis("internal problem with db"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Box<Region>> getBoxRegionFromId(Long id) {
        List<Region> regions;
        try {
            System.out.println(id);
            System.out.println("log: fisrt");
            if (id != null) {
                Specifications<Region> specifications = Specifications.<Region>where((root, cq, cb) -> cb.equal(root.get(Region_.regId), id));
                regions = repositoryRegion.findAll(specifications);
                System.out.println("log: id=" + id);
                return new ResponseEntity<Box<Region>>(new Box<Region>(regions, "regions"), HttpStatus.OK);
            } else {
                System.out.println("log: not term");
                return new ResponseEntity<Box<Region>>(new Box<Region>(repositoryRegion.findAll(), "regions"), HttpStatus.OK);
            }
        } catch (Exception dbException) {
            return new ResponseEntity<Box<Region>>(new Box<Region>("regions").setRestErrorAndGetThis("internal problem with db"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Box<Role>> getBoxRoleFromId(Long id) {
        List<Role> role = new LinkedList<>();
        try {
            LogInOut.write("getBoxRoleFromId", id.toString());
            if (id != null) {
                Specifications<Role> specifications = Specifications.<Role>where((root, cq, cb) -> cb.equal(root.get(Role_.roleId), id));
                role = repositoryRole.findAll(specifications);
            } else {
                System.out.println("log: not term");
                role = repositoryRole.findAll();
            }
            return new ResponseEntity<Box<Role>>(new Box<Role>(repositoryRole.findAll(), "role"), HttpStatus.OK);
        } catch (Exception dbException) {
            return new ResponseEntity<Box<Role>>(new Box<Role>("role").setRestErrorAndGetThis("internal problem with db"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Box<RoleTree>> getBoxRoleTreeFromId(Long idMaster, Long idSlaver) {
        List<RoleTree> roleMaster = new LinkedList<RoleTree>();
        List<RoleTree> roleSlaver = new LinkedList<RoleTree>();
        Set<RoleTree> setRole = new HashSet<RoleTree>();

        try {
            LogInOut.write("getBoxRoleTreeFromId", idMaster + " " + idSlaver);
            if (idSlaver != null) {
                Specifications<RoleTree> specifications = Specifications.<RoleTree>where((root, cq, cb) -> cb.equal(root.get(RoleTree_.slaveRoleId), idSlaver));
                roleSlaver = repositoryRoleTree.findAll(specifications);
            }
            System.out.println(roleSlaver.size());
            if (idMaster != null) {
                Specifications<RoleTree> specifications = Specifications.<RoleTree>where((root, cq, cb) -> cb.equal(root.get(RoleTree_.masterRoleId), idMaster));
                roleMaster = repositoryRoleTree.findAll(specifications);
            }
            if (idMaster == null && idSlaver == null) {
                return new ResponseEntity<Box<RoleTree>>(new Box<RoleTree>(repositoryRoleTree.findAll(), "roleTree"), HttpStatus.OK);
            }
            System.out.println(roleMaster.size());
            setRole.addAll(roleSlaver);
            setRole.addAll(roleMaster);
            System.out.println(setRole.size());
            return new ResponseEntity<Box<RoleTree>>(new Box<RoleTree>(new LinkedList<RoleTree>(setRole), "roleTree"), HttpStatus.OK);
        } catch (Exception dbException) {
            return new ResponseEntity<Box<RoleTree>>(new Box<RoleTree>("role").setRestErrorAndGetThis("internal problem with db"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Box<User>> getBoxUserFromId(Long id) {
        List<User> users = new LinkedList<User>();

        if (id != null) {
            LogInOut.write("getBoxUserFromId", id.toString());
            Specifications<User> specifications = Specifications.<User>where((root, cq, cb) -> cb.equal(root.get(User_.userId), id));
            users = repositoryUser.findAll(specifications);
        } else {
            LogInOut.write("getBoxUserFromId", null);
            users = repositoryUser.findAll();
        }
        for (User user : users) {
            user.setPscUser(null);
        }
        return new ResponseEntity<Box<User>>(new Box<User>(users, "user"), HttpStatus.OK);
    }

    public ResponseEntity<Box<UserRole>> getBoxUserRoleFromId(Long idUser, Long idRole) {
        List<UserRole> usersRole = new LinkedList<UserRole>();
        List<UserRole> byUserId = new LinkedList<UserRole>();
        List<UserRole> byRoleId = new LinkedList<UserRole>();
        Set<UserRole> usersSet = new HashSet<UserRole>();
        if (idUser != null) {
            LogInOut.write("getBoxUserRoleFromId", idUser.toString());
            Specifications<UserRole> specifications = Specifications.<UserRole>where((root, cq, cb) -> cb.equal(root.get(UserRole_.userId), idUser));
            usersSet.addAll(repositoryUserRole.findAll(specifications));
        }
        if (idRole != null) {
            LogInOut.write("getBoxUserRoleFromId", idRole.toString());
            Specifications<UserRole> specifications = Specifications.<UserRole>where((root, cq, cb) -> cb.equal(root.get(UserRole_.roleId), idRole));
            usersSet.addAll(repositoryUserRole.findAll(specifications));
        }
        if (idRole == null && idUser == null) {
            usersSet.addAll(repositoryUserRole.findAll());
        }
        usersRole.addAll(usersSet);
        for (UserRole userRole : usersRole) {
            userRole.setUser(null);
        }
        return new ResponseEntity<Box<UserRole>>(new Box<UserRole>(usersRole, "userRole"), HttpStatus.OK);
    }

    public ResponseEntity<Box<Upload>> getBoxUploadFromId(Long id) {
        List<Upload> upload;
        try {
            LogInOut.write("getBoxUploadFromId", id);
            if (id != null) {
                Specifications<Upload> specifications =
                        Specifications.<Upload>where((root, cq, cb) -> cb.equal(root.get(Upload_.uploadId), id));
                upload = repositoryUpload.findAll(specifications);
                for (Upload uploadItem : upload) {
                    uploadItem.setUser(null);
                }
                return new ResponseEntity<Box<Upload>>(new Box<Upload>(upload, "Upload"), HttpStatus.OK);
            } else {
                upload = repositoryUpload.findAll();
                for (Upload uploadItem : upload) {
                    uploadItem.setUser(null);
                }
                return new ResponseEntity<Box<Upload>>(new Box<Upload>(upload, "Upload"), HttpStatus.OK);
            }
        } catch (Exception dbException) {
            return new ResponseEntity<Box<Upload>>(new Box<Upload>("Upload").setRestErrorAndGetThis("internal problem with db"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

package restService.com.websystique.springmvc.services;

import com.psc.model.*;
import com.psc.repository.*;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import restService.com.websystique.springmvc.dontUse.LogInOut;
import restService.com.websystique.springmvc.model.Box;
import com.google.common.hash.Hashing;
import com.google.gwt.thirdparty.guava.common.base.Charsets;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Alexander.Luchko on 16.08.2016.
 */
@Service
public class PostService {
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

    public ResponseEntity<Box<Region>> createRegion(@RequestBody Region region) {
        System.out.println(region.getRegId());
        System.out.println("Creating region " + region.getDef());
        //проверка на валидность ввода
        if (region.getNaviUser() == null || region.getNaviDate() == null || region.getDef() == null) {
            return new ResponseEntity<Box<Region>>(new Box<Region>("regions").
                    setRestErrorAndGetThis("Please, write all fields. NaviUser, NaviDate, Def"), HttpStatus.CONFLICT);
        }
        //проверка на существование уже такого региона
        Specifications<Region> specificationRegion = Specifications.
                <Region>where((root, cq, cb) -> cb.equal(root.get(Region_.def), region.getDef()));
        if (!repositoryRegion.findAll(specificationRegion).isEmpty()) {
            System.out.println("A region with " + region.getDef() + " already exist");
            return new ResponseEntity<Box<Region>>(new Box<Region>("regions").
                    setRestErrorAndGetThis("Region with this def already existed."), HttpStatus.CONFLICT);
        }
        long id = region.getBrncBrncId() == null ? 0 : region.getBrncBrncId();
        Specifications<Branch> specificationBranch = Specifications.<Branch>where((root, cq, cb) -> cb.equal(root.get(Branch_.brncId), id));
        List<Branch> branches = repositoryBranch.findAll(specificationBranch);
        //проверка на существование такого филиала
        if (branches.isEmpty()) {
            return new ResponseEntity<Box<Region>>(new Box<Region>("regions").setRestErrorAndGetThis("Branch doesn't exist. Look at id of branch."), HttpStatus.CONFLICT);
        }
        region.setBranch(branches.get(0));
        repositoryRegion.save(region);
        List<Region> list = new LinkedList<>();
        list.add(region);
        return new ResponseEntity<Box<Region>>(new Box<Region>(list, "regions"), HttpStatus.CREATED);
    }

    public ResponseEntity<Box<Role>> createRole(@RequestBody Role role) {
        LogInOut.write("Creating role " + role.getDef(), role.getDisplayDef());
        if (role.getDef() == null || role.getDisplayDef() == null || role.getNaviUser() == null || role.getNaviDate() == null) {
            return new ResponseEntity<Box<Role>>(new Box<Role>("role").
                    setRestErrorAndGetThis("Please, write all fields. def, displayDef"), HttpStatus.CONFLICT);
        }
        Specifications<Role> specificationRole = Specifications.
                <Role>where((root, cq, cb) -> cb.equal(root.get(Role_.def), role.getDef()));
        if (!repositoryRole.findAll(specificationRole).isEmpty()) {
            System.out.println("A Role with " + role.getDef() + " already exist");
            return new ResponseEntity<Box<Role>>(new Box<Role>("role").
                    setRestErrorAndGetThis("Role with this def already existed."), HttpStatus.CONFLICT);
        }
        repositoryRole.save(role);
        List<Role> list = new LinkedList<>();
        list.add(role);
        return new ResponseEntity<Box<Role>>(new Box<Role>(list, "role"), HttpStatus.CREATED);
    }

    public ResponseEntity<Box<RoleTree>> createRoleTree(@RequestBody RoleTree roleTree) {
        LogInOut.write("Creating roleTree", roleTree.getMasterRoleId() + " " + roleTree.getSlaveRoleId());
        if (roleTree.getMasterRoleId() == null || roleTree.getSlaveRoleId() == null || roleTree.getNaviUser() == null || roleTree.getNaviDate() == null) {
            return new ResponseEntity<Box<RoleTree>>(new Box<RoleTree>("roleTree").
                    setRestErrorAndGetThis("Please, write all fields. def, displayDef"), HttpStatus.CONFLICT);
        }
        if (roleTree.getMasterRoleId() == null || roleTree.getSlaveRoleId() == null) {
            System.out.println("A RoleTree with ( " + roleTree.getMasterRoleId() + " , " + roleTree.getSlaveRoleId() + " ) haven't valid foreign id");
            return new ResponseEntity<Box<RoleTree>>(new Box<RoleTree>("role").
                    setRestErrorAndGetThis("Your roleTree not valid."), HttpStatus.CONFLICT);
        }
        Specifications<RoleTree> specificationRoleTree = Specifications.
                <RoleTree>where((root, cq, cb) -> cb.and(cb.equal(root.get(RoleTree_.masterRoleId), roleTree.getMasterRoleId()), cb.equal(root.get(RoleTree_.masterRoleId), roleTree.getSlaveRoleId())));
        if (!repositoryRoleTree.findAll(specificationRoleTree).isEmpty()) {
            System.out.println("A RoleTree with ( " + roleTree.getMasterRoleId() + " , " + roleTree.getSlaveRoleId() + " ) already exist");
            return new ResponseEntity<Box<RoleTree>>(new Box<RoleTree>("role").
                    setRestErrorAndGetThis("Role with this def already existed."), HttpStatus.CONFLICT);
        }
        roleTree.setRoleMaster(repositoryRole.findOne(roleTree.getMasterRoleId()));
        roleTree.setRoleSlave(repositoryRole.findOne(roleTree.getSlaveRoleId()));
        repositoryRoleTree.save(roleTree);
        List<RoleTree> list = new LinkedList<>();
        list.add(roleTree);
        return new ResponseEntity<Box<RoleTree>>(new Box<RoleTree>(list, "role"), HttpStatus.CREATED);
    }


    public ResponseEntity<Box<User>> createUser(@RequestBody User user) {

        //проверка на валидность ввода
        if (user.getNaviUser() == null || user.getNaviDate() == null || user.getDef() == null) {
            return new ResponseEntity<Box<User>>(new Box<User>("user").
                    setRestErrorAndGetThis("Please, write all fields. NaviUser, NaviDate, Def"), HttpStatus.CONFLICT);
        }
        if (user.getDisplayDef() == null || user.getPhone() == null || user.getEmail() == null) {
            return new ResponseEntity<Box<User>>(new Box<User>("user").
                    setRestErrorAndGetThis("Please, write all fields. DisplayDef, Phone, Email"), HttpStatus.CONFLICT);
        }
        if (user.getPwd() == null || user.getCuratorUserId() == null) {
            return new ResponseEntity<Box<User>>(new Box<User>("user").
                    setRestErrorAndGetThis("Please, write all fields. DelDate, BrncBrncId, Pwd, CuratorUserId."), HttpStatus.CONFLICT);
        }
        if (user.getBrncBrncId() == null) {
            user.setBrncBrncId(0L);
        }
        user.setPwd(Hashing.sha1().hashString(user.getPwd(), Charsets.UTF_8).toString());
        Specifications<User> specificationRegion = Specifications.
                <User>where((root, cq, cb) -> cb.equal(root.get(User_.def), user.getDef()));
        if (!repositoryUser.findAll(specificationRegion).isEmpty()) {
            System.out.println("A user with " + user.getDef() + " already exist");
            return new ResponseEntity<Box<User>>(new Box<User>("user").
                    setRestErrorAndGetThis("User with this def already existed."), HttpStatus.CONFLICT);
        }
        Specifications<Branch> specificationBranch = Specifications.<Branch>where((root, cq, cb) -> cb.equal(root.get(Branch_.brncId), user.getBrncBrncId()));
        List<Branch> branches = repositoryBranch.findAll(specificationBranch);
        if (branches.isEmpty()) {
            return new ResponseEntity<Box<User>>(new Box<User>("user").setRestErrorAndGetThis("Branch doesn't exist. Look at id of branch."), HttpStatus.CONFLICT);
        }
        user.setPscBranch(branches.get(0));
       /* Long idCurator = user.getCuratorUserId() == null ? null : user.getCuratorUserId();
        if (idCurator != null) {
            Specifications<User> specificationUser = Specifications.<User>where((root, cq, cb) -> cb.equal(root.get(User_.userId), idCurator));
            List<User> users = repositoryUser.findAll(specificationUser);
            user.setPscUser(users.get(0));
        }  */
        List<User> list = new LinkedList<>();
        repositoryUser.save(user);
        user.setPscUser(null);
        list.add(user);
        return new ResponseEntity<Box<User>>(new Box<User>(list, "user"), HttpStatus.CREATED);
    }

    public ResponseEntity<Box<UserRole>> createUserRole(@RequestBody UserRole userRole) {
        if (userRole.getUserId() == null || userRole.getRoleId() == null || userRole.getNaviUser() == null
                || userRole.getNaviDate() == null) {
            return new ResponseEntity<Box<UserRole>>(new Box<UserRole>("UserRole").
                    setRestErrorAndGetThis("Please, write all fields."), HttpStatus.CONFLICT);
        }
        LogInOut.write("Creating UserRole " + userRole.getUserId(), userRole.getRoleId().toString());

        Specifications<UserRole> specificationRole = Specifications.
                <UserRole>where((root, cq, cb) -> cb.and(cb.equal(root.get(UserRole_.userId), userRole.getUserId()),
                        cb.equal(root.get(UserRole_.roleId), userRole.getRoleId())));
        if (!repositoryUserRole.findAll(specificationRole).isEmpty()) {
            System.out.println("A userRole with ( " + userRole.getUserId() + " , " + userRole.getRoleId() + " ) already exist");
            return new ResponseEntity<Box<UserRole>>(new Box<UserRole>("role").
                    setRestErrorAndGetThis("UserRole with this def already existed."), HttpStatus.CONFLICT);
        }
        Role role = repositoryRole.findOne(userRole.getRoleId());
        if (role == null) {
            System.out.println("A Role with " + userRole.getRoleId() + " doesn't exist");
            return new ResponseEntity<Box<UserRole>>(new Box<UserRole>("UserRole").
                    setRestErrorAndGetThis("A Role with " + userRole.getRoleId() + " doesn't exist"), HttpStatus.CONFLICT);
        }
        userRole.setRole(role);
        repositoryUserRole.save(userRole);
        List<UserRole> list = new LinkedList<>();
        list.add(userRole);
        return new ResponseEntity<Box<UserRole>>(new Box<UserRole>(list, "role"), HttpStatus.CREATED);
    }

    public ResponseEntity<Box<CommandHist>> createCommandHistPost(@RequestBody CommandHist commandHist) {
        LogInOut.write("Creating commandHist " + commandHist.getDef(), "POST");
        /*Specifications<CommandHist> specification = Specifications.
                <CommandHist>where((root, cq, cb) -> cb.equal(root.get(CommandHist_.def), commandHist.getDef()));
        if (!commandHistRepository.findAll(specification).isEmpty()) {
            System.out.println("A CommandHist with def: " + commandHist.getDef() + " already exist");
            return new ResponseEntity<Box<CommandHist>>(new Box<CommandHist>("commandHist").
                    setRestErrorAndGetThis("CommandHist with this def already existed."), HttpStatus.CONFLICT);
        }*/
        Specifications<CommandStatus> specificationCommandStatus = Specifications.
                <CommandStatus>where((root, cq, cb) -> cb.equal(root.get(CommandStatus_.cmstId), commandHist.getCommandStatusId()));
        List<CommandStatus> listCommandStatus = repositoryCommandStatus.findAll(specificationCommandStatus);
        if (listCommandStatus.isEmpty()) {
            System.out.println("A CommandStatus with this commandStatusId: " + commandHist.getCommandStatusId() + " doesn't exist");
            return new ResponseEntity<Box<CommandHist>>(new Box<CommandHist>("commandHist").
                    setRestErrorAndGetThis("A CommandStatus with this commandStatusId: " + commandHist.getCommandStatusId() +
                            " doesn't exist"), HttpStatus.CONFLICT);
        }
        commandHist.setCommandStatus(listCommandStatus.get(0));
        Long id = (commandHist.getBrncBrncId() == null) ? 0L : commandHist.getBrncBrncId();
        Specifications<Branch> specificationBranch =
                Specifications.<Branch>where((root, cq, cb) -> cb.equal(root.get(Branch_.brncId), id));
        List<Branch> list = repositoryBranch.findAll(specificationBranch);
        commandHist.setBranch(list.get(0));
        repositoryCommandHist.save(commandHist);
        List<CommandHist> listCommandHist = new LinkedList<>();
        listCommandHist.add(commandHist);
        return new ResponseEntity<Box<CommandHist>>(new Box<CommandHist>(listCommandHist, "commandHist"), HttpStatus.CREATED);
    }

    public ResponseEntity<Box<Upload>> createUpload(@RequestBody Upload upload) {
        LogInOut.write("Creating upload " + upload.getFileName(), " with id : " + upload.getUploadId());
        Specifications<Upload> specificationUploadFileData = Specifications.
                <Upload>where((root, cq, cb) -> cb.equal(root.get(Upload_.uploadId), upload.getUploadId()));
        Upload uploadFromDB = repositoryUpload.findAll(specificationUploadFileData).get(0);
        if (upload == null) {
            return new ResponseEntity<Box<Upload>>(new Box<Upload>("upload").
                    setRestErrorAndGetThis("Please, write a valid uploadId. All you need to write is, for example, \n{\n" +
                            "\"uploadId\"  : \"70\",\n" +
                            "\"commandId\" : \"15\",\n" +
                            "\"userId\" : \"1\"\n" +
                            "}"), HttpStatus.CONFLICT);
        }
        upload.setFileData(uploadFromDB.getFileData());
        upload.setFileName(uploadFromDB.getFileName());
        upload.setTimestamp(uploadFromDB.getTimestamp());

        upload.setUploadId(null);
        if (upload.getCommandId() == null || upload.getUserId() == null) {
            return new ResponseEntity<Box<Upload>>(new Box<Upload>("upload").
                    setRestErrorAndGetThis("Please, write all fields UserId and CommandId."), HttpStatus.CONFLICT);
        }
       /* Specifications<Upload> specificationUpload = Specifications.
                <Upload>where((root, cq, cb) -> cb.equal(root.get(Upload_.fileName), upload.getFileName()));
        if (!uploadRepository.findAll(specificationUpload).isEmpty()) {
            System.out.println("A upload with " + upload.getFileName() + " already exist");
            return new ResponseEntity<Box<Upload>>(new Box<Upload>("upload").
                    setRestErrorAndGetThis("A upload with " + upload.getFileName() + " already exist"), HttpStatus.CONFLICT);
        }*/
        Specifications<User> specificationUser = Specifications.
                <User>where((root, cq, cb) -> cb.equal(root.get(User_.userId), upload.getUserId()));
        List<User> users = repositoryUser.findAll(specificationUser);
        if (users.isEmpty()) {
            System.out.println("A upload with this userId " + upload.getFileName() + " forbidden please write valid userId");
            return new ResponseEntity<Box<Upload>>(new Box<Upload>("upload").
                    setRestErrorAndGetThis("A upload with this userId " + upload.getFileName() +
                            " forbidden please write valid userId"), HttpStatus.CONFLICT);
        }

        Specifications<Command> specificationCommand = Specifications.
                <Command>where((root, cq, cb) -> cb.equal(root.get(Command_.cmdId), upload.getCommandId()));
        List<Command> commands = repositoryCommand.findAll(specificationCommand);
        if (commands.isEmpty()) {
            System.out.println("A upload with this commandId " + upload.getFileName() + " forbidden please write valid commandId");
            return new ResponseEntity<Box<Upload>>(new Box<Upload>("upload").
                    setRestErrorAndGetThis("A upload with this commandId " + upload.getFileName() +
                            " forbidden please write valid commandId"), HttpStatus.CONFLICT);
        }
        upload.setUser(users.get(0));
        upload.setCommand(commands.get(0));

        repositoryUpload.save(upload);
        List<Upload> list = new LinkedList<>();
        list.add(upload);

        for (Upload uploadItem : list) {
            uploadItem.setUser(null);
        }
        upload.getCommand().setPscUser(null);
        return new ResponseEntity<Box<Upload>>(new Box<Upload>(list, "upload"), HttpStatus.CREATED);
    }
}




/*   //template for insert operations
    /*@RequestMapping(value = {"/branch/","/branch"}, method = RequestMethod.POST)
    public ResponseEntity<Box<Branch>> createUser(@RequestBody Branch branch, UriComponentsBuilder ucBuilder) {
        System.out.println("Creating branch " + branch.getDef());
        Specifications<Branch> specifications = Specifications.<Branch>where((root, cq, cb) -> cb.equal(root.get(Branch_.def), branch.getDef()));
        if (!repositoryRoleTree.findAll(specifications).isEmpty()) {
            System.out.println("A branch with " + branch.getDef() + " already exist");
            return new ResponseEntity<Box<Branch>>(new Box<Branch>("branch").setRestErrorAndGetThis("Table have already existed."), HttpStatus.CONFLICT);
        }

        EntityManager em = getEntityManager();

        em.getTransaction().begin();
        em.persist(branch);
        em.getTransaction().commit();

        List<Branch> list=new ArrayList<Branch>();
        list.add(branch);
        return new ResponseEntity<Box<Branch>>(new Box<Branch>(list,"branch"), HttpStatus.CREATED);
    }*/
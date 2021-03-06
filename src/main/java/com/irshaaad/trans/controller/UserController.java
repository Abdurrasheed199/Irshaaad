package com.irshaaad.trans.controller;

import com.irshaaad.trans.model.Bus;
import com.irshaaad.trans.model.Passenger;
import com.irshaaad.trans.model.Role;
import com.irshaaad.trans.model.User;
import com.irshaaad.trans.model.viewmodels.RegisterUserModel;
import com.irshaaad.trans.repository.RoleRepository;
import com.irshaaad.trans.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
public class UserController {
    final
    PasswordEncoder passwordEncoder;
    final UserRepository userRepository;
    final
    RoleRepository roleRepository;

    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

   /* @GetMapping("/users/feedback")
    public String contact(Model model) {
        return "user/contact";
    }
*/
   /* @GetMapping("/passengers/list")
    public String users(Model model) {
        model.addAttribute("passengers", userRepository.findUsersByJobTittle("Passenger"));
        return "passenger/list";
    }
*/
    @GetMapping("/staffs/list")
    public String staffs(Model model) {
        model.addAttribute("staffs", userRepository.findUsersByJobTittleNot("Passenger"));
        return "user/list";
    }

    @GetMapping("/users/create")
    public String create(Model model) {
        return "user/register";
    }

    @PostMapping(value = "/users/register")
    public String register(Model model, RedirectAttributes redirectAttributes, RegisterUserModel registerUserModel) {
        //String regex="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\\\S+$).{8,20}$";
        //Pattern p = Pattern.compile(regex);
        // Matcher m = p.matcher(registerUserModel.getPassword());
        if (!registerUserModel.getPassword().equals(registerUserModel.getConfirmPassword())) {
            redirectAttributes.addAttribute("error", "Password does not match ");
        } else if (userRepository.existsByUsername(registerUserModel.getUserName())) {
            redirectAttributes.addAttribute("error", "User with same id already exist ");
        } else if (registerUserModel.getPassword().isBlank() || registerUserModel.getPassword().isEmpty()) {
            redirectAttributes.addAttribute("error", "Password can not be empty or blank ");
        }

        //       else if( !m.matches()){
//            redirectAttributes.addAttribute("error","Password is not strong enough");
//        }
        else {
            User u = new User();
            u.setUsername(registerUserModel.getUserName());
            u.setPassword(passwordEncoder.encode(registerUserModel.getPassword()));
            Optional<Role> optionalRole = roleRepository.findByName("PASSENGER");
            if (optionalRole.isPresent()) {
                Role role = optionalRole.get();
                List<Role> roleList = new ArrayList<>();
                roleList.add(role);
                u.setRoles(roleList);
            }
            u.setJobTittle("Passenger");
            u.setFirstName(registerUserModel.getFirstName());
            u.setLastName(registerUserModel.getLastName());
            u.setAge(registerUserModel.getAge());
            u.setAddress(registerUserModel.getAddress());
            u.setPhone(registerUserModel.getPhone());
            u.setEmail(registerUserModel.getEmail());
            userRepository.save(u);
            redirectAttributes.addAttribute("success", "You have successfully registered");
            //redirectAttributes.addAttribute("error","");
            return "redirect:/users/create";
        }
        return "redirect:/users/create";
    }

    @GetMapping("/staffs/create")
    public String createStaff(Model model) {
        return "staff/register";
    }

    @PostMapping(value = "/staffs/register")
    public String registerStaff(Model model, RedirectAttributes redirectAttributes, RegisterUserModel registerUserModel) {
        //String regex="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\\\S+$).{8,20}$";
        //Pattern p = Pattern.compile(regex);
        // Matcher m = p.matcher(registerUserModel.getPassword());
        if (!registerUserModel.getPassword().equals(registerUserModel.getConfirmPassword())) {
            redirectAttributes.addAttribute("error", "Password does not match ");
        } else if (userRepository.existsByUsername(registerUserModel.getUserName())) {
            redirectAttributes.addAttribute("error", "User with same id already exist ");
        } else if (registerUserModel.getPassword().isBlank() || registerUserModel.getPassword().isEmpty()) {
            redirectAttributes.addAttribute("error", "Password can not be empty or blank ");
        }

        //       else if( !m.matches()){
//            redirectAttributes.addAttribute("error","Password is not strong enough");
//        }
        else {
            User u = new User();
            u.setUsername(registerUserModel.getUserName());
            u.setPassword(passwordEncoder.encode(registerUserModel.getPassword()));
            Optional<Role> optionalRole = roleRepository.findByName("STAFF");
            if (optionalRole.isPresent()) {
                Role role = optionalRole.get();
                List<Role> roleList = new ArrayList<>();
                roleList.add(role);
                u.setRoles(roleList);
            }
            u.setJobTittle(registerUserModel.getJobTittle());
            u.setFirstName(registerUserModel.getFirstName());
            u.setLastName(registerUserModel.getLastName());
            u.setAge(registerUserModel.getAge());
            u.setAddress(registerUserModel.getAddress());
            u.setPhone(registerUserModel.getPhone());
            u.setEmail(registerUserModel.getEmail());
            userRepository.save(u);
            redirectAttributes.addAttribute("success", "You have successfully registered");
            //redirectAttributes.addAttribute("error","");
            return "redirect:/staffs/create";
        }
        return "redirect:/staffs/create";
    }


    @RequestMapping(value = "/users/userRole/{id}", method = RequestMethod.GET)
    public String showAddRoleForm(@PathVariable("id") long id, Model model) {

        model.addAttribute("user", userRepository.findById(id).get());
        model.addAttribute("allRole", roleRepository.findAll());
        return "user/assignRole";
    }

    @RequestMapping(value = "/users/addNewRole", method = RequestMethod.POST)
    public String updateRole(Model model, @RequestParam long id, @RequestParam String name) {

        User u = userRepository.findById(id).get();
        //Optional<Role> optionalRole= roleRepository.findByName(name);
        Role role = roleRepository.findByName(name).get();

        List<Role> roleList = u.getRoles();
        roleList.add(role);
        u.setRoles(roleList);


        userRepository.save(u);

        return "redirect:/users/list";
    }


    @RequestMapping(value = "/users/delete/{id}", method = RequestMethod.GET)
    public String remove(@PathVariable("id") long id, Model model) {

        User u = userRepository.findById(id).get();

        userRepository.delete(u);
        return "redirect:/users/list";
    }

    @RequestMapping(value = "/users/edit/{id}", method = RequestMethod.GET)
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        //Bus bus = busRepository.findById(id);

        model.addAttribute("user", userRepository.findById(id).get());
        return "/user/edit";
    }


    @RequestMapping(value = "/users/update", method = RequestMethod.POST)
    public String updateUser(Model model, @RequestParam long id, @RequestParam String username) {

        User u = userRepository.findById(id).get();
        u.setUsername(username);

        userRepository.save(u);

        return "redirect:/users/list";
    }


}


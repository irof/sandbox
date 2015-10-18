package chirp;

import chirp.domain.User;
import chirp.domain.UserId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author irof
 */
@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    UserRepository users;

    @RequestMapping(method = RequestMethod.GET)
    public String create() {
        UserId id = UserId.generate();
        users.register(new User(id, "DUMMY"));
        return "redirect:/user/" + id.getId();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public String update(@PathVariable("id") String id, Model model) {
        User user = users.get(id);
        UserForm userForm = new UserForm();
        userForm.setId(user.getId().getId());
        userForm.setName(user.getName());
        model.addAttribute("userForm", userForm);
        return "chirp/user";
    }

    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public String update(UserForm form, Model model) {
        User user = users.get(form.getId());
        user.setName(form.getName());
        users.update(user);
        return "chirp/user";
    }

    public static class UserForm {
        String id;
        String mail;
        String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMail() {
            return mail;
        }

        public void setMail(String mail) {
            this.mail = mail;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}

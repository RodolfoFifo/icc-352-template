import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String processLoginForm(@RequestParam("username") String username,
                                   @RequestParam("password") String password,
                                   HttpServletRequest request,
                                   Model model) {
        // Validate the form and authenticate the user
        boolean isValidCredentials = validateCredentials(username, password);

        if (isValidCredentials) {
            // Set a session attribute to indicate successful login
            request.getSession().setAttribute("loggedIn", true);

            // Redirect to the home page on successful login
            return "redirect:/";
        } else {
            // Add an error message to the model
            model.addAttribute("error", "Invalid username or password");

            // Redirect back to the login page
            return "login";
        }
    }

    private boolean validateCredentials(String username, String password) {
        // Implement your validation and authentication logic here
        // Return true if the credentials are valid
        // For demo purposes, let's assume the username is "admin" and password is "password"
        return username.equals("admin") && password.equals("password");
    }
}

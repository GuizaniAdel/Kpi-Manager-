package com.Springboot.example.web;


import com.Springboot.example.model.User;
import com.Springboot.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;

import com.Springboot.example.validator.UserValidator;
import com.Springboot.example.model.ConfirmationToken;
import com.Springboot.example.repository.ConfirmationTokenRepository;
import com.Springboot.example.service.EmailSenderService;


@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private EmailSenderService emailSenderService;


    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.save(userForm);
        ConfirmationToken confirmationToken = new ConfirmationToken(userForm);


        confirmationTokenRepository.save(confirmationToken);

        return "redirect:/welcome";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }

    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcome(Model model) {
        return "welcome";
    }


    @RequestMapping(value = "/forgotPassword", method = RequestMethod.GET)
    public String displayResetPassword(Model modelAndView, User user) {
        modelAndView.addAttribute("user", user);
        return "forgotPassword";

    }


    /**
     * Receive email of the user, create token and send it via email to the user
     */

    @RequestMapping(value = "/forgotPassword", method = RequestMethod.POST)
    public String forgotUserPassword(Model model, @ModelAttribute("user") User user) {
        User existingUser = userService.findByEmail(user.getEmail());
        System.out.println(existingUser);
        if (existingUser != null) {
            // create token
            ConfirmationToken confirmationToken = new ConfirmationToken(existingUser);
            // save it
            confirmationTokenRepository.save(confirmationToken);
            // create the email
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(existingUser.getEmail());
            mailMessage.setSubject("Complete Password Reset!");
            mailMessage.setFrom("smidaamine8@gmail.com");
            mailMessage.setText("To complete the password reset process, please click here: "
                    + "http://localhost:6060/confirm-reset?token=" + confirmationToken.getConfirmationToken());
            emailSenderService.sendEmail(mailMessage);
            return "successForgotPassword";
        } else {
            model.addAttribute("message","email not found");
            return "forgotPassword";
        }

    }
    @RequestMapping(value = "/confirm-reset", method = {RequestMethod.GET, RequestMethod.POST})
    public String validateResetToken(Model modelAndView, @RequestParam("token") String confirmationToken) {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
        if (token != null) {
            User user = userService.findByEmail(token.getUser().getEmail());
            userService.save(user);
            modelAndView.addAttribute("user", user);
            modelAndView.addAttribute("email", user.getEmail());
            return "resetPassword";
        } else {
            String Msg = "The link is invalid or broken!";
            modelAndView.addAttribute("message", Msg);
            return ("error");

        }
    }
    /**
     * Receive the token from the link sent via email and display form to reset password
     */
    @RequestMapping(value = "/reset-password", method = RequestMethod.POST)
    public String resetUserPassword(Model modelAndView, User user) {
        // ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
        if (user.getEmail() != null) {
            // use email to find user
            User tokenUser = userService.findByEmail(user.getEmail());
            tokenUser.setPassword(user.getPassword());

            userService.save(tokenUser);

            return "successResetPassword";

        } else {
            return "error";

        }
    }
}

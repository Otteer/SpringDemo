package com.example.demo.controller;

import com.example.demo.model.UserSession;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.*;

@Controller
public class HomeController {

    @Autowired
    private UserSession userSession;

    private Map<String, Integer> userScores = new HashMap<>();

    @GetMapping("/")
    public String home(HttpServletRequest request, Model model) {
        String username = (String) request.getSession().getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }
        model.addAttribute("message", "Hello, Springs Boot with JSP!");
        model.addAttribute("userSession", userSession);
        return "home";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        return "login";
    }

    @PostMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response, String username, String password) {
        // Define the valid users
        Map<String, String> validUsers = new HashMap<>();
        validUsers.put("user1", "password1");
        validUsers.put("user2", "password2");

        // Check if the username and password are valid
        if (validUsers.containsKey(username) && validUsers.get(username).equals(password)) {
            request.getSession().setAttribute("username", username);
            return "redirect:/";
        } else {
            return "redirect:/login?error=invalid username or password";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        // Clear the session and cookies
        request.getSession().invalidate();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
        userSession.setUsername(null);
        return "redirect:/login";
    }

    @GetMapping("/quiz")
    public String showQuiz(HttpServletRequest request, HttpServletResponse response) {
        String username = (String) request.getSession().getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }

        // Define the questions and choices
        String[][] questions = {
                {"Who was the Greek Goddess of Wisdom?", "Hera", "Artemis", "Athena", "Aphrodite"},
                {"What is the name of the messenger god in Greek mythology?", "Apollo", "Zeus", "Hermes", "Poseidon"},
                {"What was Dionysus the god of?", "War", "Love", "Wine, fertility, and ritual madness", "Hunting"},
                {"Who was the Greek God of Fire?", "Ares", "Hephaestus", "Chronos", "Hades"},
                {"According to Greek mythology, who captured Pegasus and rode him in his fight with the Chimera?", "Perseus", "Theseus", "Bellerophon", "Odysseus"}
        };

        // Randomize the order of the questions
        List<String[]> randomizedQuestions = new ArrayList<>(Arrays.asList(questions));
        Collections.shuffle(randomizedQuestions);

        // Randomize the choices for each question
        for (int i = 0; i < randomizedQuestions.size(); i++) {
            String[] question = randomizedQuestions.get(i);
            List<String> choices = new ArrayList<>(Arrays.asList(question));
            choices.remove(0); // Remove the question text
            Collections.shuffle(choices); // Randomize the choices
            choices.add(0, question[0]); // Add the question text back to the beginning
            randomizedQuestions.set(i, choices.toArray(new String[0]));
        }

        // Store the randomized questions in the session
        request.getSession().setAttribute("questions", randomizedQuestions);

        return "quiz";
    }

    @PostMapping("/result")
    public String showResult(HttpServletRequest request, HttpServletResponse response, Model model) {
        String username = (String) request.getSession().getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }

        // Define the correct answers
        String[] correctAnswers = {"Athena", "Hermes", "Wine, fertility, and ritual madness", "Hephaestus", "Bellerophon"};

        // Retrieve the randomized questions from the session
        @SuppressWarnings("unchecked")
        List<String[]> randomizedQuestions = (List<String[]>) request.getSession().getAttribute("questions");

        // Retrieve the user's answers from the form submission
        List<String> userAnswers = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            String answer = request.getParameter("question" + i);
            userAnswers.add(answer);
        }

        // Calculate the score
        int score = 0;
        for (int i = 0; i < userAnswers.size(); i++) {
            String userAnswer = userAnswers.get(i);
            String[] question = randomizedQuestions.get(i);
            for (String correctAnswer : correctAnswers) {
                if (userAnswer.equals(correctAnswer) && Arrays.asList(question).contains(userAnswer)) {
                    score++;
                    break;
                }
            }
        }

        // Store the score for the user
        userScores.put(username, score);

        // Redirect to the grades page
        return "redirect:/grades";
    }

    @GetMapping("/grades")
    public String showGrades(HttpServletRequest request, Model model) {
        String username = (String) request.getSession().getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }

        // Pass the user scores to the view
        model.addAttribute("userScores", userScores);

        return "grades";
    }
}
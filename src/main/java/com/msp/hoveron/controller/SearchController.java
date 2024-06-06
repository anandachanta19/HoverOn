package com.msp.hoveron.controller;

import com.msp.hoveron.payload.SearchDto;
import com.msp.hoveron.service.SearchService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/hoveron")
public class SearchController {

    private final SearchService searchService;
    private final HttpServletRequest request;

    public SearchController(SearchService searchService, HttpServletRequest request) {
        this.searchService = searchService;
        this.request = request;
    }

    @GetMapping("/search")
    public String showSearchPage(Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            // Redirect to login if the user is not logged in
            return "redirect:/hoveron/login";
        }
        model.addAttribute("userId", userId);
        return "search"; // The name of your Thymeleaf template
    }


    @GetMapping("/search/results")
    @ResponseBody
    public ResponseEntity<?> search(@RequestParam(value = "query", required = false) String query) {
        Long userId = getUserIdFromSession();
        if (query != null && !query.isEmpty()) {
            SearchDto searchResult = searchService.searchSongs(query);
            if (searchResult != null) {
                return ResponseEntity.ok(searchResult);
            } else {
                return ResponseEntity.ok(new SearchDto()); // Return an empty SearchDto
            }
        } else {
            return ResponseEntity.badRequest().body("Query parameter is missing or empty");
        }
    }

    @GetMapping("/back")
    public String redirectToUserHome() {
        Long userId = getUserIdFromSession();
        if (userId != null) {
            return "redirect:/hoveron/home/" + userId; // Redirect to the home page of the specified user
        } else {
            // Handle the case when userId is not provided, for example, redirect to a default home page
            return "redirect:/hoveron/home"; // Redirect to a default home page
        }
    }

    private Long getUserIdFromSession() {
        HttpSession session = request.getSession(false); // Get session without creating a new one
        if (session != null && session.getAttribute("userId") != null) {
            return (Long) session.getAttribute("userId");
        }
        return null;
    }
}

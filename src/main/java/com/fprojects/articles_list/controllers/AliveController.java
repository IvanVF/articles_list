package com.fprojects.articles_list.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Check is application starts up
 */
@RestController
public class AliveController {

    @GetMapping(path = "/")
    public String appWorkingAnswer() { return "Articles List works!"; }
}

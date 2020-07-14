package org.meteorite.com.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/es/base/")
@Slf4j
public class BaseController {

    @PostMapping(value = "/login/test",consumes = "text/xml")
    public String test(@RequestBody String requestString, ServerHttpRequest request) throws Exception {

        return "test";
    }

}

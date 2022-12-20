package com.ss.training.controller;

import com.ss.training.domain.AppUser;
import com.ss.training.service.AppUserService;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/appUser")
public class AppUserController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    AppUserService appUserService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody String json){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=utf-8");
        headers.add("responseCode", "0");
        LOGGER.info("[START][save][01] json : {}",json);

        try{
            AppUser appUser = new JSONDeserializer<AppUser>().use(null, AppUser.class).deserialize(json);
            if(appUser.getId() != null){
                appUserService.updateAppUser(appUser);
            }else{
                appUserService.insertAppUser(appUser);
            }

            return new ResponseEntity<String>(new JSONSerializer().deepSerialize("sucess"), headers, HttpStatus.OK);
        }catch(Exception ex){
            LOGGER.error("[ERROR][save] : {}",ex);

            headers.add("responseCode", "-1");
            headers.add("errMsg", ex.getMessage());

            Map<String,Object> result = new HashMap<>();
            result.put("errMsg", ex.getMessage());
            result.put("errorStatus", "-1");
            result.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<String>(new JSONSerializer().deepSerialize(result), headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

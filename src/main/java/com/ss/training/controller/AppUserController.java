package com.ss.training.controller;

import com.ss.training.domain.AppUser;
import com.ss.training.service.AppUserService;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/appUser")
public class AppUserController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    final
    AppUserService appUserService;

    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

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
            return getStringResponseEntity(headers, ex);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<String> update(@RequestBody String json){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=utf-8");
        headers.add("responseCode", "0");
        LOGGER.info("[START][update][01] json : {}",json);

        try{
            AppUser appUser = new JSONDeserializer<AppUser>().use(null, AppUser.class).deserialize(json);
            if(appUser.getId() != null){
                appUserService.updateAppUser(appUser);

            }

            return new ResponseEntity<String>(new JSONSerializer().deepSerialize("sucess"), headers, HttpStatus.OK);
        }catch(Exception ex){
            LOGGER.error("[ERROR][update] : {}",ex);

            headers.add("responseCode", "-1");
            return getStringResponseEntity(headers, ex);
        }
    }
    @GetMapping("/deleteAppUserById")
    public ResponseEntity<String> delete(@RequestParam(value = "id",required = false)Long id){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=utf-8");
        headers.add("responseCode", "0");
        LOGGER.info("[START][delete][01] id : {}",id);

        try{
           appUserService.deleteAppUserById(id);

            return new ResponseEntity<String>(new JSONSerializer().deepSerialize("sucess"), headers, HttpStatus.OK);
        }catch(Exception ex){
            LOGGER.error("[ERROR][delete] : {}",ex);

            headers.add("responseCode", "-1");
            return getStringResponseEntity(headers, ex);
        }
    }
    @GetMapping("/findAppUserById")
    public ResponseEntity<String> findAppUserById(@RequestParam(value = "id",required = true)Long id){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=utf-8");
        headers.add("responseCode", "0");
        LOGGER.info("[START][select][01] id : {}",id);

        try{
           AppUser appUser = appUserService.findAppUserById(id);

            return new ResponseEntity<String>(new JSONSerializer().deepSerialize(appUser), headers, HttpStatus.OK);
        }catch(Exception ex){
            LOGGER.error("[ERROR][select] : {}",ex);

            headers.add("responseCode", "-1");
            return getStringResponseEntity(headers, ex);
        }
    }


    static ResponseEntity<String> getStringResponseEntity(HttpHeaders headers, Exception ex) {
        headers.add("errMsg", ex.getMessage());

        Map<String,Object> result = new HashMap<>();
        result.put("errMsg", ex.getMessage());
        result.put("errorStatus", "-1");
        result.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<String>(new JSONSerializer().deepSerialize(result), headers, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

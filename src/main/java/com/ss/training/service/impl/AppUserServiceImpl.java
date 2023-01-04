package com.ss.training.service.impl;

import com.ss.training.domain.AppUser;
import com.ss.training.repository.AppUserRepository;
import com.ss.training.service.AppUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AppUserServiceImpl implements AppUserService {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    AppUserRepository appUserRepository;

    @Override
    @Transactional
    public void insertAppUser(AppUser appUser) {
        LOGGER.info("[AppUserServiceImpl][insertAppUser][01]");
        try {
            appUserRepository.save(appUser);
        } catch (Exception ex) {
            LOGGER.error("[ERROR][AppUserServiceImpl][insertAppUser] errorMsg : {}", ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    @Transactional
    public void updateAppUser(AppUser appUser) {
        LOGGER.info("[AppUserServiceImpl][updateAppUser][01]");

        try {
            AppUser appUserReal = findById(appUser.getId());
            if (appUserReal != null) {
                if (appUser.getVersion() == null)
                    throw new RuntimeException("[ERROR][AppUserServiceImpl][updateAppUser] not found version");
                if (appUser.getVersion().compareTo(appUserReal.getVersion()) != 0)
                    throw new RuntimeException("Old version can't update");

                appUserReal.setEmpCode(appUser.getEmpCode());
                appUserReal.setFirstName(appUser.getFirstName());
                appUserReal.setLastName(appUser.getLastName());
                appUserReal.setPassword(appUser.getPassword());
                appUserReal.setTitle(appUser.getTitle());
                appUserReal.setUsername(appUser.getUsername());
                appUserRepository.save(appUserReal);
            }


            LOGGER.info("[AppUserServiceImpl][updateAppUser][02]");
        } catch (Exception ex) {
            LOGGER.error("[ERROR][AppUserServiceImpl][updateAppUser] errorMsg : {}", ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    public List<AppUser> findAll() {
        LOGGER.info("[AppUserServiceImpl][findAll][01]");
        try {
            return appUserRepository.findAll();
        } catch (Exception ex) {
            LOGGER.error("[ERROR][AppUserServiceImpl][findAll] errorMsg : {}", ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    public AppUser findById(Long id) {
        LOGGER.info("[AppUserServiceImpl][findById][01] id : {}", id);

        try {
            Optional<AppUser> appUser = appUserRepository.findById(id);
            if (appUser.isPresent()) {
                LOGGER.info("[AppUserServiceImpl][findById][02] appUser is not null!");
                LOGGER.info(" ================== [findById] id :{}", appUser.get().getId());
                return appUser.get();
            } else {
                LOGGER.info("[AppUserServiceImpl][findById][03] appUser is null!");
                return null;
            }

        } catch (Exception ex) {
            LOGGER.error("[ERROR][AppUserServiceImpl][findById] errorMsg : {}", ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    @Transactional
    public void deleteAppUser(List<Long> idList) {
        LOGGER.info("[AppUserServiceImpl][deleteAppUser][01] : {} ", idList);
        try {
            for (Long id : idList) {
                AppUser appUser = findById(id);
                if (appUser != null) {
                    LOGGER.info("[AppUserServiceImpl][deleteAppUser] delete id : {} ", id);
                    appUserRepository.delete(appUser);
                } else {
                    LOGGER.info("[AppUserServiceImpl][deleteAppUser] not found id :{}  ", id);
                }
            }
            LOGGER.info("[AppUserServiceImpl][deleteAppUser][02]");
        } catch (Exception e) {
            LOGGER.error("[ERROR][AppUserServiceImpl][deleteAppUser] errorMsg : {} ", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public void deleteAppUserById(Long id) {
        LOGGER.info("[AppUserServiceImpl][findById][01] id : {}", id);
        try {
            AppUser appUser = findById(id);
            if (appUser != null) {
                appUserRepository.delete(appUser);
            }
            LOGGER.info("[AppUserServiceImpl][findById][02]");
        } catch (Exception e) {
            LOGGER.error("[ERROR][AppUserServiceImpl][deleteAppUser] errorMsg : {} ", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public AppUser findAppUserById(Long id){
        LOGGER.info("[AppUserServiceImpl][findById][01] id : {}", id);
        try {
            AppUser appUser= findById(id);
            LOGGER.info("Get User by firstName",appUser.getFirstName());
            if(appUser == null){
                throw new RuntimeException("App User Not found!");
            }
            LOGGER.info("[END]findAppUserById");

            return  appUser;
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }


}

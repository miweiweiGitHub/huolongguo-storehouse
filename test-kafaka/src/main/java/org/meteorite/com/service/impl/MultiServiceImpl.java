package org.meteorite.com.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.meteorite.com.domain.User;
import org.meteorite.com.service.MultiService;
import org.springframework.stereotype.Service;

/**
 * @author EX_052100260
 * @title: MultiServiceImpl
 * @projectName huolongguo-storehouse
 * @description: TODO
 * @date 2020-10-10 10:37
 */
@Service
@Slf4j
public class MultiServiceImpl implements MultiService {
    @Override
    public void addUser(User user) {
        log.info("MultiServiceImpl addUser ... ");
    }
}

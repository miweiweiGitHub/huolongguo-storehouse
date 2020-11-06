package org.meteorite.com.service.impl;

import com.google.common.util.concurrent.RateLimiter;
import org.meteorite.com.service.AccessLimitService;
import org.springframework.stereotype.Service;

/**
 * @author EX_052100260
 * @title: AccessLimitServiceImpl
 * @projectName huolongguo-storehouse
 * @description: TODO
 * @date 2020-11-5 17:13
 */
@Service
public class AccessLimitServiceImpl implements AccessLimitService {

    RateLimiter re =  RateLimiter.create(2.0);


    @Override
    public boolean getTokenCard() {
        return re.tryAcquire();
    }
}

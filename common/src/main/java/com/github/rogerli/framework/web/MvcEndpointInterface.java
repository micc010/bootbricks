package com.github.rogerli.framework.web;

import com.github.rogerli.framework.service.ServiceInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface MvcEndpointInterface<S extends ServiceInterface> {

    S getService();

    default Logger logger() {
        return LoggerFactory.getLogger(this.getClass());
    }

}

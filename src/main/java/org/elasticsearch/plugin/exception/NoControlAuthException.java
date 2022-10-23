package org.elasticsearch.plugin.exception;

import org.elasticsearch.ElasticsearchStatusException;
import org.elasticsearch.rest.RestStatus;

/**
 * @author Jayden
 * @date 2022/8/4
 */
public class NoControlAuthException extends ElasticsearchStatusException {

    private static final String DEFAULT_MSG = "No control auth";

    public NoControlAuthException() {
        super(DEFAULT_MSG, RestStatus.FORBIDDEN);
    }
}




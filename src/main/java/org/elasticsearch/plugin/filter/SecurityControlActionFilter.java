package org.elasticsearch.plugin.filter;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.ActionRequest;
import org.elasticsearch.action.ActionResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexAction;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.ActionFilter;
import org.elasticsearch.action.support.ActionFilterChain;
import org.elasticsearch.common.component.AbstractComponent;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.util.concurrent.ThreadContext;
import org.elasticsearch.plugin.exception.NoControlAuthException;
import org.elasticsearch.plugin.settings.Constants;
import org.elasticsearch.plugin.utils.StringUtils;
import org.elasticsearch.tasks.Task;
import org.elasticsearch.threadpool.ThreadPool;

/**
 * @author Jayden
 * @date 2022/8/4
 */
public class SecurityControlActionFilter extends AbstractComponent implements ActionFilter {

    private final ThreadContext threadContext;
    private final String controlUser;
    private final String controlSecret;


    public SecurityControlActionFilter(Settings settings, ThreadPool threadPool) {
        super(settings);
        this.threadContext = threadPool.getThreadContext();
        this.controlUser = settings.get(Constants.CONTROL_USER_CONFIG);
        this.controlSecret = settings.get(Constants.CONTROL_SECRET_CONFIG);
    }

    @Override
    public int order() {
        return 0;
    }

    @Override
    public <Request extends ActionRequest, Response extends ActionResponse> void apply(Task task, String action, Request request, ActionListener<Response> listener, ActionFilterChain<Request, Response> chain) {
        /*
        *
        * */
        if (DeleteIndexRequest.class.getName().equals(request.getClass().getName())) {
            String headerControlUser = threadContext.getHeader(Constants.CONTROL_USER_HEADER);
            String headerControlSecret = threadContext.getHeader(Constants.CONTROL_SECRET_HEADER);
            if (StringUtils.isEmpty(headerControlUser)
                    || StringUtils.isEmpty(headerControlSecret)
                    || !controlUser.equals(headerControlUser)
                    || !controlSecret.equals(headerControlSecret)) {
                throw new NoControlAuthException();
            }
        }
        chain.proceed(task, action, request, listener);
    }
}

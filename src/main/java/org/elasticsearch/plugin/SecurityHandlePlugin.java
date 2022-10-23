package org.elasticsearch.plugin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.support.ActionFilter;
import org.elasticsearch.common.settings.Setting;
import org.elasticsearch.common.util.concurrent.ThreadContext;
import org.elasticsearch.plugin.filter.SecurityControlActionFilter;
import org.elasticsearch.plugin.settings.Constants;
import org.elasticsearch.plugins.ActionPlugin;
import org.elasticsearch.plugins.NetworkPlugin;
import org.elasticsearch.plugins.Plugin;
import org.elasticsearch.rest.RestHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Plugin main entrance.
 *
 * @author Jayden
 * @date 2022/8/4
 */
public class SecurityHandlePlugin extends Plugin implements ActionPlugin, NetworkPlugin {

    private final static Logger LOG = LogManager.getLogger(SecurityHandlePlugin.class);


    public SecurityHandlePlugin(final Setting settings) {

    }

    @Override
    public List<Setting<?>> getSettings() {
        List<Setting<?>> settings = new ArrayList<>();
        settings.add(Setting.simpleString(Constants.CONTROL_USER_CONFIG, Setting.Property.NodeScope));
        settings.add(Setting.simpleString(Constants.CONTROL_SECRET_CONFIG, Setting.Property.NodeScope));
        return settings;
    }

    @Override
    public Collection<String> getRestHeaders() {
        List<String> interceptedHeader = new ArrayList<>();
        interceptedHeader.add(Constants.CONTROL_USER_HEADER);
        interceptedHeader.add(Constants.CONTROL_SECRET_HEADER);
        return interceptedHeader;
    }


    @Override
    public List<Class<? extends ActionFilter>> getActionFilters() {
        List<Class<? extends ActionFilter>> actionFilters = new ArrayList<>();
        actionFilters.add(SecurityControlActionFilter.class);
        return actionFilters;
    }
}

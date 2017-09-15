package com.github.frapontillo.pulse.crowd.plugin;

import com.github.frapontillo.pulse.crowd.data.entity.ProjectRun;
import com.github.frapontillo.pulse.crowd.data.plugin.GenericDbConfig;
import com.github.frapontillo.pulse.spi.PluginConfigHelper;
import com.google.gson.JsonElement;

/**
 * @author Francesco Pontillo
 */
public class ProjectRunOptions extends GenericDbConfig<ProjectRunOptions> {
    private String projectRunId;
    private String log;

    /**
     * Get the ID of the project run to update while executing the plugin using this configuration.
     *
     * @return The ID of a {@link ProjectRun}, as a {@link String}.
     */
    public String getProjectRunId() {
        return projectRunId;
    }

    /**
     * Set the ID of the project run to update while executing the plugin using this configuration.
     *
     * @param projectRunId The ID of the {@link ProjectRun}.
     */
    public void setProjectRunId(String projectRunId) {
        this.projectRunId = projectRunId;
    }

    /**
     * Get the location of the log path for the current run.
     *
     * @return The filesystem path of the log.
     */
    public String getLog() {
        return log;
    }

    /**
     * Set the path for the current run log.
     *
     * @param log The filesystem log path.
     */
    public void setLog(String log) {
        this.log = log;
    }

    @Override public ProjectRunOptions buildFromJsonElement(JsonElement json) {
        return PluginConfigHelper.buildFromJson(json, ProjectRunOptions.class);
    }
}
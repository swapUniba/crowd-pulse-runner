package com.github.frapontillo.pulse.crowd.plugin;

import com.github.frapontillo.pulse.crowd.data.entity.ProjectRun;
import com.github.frapontillo.pulse.util.DateUtil;
import com.github.frapontillo.pulse.util.PulseLogger;
import org.apache.logging.log4j.Logger;

import java.util.Date;

/**
 * @author Francesco Pontillo
 */
public class ProjectRunEndPlugin extends ProjectRunPlugin {
    public static final String PLUGIN_NAME = "project-run-end";
    private final Logger logger = PulseLogger.getLogger(ProjectRunEndPlugin.class);

    @Override public String getName() {
        return PLUGIN_NAME;
    }

    @Override protected void handleWake(ProjectRun projectRun, ProjectRunOptions parameters,
            boolean success) {
        projectRun.setDateEnd(new Date());
        projectRun.setStatus(success ? 0 : 1);
        logger.info("Project run has {} at {}.", success ? "completed" : "errored",
                DateUtil.toISOString(projectRun.getDateEnd()));
    }
}

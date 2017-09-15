package com.github.frapontillo.pulse.crowd.cli;

import com.github.frapontillo.pulse.cli.AbstractBlade;
import com.github.frapontillo.pulse.crowd.plugin.ProjectRunStartPlugin;
import com.github.frapontillo.pulse.crowd.plugin.ProjectRunEndPlugin;
import com.github.frapontillo.pulse.graph.Graph;
import com.github.frapontillo.pulse.graph.Node;
import com.github.frapontillo.pulse.util.DateUtil;
import com.google.gson.JsonObject;

import java.io.FileNotFoundException;
import java.util.Date;

/**
 * Dynamic pipeline builder from a JSON configuration file.
 * <p/>
 * Because CrowdBlade Runner. Get it? HA!
 *
 * @author Francesco Pontillo
 */
public class CrowdBlade extends AbstractBlade<CrowdBladeParameters> {

    public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException {
        CrowdBlade crowdBlade = new CrowdBlade(args);
        crowdBlade.run();
    }

    public CrowdBlade(String[] args) throws ClassNotFoundException, FileNotFoundException {
        super(args);
    }

    @Override protected CrowdBladeParameters getNewBladeParameters() {
        return new CrowdBladeParameters();
    }

    /**
     * Wrap the given {@link Graph} between two new steps:
     * <ul>
     * <li>the first one sets the starting date, the process ID and the log path into the project
     * run</li>
     * <li>the last one sets the ending date into the project run</li>
     * </ul>
     *
     * @param graph      The {@link Graph} to wrap.
     * @param parameters The {@link CrowdBladeParameters} to use (they can specify the log, db and
     *                   project run).
     */
    @Override protected void alterGraph(Graph graph, CrowdBladeParameters parameters) {
        super.alterGraph(graph, parameters);
        if (parameters.mustSetProjectRun()) {
            getLogger().debug("Using project run information from CLI params, wrapping graph...");

            String uid = DateUtil.toISOString(new Date());
            JsonObject config = new JsonObject();
            config.addProperty("projectRunId", parameters.getRun());
            config.addProperty("log", parameters.getLog());
            config.addProperty("db", parameters.getDb());

            Node first = new Node();
            first.setGraph(graph);
            first.setName("wrap_start_" + uid);
            first.setPlugin(ProjectRunStartPlugin.PLUGIN_NAME);
            first.setConfig(config);

            Node last = new Node();
            last.setGraph(graph);
            last.setName("wrap_end_" + uid);
            last.setPlugin(ProjectRunEndPlugin.PLUGIN_NAME);
            last.setConfig(config);

            graph.prependSingleRoot(first);
            graph.appendSingleTerminal(last);

            getLogger().debug("Graph wrapped with info from CLI params.");
        }
    }
}

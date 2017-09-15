package com.github.frapontillo.pulse.crowd.cli;

import com.beust.jcommander.Parameter;
import com.github.frapontillo.pulse.cli.BladeParameters;
import com.github.frapontillo.pulse.util.StringUtil;

/**
 * Command line parameters for the {@link CrowdBlade} runner, all optionals:
 * <ul>
 * <li>-c or --config, path to the configuration file; if not specified, the config file will be
 * read from the standard input</li>
 * <li>-r or --run, the ID of the current run, if specified the current run will be updated in the
 * database</li>
 * <li>-l or --log, file path to save logs to</li>
 * <li>-d or --db, connection string to the database where the run will be updated</li>
 * </ul>
 *
 * @author Francesco Pontillo
 */
public class CrowdBladeParameters extends BladeParameters {

    @Parameter(names = {"-r", "--run"},
            description = "The ID of the run owner of this execution") private String run;

    @Parameter(names = {"-d", "--db"},
            description = "Connection string to the database where runs are saved") private String
            db;

    public String getRun() {
        return run;
    }

    public String getDb() {
        return db;
    }

    public boolean mustSetProjectRun() {
        return (!StringUtil.isNullOrEmpty(run) && !StringUtil.isNullOrEmpty(run));
    }
}

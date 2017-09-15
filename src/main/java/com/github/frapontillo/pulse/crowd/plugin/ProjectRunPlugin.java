package com.github.frapontillo.pulse.crowd.plugin;

import com.github.frapontillo.pulse.crowd.data.entity.ProjectRun;
import com.github.frapontillo.pulse.crowd.data.repository.ProjectRunRepository;
import com.github.frapontillo.pulse.spi.IPlugin;
import com.github.frapontillo.pulse.util.PulseLogger;
import org.apache.logging.log4j.Logger;
import org.bson.types.ObjectId;
import rx.Observable;
import rx.Subscriber;
import rx.observers.SafeSubscriber;

/**
 * @author Francesco Pontillo
 */
public abstract class ProjectRunPlugin extends IPlugin<Object, Object, ProjectRunOptions> {
    private ProjectRunRepository projectRunRepository;
    private final Logger logger = PulseLogger.getLogger(ProjectRunPlugin.class);

    @Override public ProjectRunOptions getNewParameter() {
        return new ProjectRunOptions();
    }

    @Override protected Observable.Operator<Object, Object> getOperator(ProjectRunOptions params) {
        return subscriber -> new SafeSubscriber<>(new Subscriber<Object>() {
            @Override public void onCompleted() {
                reportCompletion(true);
                subscriber.onCompleted();
            }

            @Override public void onError(Throwable e) {
                reportCompletion(false);
                e.printStackTrace();
                subscriber.onError(e);
            }

            @Override public void onNext(Object o) {
                subscriber.onNext(o);
            }

            private void reportCompletion(boolean success) {
                projectRunRepository = new ProjectRunRepository(params.getDb());
                ProjectRun run = projectRunRepository.get(new ObjectId(params.getProjectRunId()));
                if (run == null) {
                    logger.warn("No project run was found for ID {}, won't set anything.",
                            params.getProjectRunId());
                    return;
                }
                handleWake(run, params, success);
                projectRunRepository.save(run);
            }
        });
    }

    protected abstract void handleWake(ProjectRun projectRun, ProjectRunOptions parameters,
            boolean success);
}

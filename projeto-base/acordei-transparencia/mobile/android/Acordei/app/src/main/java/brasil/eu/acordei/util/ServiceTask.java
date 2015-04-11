package brasil.eu.acordei.util;

import needle.Needle;
import needle.UiRelatedTask;

/**
 * Created by deivison on 6/2/14.
 */
public class ServiceTask<T> {

    private OnServiceBackground<T> onServiceBackground;
    private Exception exception;

    private ServiceTask() {
    }

    private ServiceTask(OnServiceBackground onServiceBackground) {
        this.onServiceBackground = onServiceBackground;
    }

    public static ServiceTask getInstance(OnServiceBackground onServiceBackground) {
        return new ServiceTask(onServiceBackground);
    }

    public void execute() {
        Needle.onBackgroundThread().withThreadPoolSize(3).execute(new UiRelatedTask<T>() {
            @Override
            protected T doWork() {
                try {
                    return onServiceBackground.execute(ServiceTask.this);
                } catch (Exception e) {
                    e.printStackTrace();
                    exception = e;
                    return null;
                }
            }

            @Override
            protected void thenDoUiRelatedWork(T result) {
                if (hasError())
                    onServiceBackground.onError(exception);
                else
                    onServiceBackground.onSuccess(result);
            }
        });
    }

    private boolean hasError() {
        return exception != null;
    }

    public static abstract class OnServiceBackground<T> {

        public T execute(ServiceTask serviceTask) throws Exception {
            return null;
        }

        public void onProgressUpdate() {
        }

        public void onSuccess(T result) {
        }

        public void onError(Exception e) {
        }
    }
}

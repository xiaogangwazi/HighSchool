package com.gaoxiaobang.community.kafka;

import io.netty.util.concurrent.RejectedExecutionHandlers;
import lombok.extern.java.Log;

import java.util.Map;
import java.util.concurrent.*;
import java.util.function.Consumer;
@Log
public abstract class DefaultExecute implements  Execute {
    private static BlockingQueue<Runnable> blockingQueue = new LinkedBlockingDeque<>();
    private static ThreadPoolExecutor threadpool= new ThreadPoolExecutor(4,8,10,TimeUnit.SECONDS,blockingQueue);

    private Consumer<Exception> onError;
    private Consumer<Object> onSuccess;
    private static Map<String,Execute> executeMap= new ConcurrentHashMap<>();
    public static void addExecute(String key,Execute execute){
        executeMap.put(key,execute);
    }
    public static void deleteExecute(String key){
        executeMap.remove(key);
    }
    public static Execute getExecute(String key){
        return executeMap.get(key);
    }
    @Override
    public void execute() {
            threadpool.execute(()->{
                try {
                    execute0();
                    if(onSuccess!=null){
                        onSuccess.accept(null);
                    }
                } catch (Exception e) {
                    if(onError!=null) {
                        log.warning("消息队列任务执行错误："+e);
                        onError.accept(e);
                    }
                }
            });


    }
    public abstract   void execute0() throws Exception;

    @Override
    public void onError(Consumer consumer) {
            this.onError=consumer;
    }

    @Override
    public void onSuccess(Consumer consumer) {
            this.onSuccess=consumer;
    }
}

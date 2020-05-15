package com.gaoxiaobang.community.kafka;

import java.util.function.Consumer;

/**
 * 事件执行类，提供一个执行方法，成功执行方法，失败执行方法，具体的执行操作有子类自行定义
 */
public interface Execute {
     void execute() ;
     void onError(Consumer consumer);
     void onSuccess( Consumer consumer);
}

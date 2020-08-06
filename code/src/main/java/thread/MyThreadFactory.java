package thread;


import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * 自定义 MyThreadFactory  实现 ThreadFactory（线程池创建线程的方法）
 *
 * 参照原生 java.util.concurrent.Executors 中  DefaultThreadFactory
 *
 */
public class MyThreadFactory implements ThreadFactory {

    /**
     * 变量这块与原生方法中相同
     */
    // 线程池编号
    private static final AtomicInteger poolNumber = new AtomicInteger(1);
    // 线程编号
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    // 名称前缀
    private String namePrefix;
    // 线程组
    private ThreadGroup group;


    /**
     * MyThreadFactory 是实现接口 ThreadFactory
     * 若 new 此对象 自定义了有参构造方法，则无参构造方法必须显式声明，否则会报错
     */
    public MyThreadFactory() {

    }

    // 自定义有参构造方法【仅重写了这里】
    MyThreadFactory(String name){

        // namePrefix 例： UserModule-1-thread-
        this.namePrefix = namePrefix = name + "-" + poolNumber.getAndIncrement() + "-thread-";
        SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();

    }




    // 重写 newThread方法 与原方法相同  未变更
    @Override
    public Thread newThread(Runnable runnable) {

        /**
         * 创建一个线程
         *
         * group : ThreadGroup
         * runnable : Runnable target
         * name : 线程名称 namePrefix + 自增threadNumber
         * stackSize : 默认为 0 【多个构造方法中不传此参数，就默认为0】
         *              设置这个参数以及这个参数是否生效取决于平台，和实际生产需要
         */
        Thread thread = new Thread(group, runnable, namePrefix + threadNumber.getAndIncrement(), 0);

        if (thread.isDaemon()) {
            thread.setDaemon(false);
        }

        if (thread.getPriority() != 5) {
            thread.setPriority(5);
        }
        // 上面代码中 5 代表 Thread.NORM_PRIORITY
        // 经查看源码 --> 有三种优先级如下：
        //    public static final int MIN_PRIORITY = 1;
        //    public static final int NORM_PRIORITY = 5;
        //    public static final int MAX_PRIORITY = 10;
        // 即：上段代码可转换为如下
//        if (thread.getPriority() != Thread.NORM_PRIORITY) {
//            thread.setPriority(Thread.NORM_PRIORITY);
//        }

        return thread;
    }
}

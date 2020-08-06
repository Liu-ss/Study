package thread;

import java.io.IOException;
import java.util.concurrent.*;

public class ThreadStudy {


    public static void main(String[] args) throws IOException {

        // test
//        MyThreadFactory myThreadFactory = new MyThreadFactory();


        ThreadPoolExecutor executor = new ThreadPoolExecutor(10,20,1, TimeUnit.MINUTES,
                new LinkedBlockingDeque<>(), new MyThreadFactory("UserModule"));

        try {

            /**
             * 1.
             */
//        executor.execute(()->{
//            System.out.println("保存用户信息");
//            throw new NullPointerException();
//        });

            executor.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("保存用户信息");
                    throw new NullPointerException();
                }
            });

            System.in.read();


            /**
             * 2. 可以通过   CompletionService 来提交任务，获得执行结果
             */
//            CompletionService<String> completionService = new ExecutorCompletionService<>(executor);
//            completionService.submit(()->{
//                System.out.println("test");
//                throw new NullPointerException();
//            });
//
//            Future<String> future = completionService.take();
//            String result = future.get();
//            System.out.println(result);

        } catch (Exception e){
            throw e;
        } finally {
            // 所有线程执行完毕 关闭线程池
            executor.shutdown();
        }

    }




}

AndroidBucket
=============

###Android开发常用整理（不断扩充中）<br/>包含各种工具类、线程池、日志、自定义的控件、程序崩溃捕捉处理、默认的Application配置、常用的Adaptr等

###注意: <br/>
> 如果需要使用FragmentTabAdapter，则需要android-support-v4.jar的支持（以兼容低版本）

###使用方法<br/>
        clone代码，并在项目中引入AndroidBucket。

###线程池、日志、程序崩溃捕捉处理等配置方法<br/>
        新建MyApplication，继承ABApplication，根据需要实现里面的方法
        initThreadPool(); // 初始化线程池
        initLogger(); // 初始化日志工具
        initImageLoader(); // 初始化图片加载器
        initCrashHandler(); // 初始化程序崩溃捕捉处理
        注意：除了图片加载器外，其他的初始化操作ABApplication都提供了默认的初始化支持。
        图片加载器推荐使用[ImageLoaderSample](https://github.com/wangjiegulu/ImageLoaderSample)项目。

        MyApplication：
        public class ABApplication extends Application{
            @Override
            public void onCreate() {
                super.onCreate();
            }

            /**
             * 初始化线程池
             */
            protected void initThreadPool(){
                ThreadPool.initThreadPool(7);
            }

            /**
             * 初始化日志
             */
            protected void initLogger(){
                Logger.initLogger(null);
            }

            /**
             * 初始化图片加载器
             */
            protected void initImageLoader(){

            }

            /**
             * 初始化程序崩溃捕捉处理
             */
            protected void initCrashHandler(){
                ABCrashHandler.init(getApplicationContext());
            }

        }


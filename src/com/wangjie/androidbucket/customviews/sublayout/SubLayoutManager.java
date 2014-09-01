package com.wangjie.androidbucket.customviews.sublayout;

import android.content.Context;
import android.view.ViewGroup;
import com.wangjie.androidbucket.log.Logger;
import com.wangjie.androidbucket.manager.OnActivityLifeCycleListener;
import com.wangjie.androidbucket.objs.DelayObj;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjie on 14-5-4.
 */
public class SubLayoutManager<T extends SubLayout> {
    public static final String TAG = SubLayoutManager.class.getSimpleName();

    /**
     * 页面切换监听器
     * @param <T>
     */
    public static abstract class LayoutSwitchListener<T extends SubLayout>{
        /**
         * 如果切换到的是当前页面回调
         * @param subLayout
         * @param position
         */
        public void switchSelf(T subLayout, int position){}

        /**
         * 切换完成回调
         * @param subLayout
         * @param position
         */
        public void switchCompleted(T subLayout, int position){};

    }


    private Context context;
    /**
     * 对要切换的布局延迟加载并缓存
     */
    private List<DelayObj<T>> subLayouts = new ArrayList<DelayObj<T>>();

    /**
     * 当前切换页索引
     */
    private int currentItemIndex = -1;

    /**
     *
     * 获取需要替换的ViewGroup
     * <FrameLayout
     *    android:id="@+id/main_content_view"
     *    android:layout_width="match_parent"
     *    android:layout_height="0dp"
     *    android:layout_weight="1.0"
     *    />
     * contentView = (ViewGroup) findViewById(R.id.main_content_view);
     * 然后通过构造方法传入
     * @return
     */
    private ViewGroup contentView;

    /**
     * 切换的布局的数量
     */
    private int subLayoutSize;

    /**
     * 布局切换监听器
     */
    private LayoutSwitchListener<T> switchListener;

    /**
     * 构造方法，把指定的页面加入缓存（只记录Class，页面真正显示时才会生成对象）
     * @param context
     * @param contentView
     * @param slszzs
     */
    public SubLayoutManager(Context context, ViewGroup contentView, Class<? extends T>... slszzs) {
        this.context = context;
        this.contentView = contentView;
        subLayoutSize = slszzs.length;
        for(int i = 0; i < subLayoutSize; i++){ // 页面对象加入缓存
            DelayObj<T> delayObj = new DelayObj<T>();
            delayObj.setClazz(slszzs[i]);
            subLayouts.add(delayObj);
        }

    }

    /**
     * 切换页面
     * @param position
     */
    public void switchLayout(int position){
        Logger.d(TAG, "-----switch start-----------------------");
        Logger.d(TAG, "switch before.........: " + subLayouts);
        try{
            if(position == currentItemIndex){ // 如果切换的依然是当前页
//            subLayouts.get(position).refreshView();
                if(null != switchListener){ // 如果设置了监听器，则回调
                    switchListener.switchSelf(subLayouts.get(position).getObj(), position);
                }
                return;
            }

            DelayObj<T> delayObj = subLayouts.get(position); // 获取指定position的延迟对象
            T subLayout = delayObj.getObj();
            if(null == subLayout){ // 如果延迟对象还没有生成，则反射生成
                Constructor<? extends T> cons = delayObj.getClazz().getConstructor(new Class[]{Context.class});
                subLayout = cons.newInstance(context);
                delayObj.setObj(subLayout);
            }

            if(null == subLayout.getLayout()){ // 如果layout为空，即setContentView方法未调用
                Logger.e(TAG, subLayout.getClass().getSimpleName() + "'s layout is null！！please invoke setContentView method...");
                return;
            }

            T curObj = subLayouts.get(currentItemIndex).getObj();

            // 调用上一个SubLayout的回调方法onPause方法
            if(-1 != currentItemIndex){
                if(OnActivityLifeCycleListener.class.isAssignableFrom(curObj.getClass())){
                    ((OnActivityLifeCycleListener)curObj).onActivityPauseCallback();
                }

            }

            // 真正的替换过程
            contentView.removeAllViews();
            contentView.addView(subLayout.getLayout(), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            if(!subLayout.isInited()){ // 如果第一次被替换，则回调initLayout方法进行初始化
                subLayout.initLayout();
            }

            // 调用这一个SubLayout的回调方法onResume方法
            currentItemIndex = position;

            if(OnActivityLifeCycleListener.class.isAssignableFrom(curObj.getClass())){
                ((OnActivityLifeCycleListener)curObj).onActivityResumeCallback();
            }

            if(null != switchListener){ // 如果设置了监听器，则回调
                switchListener.switchCompleted(subLayout, position);
            }

            Logger.d(TAG, "switch after.........: " + subLayouts);
            Logger.d(TAG, "-----switch end-----------------------");

        }catch(Exception ex){
            Logger.e(TAG, ex);
        }

    }


    /**
     * 获取所有页面（有些页面layout可能为空，因为可能这些页面没有被切换过）
     * @return
     */
    public List<DelayObj<T>> getSubLayouts() {
        return subLayouts;
    }

    /**
     * 获取当前页面索引
     * @return
     */
    public int getCurrentItemIndex() {
        return currentItemIndex;
    }

    /**
     * 获取需要替换的ViewGroup
     * @return
     */
    public ViewGroup getContentView() {
        return contentView;
    }

    /**
     * 获取所有页面数量
     * @return
     */
    public int getSubLayoutSize() {
        return subLayoutSize;
    }

    /**
     * 获取页面切换监听器
     * @return
     */
    public LayoutSwitchListener<T> getSwitchListener() {
        return switchListener;
    }

    /**
     * 设置页面切换监听器
     * @param switchListener
     */
    public void setSwitchListener(LayoutSwitchListener<T> switchListener) {
        this.switchListener = switchListener;
    }


    /**
     * 在页面退出或者程序退出时进行调用（如在Activity的onDestory()方法中调用）
     */
    public void destoryClear(){
        context = null;
        subLayouts.clear();
        subLayouts = null;
        contentView = null;
        switchListener = null;
    }



}

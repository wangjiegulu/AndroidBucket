package com.wangjie.androidbucket.system;

import android.view.KeyEvent;
import android.widget.EditText;

/**
 * Created by wangjie on 6/12/14.
 */
public class SimulateEvents {

    /**
     * 模拟输入框删除
     * @param et
     */
    public static void editTextDel(EditText et){
        // 动作按下
        int action = KeyEvent.ACTION_DOWN;
        // code:删除，其他code也可以
        int code = KeyEvent.KEYCODE_DEL;
        KeyEvent event = new KeyEvent(action, code);
        et.onKeyDown(KeyEvent.KEYCODE_DEL, event);
    }


}

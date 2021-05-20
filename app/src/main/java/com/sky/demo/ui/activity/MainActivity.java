package com.sky.demo.ui.activity;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.sky.demo.R;
import com.sky.demo.api.IDataResultImpl;
import com.sky.demo.model.ActivityModel;
import com.sky.demo.model.CouponBO;
import com.sky.demo.other.factory.abstractfactory.Boy;
import com.sky.demo.other.factory.abstractfactory.Girl;
import com.sky.demo.other.factory.abstractfactory.HNFactory;
import com.sky.demo.other.factory.abstractfactory.MCFctory;
import com.sky.demo.other.factory.abstractfactory.PersonFactory;
import com.sky.demo.other.factory.factory.HairFactory;
import com.sky.demo.other.factory.factory.HairInterface;
import com.sky.demo.other.factory.factory.LeftHair;
import com.sky.demo.ui.BaseActivity;
import com.sky.demo.ui.activity.custom.PullDownActivity;
import com.sky.demo.ui.adapter.MainAdapter;
import com.sky.demo.ui.widget.pending.MyRecyclerView;
import com.sky.demo.utils.LogUtils;
import com.sky.demo.utils.NotficationManager;
import com.sky.demo.utils.http.HttpDataUtils;
import com.sky.utils.JumpAct;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author sky QQ:1136096189
 * @Description: DrawerLayout的应用
 * @date 15/11/28 下午12:38
 */
@ContentView(R.layout.main)
public class MainActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @ViewInject(R.id.dl_mian_drawer)//主布局
    private DrawerLayout mDrawerLayout;
    @ViewInject(R.id.nv_main_navigation)//侧滑栏
    private NavigationView mNavigationView;

    @ViewInject(R.id.swipe)//下拉刷新的框架
    private SwipeRefreshLayout swipe;
    @ViewInject(R.id.recycle)
    private MyRecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;
    private MainAdapter adapter;

    private int lastVisibleItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar();
        showLoading();
        setLeftButton(R.mipmap.ic_menu);
        //侧划栏中的点击事件
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                item.setChecked(true);
                mDrawerLayout.closeDrawers();

                factoryModel();
                return true;
            }
        });
        final FloatingActionButton fab = $(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSnack(fab);
            }
        });
        //刷新
        toRefresh();
//        startService();
//        bindService();
        //实体类测试
//        Student p = new Student();
//        p.setId(12);
//        p.setName("name");
//        LogUtils.i(p.toString());
        HttpDataUtils.getData(new IDataResultImpl<List<CouponBO>>() {
            @Override
            public void onSuccessData(List<CouponBO> data) {
                hideLoading();
            }
        });
    }

    /**
     * 工厂模式应用，待优化
     */
    private void factoryModel() {
        //工厂模式
        HairInterface leftHair = new LeftHair();
        leftHair.draw();
        HairFactory factory = new HairFactory();
        HairInterface right = factory.getHair("right");
        right.draw();
        HairInterface left = factory.getHairByClass("com.sky.demo.other.factory.factory.LeftHair");
        left.draw();
        HairInterface hair = factory.getHairByClassKey("in");
        hair.draw();

        //抽象工厂模式
        PersonFactory facoty = new MCFctory();
        Girl girl = facoty.getGirl();
        girl.drawWomen();
        PersonFactory boyfacoty = new HNFactory();
        Boy boy = boyfacoty.getBoy();
        boy.drawMan();
    }

    /**
     * 页面刷新
     */
    private void toRefresh() {
        //设置swipe的开始位置与结束位置
        swipe.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80, getResources()
                        .getDisplayMetrics()));
        //为进度圈设置颜色
        swipe.setColorSchemeColors(R.color.red, R.color.white, R.color.black);
        swipe.setOnRefreshListener(this);//监听

        recyclerView.setHasFixedSize(true);
        adapter = new MainAdapter(R.layout.adapter_main);
        recyclerView.setAdapter(adapter);

        adapter.setDatas(getData());
        mLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        adapter.setOnItemClickListener(new MainAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position == 0) {
                    Intent intent = new Intent();
                    ComponentName componentName = new
                            ComponentName(MainActivity.this, adapter.getDatas().get(position).getComponentName());
                    intent.setComponent(componentName);
                    startActivity(intent);
                } else if (position == 1) {
                    JumpAct.jumpActivity(MainActivity.this, adapter.getDatas().get(position).getComponentName());
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(new Intent().setClassName(MainActivity.this, adapter.getDatas().get(position).getComponentName()),
                            ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
                } else
                    JumpAct.jumpActivity(MainActivity.this, adapter.getDatas().get(position).getComponentName());
                try {
                    Class.forName(adapter.getDatas().get(position).getComponentName());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onItemLongClick(View view, final int position) {
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView,
                                             int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter.getItemCount()) {
                    handler.sendEmptyMessageDelayed(1, 000);
                }
//                if (newState == RecyclerView.SCROLL_STATE_IDLE
//                        && lastVisibleItem + 1 == adapter.getItemCount()) {
//                    swipe.setRefreshing(true);
//                    // 此处在现实项目中，请换成网络请求数据代码，sendRequest .....
//                    handler.sendEmptyMessageDelayed(0, 3000);
//                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //  dx：大于0，向右滚动    小于0，向左滚动
                //  dy：大于0，向上滚动    小于0，向下滚动
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            }
        });
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getData();
            }
        }, 100);
    }

    /**
     * 从manifest中获取activity的信息
     *
     * @return
     */
    protected List<ActivityModel> getData() {
        List<ActivityModel> activityInfos = new ArrayList<>();

        //Intent mainIntent = new Intent(Intent.ACTION_MAIN);//获取action为ACTION_MAIN的activity
        //mainIntent.addCategory(Intent.CATEGORY_SAMPLE_CODE);//筛选category为sample code的act
        //mainIntent.setPackage(getPackageName());//只选出自己应用的act
        Intent mainIntent = new Intent("com.sky.action");//自定义的action
        PackageManager pm = getPackageManager();
        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(mainIntent, 0);

        if (null == resolveInfos)
            return activityInfos;

        int len = resolveInfos.size();
        for (int i = 0; i < len; i++) {
            ResolveInfo info = resolveInfos.get(i);
            //获取label
            CharSequence labelSeq = info.loadLabel(pm);
            String label = labelSeq != null
                    ? labelSeq.toString()
                    : info.activityInfo.name;
            //获取说明
            int descriptionRes = info.activityInfo.descriptionRes;
            String describe = descriptionRes == 0
                    ? "未添加"
                    : getResources().getString(descriptionRes);
            //获取icon  Drawable icon = info.loadIcon(pm);
            int iconRes = info.activityInfo.icon;
            int icon = iconRes == 0
                    ? R.mipmap.ic_banner
                    : iconRes;
            ActivityModel activityModel = new ActivityModel(label, describe, icon, info.activityInfo.name);
            activityInfos.add(activityModel);
        }
        //排序
        Collections.sort(activityInfos, sDisplayNameComparator);
//        Collections.sort(activityInfos);//使用activityModel中的compareTo进行排序
        return activityInfos;
    }

    /**
     * 为筛选出的act进行排序
     */
    private final static Comparator<ActivityModel> sDisplayNameComparator =
            new Comparator<ActivityModel>() {
                private final Collator collator = Collator.getInstance();

                @Override
                public int compare(ActivityModel lhs, ActivityModel rhs) {
                    return collator.compare(lhs.getClassName(), rhs.getClassName());
                }
            };

    @Override
    public void leftOnClick() {
        mDrawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //menu.add(Menu.NONE,Menu.FIRST+1,1000,"dd").setIcon(R.mipmap.back).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_architecture:
                Intent architecture = new Intent();
                architecture.setClassName("com.cx.architecture",
                        "com.cx.architecture.ui.activity.LoginActivity");
                try {
                    startActivity(architecture);
                } catch (ActivityNotFoundException e) {
                    showToast("LoginActivity未找到");
                }
                break;
            case R.id.action_materialdesigndemo:
                Intent materialdesigndemo = new Intent();
                materialdesigndemo.setClassName("com.gc.materialdesigndemo",
                        "com.gc.materialdesigndemo.ui.MainActivity");
                try {
                    startActivity(materialdesigndemo);
                } catch (ActivityNotFoundException e) {
                    showToast("MainActivity未找到");
                }
                break;
        }
        return false;
    }

    private long lastBack;

    @Override
    public void onBackPressed() {
        //监测mNavigationView侧滑栏是否展开
        if (mNavigationView.isShown()) {
            mDrawerLayout.closeDrawers();
//            mDrawerLayout.closeDrawer(GravityCompat.START);
            return;
        }
        long nowCurrent = System.currentTimeMillis();
        if (nowCurrent - lastBack > 3000) {
            showToast(getResources().getString(R.string.toast_exit));
            lastBack = nowCurrent;
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onRefresh() {
        //swipe刷新
        handler.sendEmptyMessageDelayed(0, 3000);
    }

    @Override
    protected void handler(Message msg) {
        super.handler(msg);
        switch (msg.what) {
            case 0:
                swipe.setRefreshing(false);
                break;
            case 1:
                setSnack(recyclerView);
                break;

        }
    }

    /**
     * 设置SnackBar
     *
     * @param view
     */
    public void setSnack(final View view) {
        Snackbar.make(view, "正在加载，请稍后", Snackbar.LENGTH_SHORT)
                .setAction("cancel", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showToast("已取消");
                        NotficationManager.PushNotification(view.getContext(), R.mipmap.logo, "sky", "你中奖了",
                                PullDownActivity.class, "骗你的");
                    }
                }).show();
    }

    public void checkTextChange(EditText editText, final ImageView view) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() >= 1) {
                    view.setVisibility(View.VISIBLE);
                } else {
                    view.setVisibility(View.INVISIBLE);
                }
//                if (isChecked) {
//                    隐藏密码
//                    et_pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//                } else {
//                    显示密码
//                    et_pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
//                }
            }
        });
    }

    private int screenWidth, screenHeight;
    private int lastX, lastY;

    public void drag(View view) {
        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        screenWidth = outMetrics.widthPixels;
        screenHeight = outMetrics.heightPixels;
        view.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int ea = event.getAction();
                switch (ea) {
                    case MotionEvent.ACTION_DOWN:
                        lastX = (int) event.getRawX();// 获取触摸事件触摸位置的原始X坐标
                        lastY = (int) event.getRawY();
                        break;

                    case MotionEvent.ACTION_MOVE:
                        int dx = (int) event.getRawX() - lastX;
                        int dy = (int) event.getRawY() - lastY;

                        int l = v.getLeft() + dx;
                        int b = v.getBottom() + dy;
                        int r = v.getRight() + dx;
                        int t = v.getTop() + dy;

                        // 下面判断移动是否超出屏幕
                        if (l < 0) {
                            l = 0;
                            r = l + v.getWidth();
                        }

                        if (t < 0) {
                            t = 0;
                            b = t + v.getHeight();
                        }

                        if (r > screenWidth) {
                            r = screenWidth;
                            l = r - v.getWidth();
                        }

                        if (b > screenHeight) {
                            b = screenHeight;
                            t = b - v.getHeight();
                        }
                        v.layout(l, t, r, b);

                        lastX = (int) event.getRawX();
                        lastY = (int) event.getRawY();
                        LogUtils.i("当前位置：" + l + "," + t + "," + r + "," + b);
                        v.postInvalidate();
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return true;
            }
        });
    }

    public long getAvailMemory(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
        manager.getMemoryInfo(info);
//        return Formatter.formatFileSize(context,info.availMem);
        return info.availMem / 1024 / 1024;
    }

    private long getTotalMemory(Context context) {
        String str1 = "/proc/meminfo";// 系统内存信息文件
        String str2;
        String[] arrayOfString;
        long initial_memory = 0;
        try {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(
                    localFileReader, 8192);
            str2 = localBufferedReader.readLine();// 读取meminfo第一行，系统总内存大小
            arrayOfString = str2.split("\\s+");
            for (String num : arrayOfString) {
                Log.i(str2, num + "\t");
            }
            initial_memory = Integer.valueOf(arrayOfString[1]).intValue() * 1024;// 获得系统总内存，单位是KB，乘以1024转换为Byte
            localBufferedReader.close();
        } catch (IOException e) {
        }
        //return Formatter.formatFileSize(context, initial_memory);// Byte转换为KB或者MB，内存大小规格化
        return initial_memory / (1024 * 1024);
    }

    private void clear(Context context) {
        ActivityManager activityManger = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> list = activityManger.getRunningAppProcesses();
        if (list != null)
            for (int i = 0; i < list.size(); i++) {
                ActivityManager.RunningAppProcessInfo apinfo = list.get(i);

                System.out.println("pid---->>>>>>>" + apinfo.pid);
                System.out.println("processName->> " + apinfo.processName);
                System.out.println("importance-->>" + apinfo.importance);
                String[] pkgList = apinfo.pkgList;

                if (apinfo.importance > ActivityManager.RunningAppProcessInfo.IMPORTANCE_SERVICE) {
                    // Process.killProcess(apinfo.pid);
                    for (int j = 0; j < pkgList.length; j++) {
                        //2.2以上是过时的,请用killBackgroundProcesses代替
                        /**清理不可用的内容空间**/
                        //activityManger.restartPackage(pkgList[j]);
                        activityManger.killBackgroundProcesses(pkgList[j]);
                    }
                }
            }
    }
}

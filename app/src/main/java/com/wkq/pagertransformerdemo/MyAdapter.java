package com.wkq.pagertransformerdemo;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * ================================================
 * 作者：WKQ
 * 时间:  2017/3/13 11:00
 * 用途:
 * 备注:
 * <p>
 * ================================================
 */

public class MyAdapter extends PagerAdapter {

    List<Integer> list;
    Context context;

    private ViewPager viewpager;

    LayoutInflater inflater = null;
    //用于存储回收掉的View
    private List<WeakReference<RelativeLayout>> viewList;

    public MyAdapter(List<Integer> list, Context context) {
        this.list = list;
        this.context = context;

        viewList = new ArrayList<WeakReference<RelativeLayout>>();
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    //实例化viewpager选项卡
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (viewpager == null) {
            viewpager = (ViewPager) container;
        }
        View view = null;
        // 从废弃的里去取 取到则使用 取不到则创建
        if (viewList.size() > 0) {
            if (viewList.get(0) != null) {
                view = initView(viewList.get(0).get(), position);
                viewList.remove(0);
            }
        }
        view = initView(null, position);
        viewpager.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //存储离屏的View 等待复用
        RelativeLayout view = (RelativeLayout) object;
        container.removeView(view);
        viewList.add(new WeakReference<RelativeLayout>(view));

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    private View initView(RelativeLayout view, int position) {
        ViewHolder viewHolder = null;
        if (view == null) {
            view = (RelativeLayout) inflater.inflate(R.layout.item, null);
            viewHolder = new ViewHolder();
            viewHolder.iv = (ImageView) view
                    .findViewById(R.id.iv);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        /**
         * 初始化数据
         */
        if (list != null && position < list.size()) {
            int article = list.get(position);
            viewHolder.iv.setImageResource(article);
        }
        return view;
    }
    private class ViewHolder {
        ImageView iv;
    }
}

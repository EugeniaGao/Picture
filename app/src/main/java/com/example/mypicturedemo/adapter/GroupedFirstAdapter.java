package com.example.mypicturedemo.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.mypicturedemo.R;
import com.example.mypicturedemo.bean.ChildBean;
import com.example.mypicturedemo.bean.GroupBean;
import com.example.mypicturedemo.util.BitmapUtil;
import com.ycbjie.adapter.AbsGroupAdapter;
import com.ycbjie.adapter.GroupViewHolder;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2019/9/18
 *     desc  : 这是普通的分组Adapter 每一个组都有头部、尾部和子项。
 *     revise:
 * </pre>
 */
public class GroupedFirstAdapter extends AbsGroupAdapter {

    private List<GroupBean> mGroups;
    private Context mContext;
    public GroupedFirstAdapter(Context context, List<GroupBean> groups) { //看一下有几组
        super(context);
        mGroups = groups;
        mContext=context;
    }

    @Override
    public int getGroupCount() { //返回组的个数
        return mGroups == null ? 0 : mGroups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) { //返回每个组中孩子的个数
        if (mGroups!=null){
            ArrayList<ChildBean> children = mGroups.get(groupPosition).getChildren();
            return children == null ? 0 : children.size();
        }
        return 0;
    }

    @Override
    public boolean hasHeader(int groupPosition) {
        return true;
    }

    @Override
    public boolean hasFooter(int groupPosition) {
        return true;
    }

    @Override
    public int getHeaderLayout(int viewType) { //头布局
        return R.layout.item_car_picture_header;
    }

    @Override
    public int getFooterLayout(int viewType) {  //脚布局
        return R.layout.item_car_picture_footer;
    }

    @Override
    public int getChildLayout(int viewType) {  //子布局

        return R.layout.item_picture_view;
    }

    @Override
    public void onBindHeaderViewHolder(GroupViewHolder holder, int groupPosition) { //头部数据的绑定
        GroupBean entity = mGroups.get(groupPosition);
        TextView textView = holder.get(R.id.tv_header);
        textView.setText(entity.getHeader());
    }

    @Override
    public void onBindFooterViewHolder(GroupViewHolder holder, int groupPosition) { //脚上数据的绑定

    }

    @Override
    public void onBindChildViewHolder(GroupViewHolder holder, int groupPosition, int childPosition) { //孩子数据的绑定
        ChildBean childBean = mGroups.get(groupPosition).getChildren().get(childPosition);
        ImageView imageView = holder.get(R.id.dragGridView_image); //加号的布局
        //解析bean里面的数据为图片进行加载显示
        BitmapUtil.setImageViewByImagLoading(mContext, childBean.getChild(), imageView);

    }

}

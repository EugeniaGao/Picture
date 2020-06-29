package com.example.mypicturedemo;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mypicturedemo.bean.ChildBean;
import com.example.mypicturedemo.bean.GroupBean;
import com.ycbjie.adapter.AbsGroupAdapter;
import com.ycbjie.adapter.GroupLayoutManager;
import com.ycbjie.adapter.GroupViewHolder;
import com.ycbjie.adapter.OnChildClickListener;
import com.ycbjie.adapter.OnFooterClickListener;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private GroupedFirstAdapter mAdapter;
    private List<GroupBean> list = new ArrayList<>();
    private static String[]data=new String[]{"房本","租房合同","购房合同","贷款合同","宅基地证"};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView mRecyclerView = findViewById(R.id.recycler_view);
        mAdapter = new GroupedFirstAdapter(this, list);

        //点击尾部添加照片
        mAdapter.setOnFooterClickListener(new OnFooterClickListener() {  //脚部的点击事件,点击后添加子条目,根据获取到的图片得到要看到的数据
            @Override
            public void onFooterClick(AbsGroupAdapter adapter, GroupViewHolder holder,
                                      int groupPosition) {
                Toast.makeText(MainActivity.this, "组尾：groupPosition = " + groupPosition,Toast.LENGTH_LONG).show();

                GroupBean groupBean = list.get(groupPosition);
                //设置footer点击后不可见状态
//                groupBean.setFooter("");
                ArrayList<ChildBean> children = groupBean.getChildren();
                //需要打开相册,获取照片的path,解析成imageview加载到对应的组上面

                int size = children.size();
                for (int j = 0; j < 1; j++) {  //后面这个数据是
                    children.add(new ChildBean("逗比"));
                }

//                通知一组里的多个子项插入//// TODO: 2020/6/29 需要加载一个子项,但是不是张的
                mAdapter.notifyChildRangeInserted(groupPosition,size,1);

            }
        });
        //点击孩子移除照片
        mAdapter.setOnChildClickListener(new OnChildClickListener() { //孩子的点击
            @Override
            public void onChildClick(AbsGroupAdapter adapter, GroupViewHolder holder,
                                     int groupPosition, int childPosition) {
                Toast.makeText(MainActivity.this,"子项：groupPosition = " + groupPosition
                        + ", childPosition = " + childPosition,Toast.LENGTH_LONG).show();
            }

        });
        mRecyclerView.setAdapter(mAdapter);
        //直接使用GroupGridLayoutManager实现子项的Grid效果
        GroupLayoutManager gridLayoutManager = new GroupLayoutManager(this, 3, mAdapter){
            //重写这个方法 改变子项的SpanSize。
            //这个跟重写SpanSizeLookup的getSpanSize方法的使用是一样的。
            @Override
            public int getChildSpanSize(int groupPosition, int childPosition) {
                return super.getChildSpanSize(groupPosition, childPosition);
            }
        };
        mRecyclerView.setLayoutManager(gridLayoutManager);

        getData();
    }

    private void getData() {
        ArrayList<GroupBean> groups = getGroups(data.length, 0);
        list.addAll(groups);
        mAdapter.notifyDataSetChanged();
    }


    /**
     * 获取组列表数据
     *
     * @param groupCount    组数量
     * @param childrenCount 每个组里的子项数量
     *
     * @return
     */
    public static ArrayList<GroupBean> getGroups(int groupCount, int childrenCount) {
        ArrayList<GroupBean> groups = new ArrayList<>();
        for (int i = 0; i < groupCount; i++) {
            ArrayList<ChildBean> children = new ArrayList<>();
            for (int j = 0; j < childrenCount; j++) {
                children.add(new ChildBean("第" + (i + 1) + "组第" + (j + 1) + "项"));
            }
            groups.add(new GroupBean(data[i],
                    "第" + (i + 1) + "组尾部", children));
        }
        return groups;
    }

}

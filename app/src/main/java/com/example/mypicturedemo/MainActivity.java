package com.example.mypicturedemo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mypicturedemo.adapter.GroupedFirstAdapter;
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
    private ArrayList<String> dataSourceList = new ArrayList<>();//每一次进去时点击的图片数量,出来后
    private List<GroupBean> list = new ArrayList<>();
    private static String[] data = new String[]{"房本", "租房合同", "购房合同", "贷款合同", "宅基地证"};
    private List<String> phothpathList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView mRecyclerView = findViewById(R.id.recycler_view);
        mAdapter = new GroupedFirstAdapter(this, list);

        //点击尾部添加照片
        mAdapter.setOnFooterClickListener(new OnFooterClickListener() {
            @Override
            public void onFooterClick(AbsGroupAdapter adapter, GroupViewHolder holder,
                                      int groupPosition) {
                Toast.makeText(MainActivity.this, "组尾：groupPosition = " + groupPosition, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, SelectPictureActivity.class);
                intent.putExtra("intent_max_num", dataSourceList.size());
                intent.putExtra("groupPosition",groupPosition);
                startActivityForResult(intent, 100);

            }
        });
        //点击孩子移除照片
        mAdapter.setOnChildClickListener(new OnChildClickListener() { //孩子的点击
            @Override
            public void onChildClick(AbsGroupAdapter adapter, GroupViewHolder holder,
                                     int groupPosition, int childPosition) {
                Toast.makeText(MainActivity.this, "子项：groupPosition = " + groupPosition
                        + ", childPosition = " + childPosition, Toast.LENGTH_LONG).show();

                list.get(groupPosition).getChildren().remove(childPosition);//删除指定的子孩子
                mAdapter.notifyDataSetChanged();
            }

        });
        mRecyclerView.setAdapter(mAdapter);
        //直接使用GroupGridLayoutManager实现子项的Grid效果
        GroupLayoutManager gridLayoutManager = new GroupLayoutManager(this, 3, mAdapter) {
            //重写这个方法 改变子项的SpanSize。
            //这个跟重写SpanSizeLookup的getSpanSize方法的使用是一样的。
            @Override
            public int getChildSpanSize(int groupPosition, int childPosition) {
                return super.getChildSpanSize(groupPosition, childPosition);
            }
        };
        mRecyclerView.setLayoutManager(gridLayoutManager);

        getData();//界面数据的显示
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == -1) { //获取到图片
            //获取到图片的path集合
            phothpathList = data.getStringArrayListExtra("paths");
            int groupPosition = data.getIntExtra("groupPosition",0);
            GroupBean groupBean = list.get(groupPosition);
            ArrayList<ChildBean> children = groupBean.getChildren();
            int size = children.size();
                for (int j = 0; j < phothpathList.size(); j++) {  //后面这个数据是添加了几个子孩子,这个必须要有
                    children.add(new ChildBean(phothpathList.get(j)));
                }
//                通知一组里的多个子项插入
                mAdapter.notifyChildRangeInserted(groupPosition,size,phothpathList.size());


        }
    }

}

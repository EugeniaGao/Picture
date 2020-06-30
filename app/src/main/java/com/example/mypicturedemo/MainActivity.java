package com.example.mypicturedemo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
    private ArrayList<String> dataSourceList = new ArrayList<>();//每一次进去时点击的图片数量,出来后
    private List<GroupBean> list = new ArrayList<>();
    private static String[] data = new String[]{"房本", "租房合同", "购房合同", "贷款合同", "宅基地证"};
    private ArrayList<ChildBean> children;
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

                //设置footer点击后不可见状态
//                groupBean.setFooter("");
                //需要打开相册,获取照片的path,解析成imageview加载到对应的组和位置上面

                Intent intent = new Intent(MainActivity.this, SelectPictureActivity.class);
                intent.putExtra("intent_max_num", dataSourceList.size());
                intent.putExtra("groupPosition",groupPosition);
                startActivityForResult(intent, 100);

//                ArrayList<ChildEntity> children = groupEntity.getChildren();
//                int size = children.size();
//                for (int j = 0; j < 10; j++) {
//                    children.add(new ChildEntity("逗比"));
//                }
//                //通知一组里的多个子项插入
//                mAdapter.notifyChildRangeInserted(groupPosition,size,10);

            }
        });
        //点击孩子移除照片
        mAdapter.setOnChildClickListener(new OnChildClickListener() { //孩子的点击
            @Override
            public void onChildClick(AbsGroupAdapter adapter, GroupViewHolder holder,
                                     int groupPosition, int childPosition) {
                Toast.makeText(MainActivity.this, "子项：groupPosition = " + groupPosition
                        + ", childPosition = " + childPosition, Toast.LENGTH_LONG).show();
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
            Log.d("jing", "onActivityResult: "+groupPosition);
            GroupBean groupBean = list.get(groupPosition);
            ArrayList<ChildBean> children = groupBean.getChildren();
            int size = children.size();
                for (int j = 0; j < phothpathList.size(); j++) {  //后面这个数据是添加了几个子孩子,这个必须要有
                    children.add(new ChildBean(phothpathList.get(j)));
                }
//                通知一组里的多个子项插入//// TODO: 2020/6/29 需要加载一个子项,但是不是张的
                mAdapter.notifyChildRangeInserted(groupPosition,size,phothpathList.size());


        }
    }

}

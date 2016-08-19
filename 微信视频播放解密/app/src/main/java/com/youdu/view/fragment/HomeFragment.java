package com.youdu.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.TextView;

import com.youdu.R;
import com.youdu.activity.SearchActivity;
import com.youdu.adapter.AdAdapter;
import com.youdu.constant.Constant;
import com.youdu.share.ShareDialog;
import com.youdu.zxing.app.CaptureActivity;

import java.util.ArrayList;
import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;


/**
 * *******************************************************
 *
 * @文件名称：HomeFragment.java
 * @文件作者：renzhiqiang
 * @创建时间：2015年10月2日 下午2:56:50
 * @文件描述：完全是普通的Fragment
 * @修改历史：2015年10月2日创建初始版本 ********************************************************
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener, PlatformActionListener {

    /**
     * UI
     */
    private View mContentView;
    private ListView mListView;
    private TextView mQRCodeView;
    private TextView mCategoryView;
    private TextView mSearchView;
    /**
     * data
     */
    private ArrayList<String> mData;
    private AdAdapter mAdapter;

    public HomeFragment() {
        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        mContentView = inflater.inflate(R.layout.fragment_home_layout, container, false);
        initView();
        return mContentView;
    }

    private void initView() {
        mQRCodeView = (TextView) mContentView.findViewById(R.id.qrcode_view);
        mQRCodeView.setOnClickListener(this);
        mCategoryView = (TextView) mContentView.findViewById(R.id.category_view);
        mCategoryView.setOnClickListener(this);
        mSearchView = (TextView) mContentView.findViewById(R.id.search_view);
        mSearchView.setOnClickListener(this);
        mListView = (ListView) mContentView.findViewById(R.id.list_view);
        mAdapter = new AdAdapter(this.getActivity(), mData);
        mListView.setAdapter(mAdapter);
        mListView.setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                mAdapter.updateAdInScrollView();
            }
        });
    }

    private void initData() {
        mData = new ArrayList<>();
        mData.add("false");
        mData.add("true");
        mData.add("false");
        mData.add("false");
        mData.add("false");
        mData.add("false");// 代表要显示广告
        mData.add("false");
        mData.add("false");
        mData.add("false");
        mData.add("false");
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.qrcode_view:
                if (hasPermission(Constant.HARDWEAR_CAMERA_PERMISSION)) {
                    doOpenCamera();
                } else {
                    requestPermission(Constant.HARDWEAR_CAMERA_CODE, Constant.HARDWEAR_CAMERA_PERMISSION);
                }
                break;
            case R.id.category_view:
                ShareDialog dialog = new ShareDialog(mContext);
                dialog.setShareType(Platform.SHARE_IMAGE);
                dialog.setShareTitle("周杰伦");
                dialog.setShareText("我很忙");
                dialog.setImagePhoto(Environment.getExternalStorageDirectory() + "/test2.jpg");
                dialog.show();
                break;
            case R.id.search_view:
                Intent searchIntent = new Intent(mContext, SearchActivity.class);
                mContext.startActivity(searchIntent);
                break;
        }
    }

    @Override
    public void doOpenCamera() {
        Intent intent = new Intent(mContext, CaptureActivity.class);
        mContext.startActivityForResult(intent, 01);
    }

    @Override
    public void onCancel(Platform arg0, int arg1) {
        Log.e("------------->", "cancel");
    }

    @Override
    public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
        Log.e("------------->", arg0.getName() + "arg1:" + arg1);
    }

    @Override
    public void onError(Platform arg0, int arg1, Throwable arg2) {
        Log.e("----->onError", arg2.getMessage());
    }
}
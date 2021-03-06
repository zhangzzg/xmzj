package com.gxtc.huchuan.data.deal;

import com.gxtc.commlibrary.data.BaseRepository;
import com.gxtc.huchuan.bean.CircleCommentBean;
import com.gxtc.huchuan.bean.CircleHomeBean;
import com.gxtc.huchuan.bean.ThumbsupVosBean;
import com.gxtc.huchuan.data.UserManager;
import com.gxtc.huchuan.http.ApiCallBack;
import com.gxtc.huchuan.http.ApiObserver;
import com.gxtc.huchuan.http.ApiResponseBean;
import com.gxtc.huchuan.http.service.AllApi;
import com.gxtc.huchuan.http.service.CircleApi;
import com.gxtc.huchuan.ui.circle.homePage.CircleMainContract;

import java.util.HashMap;
import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by sjr on 2017/2/15.
 * 圈子主页
 */

public class CircleMainRepository extends BaseRepository implements CircleMainContract.Source {

    @Override
    public void getData(int groupId, final int start,long loadTime,
            final ApiCallBack<List<CircleHomeBean>> callBack) {
        String token = UserManager.getInstance().getToken();
        addSub(AllApi.getInstance()
                     .listByGroup(groupId, token, String.valueOf(start),loadTime)
                     .subscribeOn(Schedulers.io())
                     .observeOn(AndroidSchedulers.mainThread())
                     .subscribe(new ApiObserver<ApiResponseBean<List<CircleHomeBean>>>(callBack)));
    }

    @Override
    public void addComment(String content, int id, int groupId, ApiCallBack callBack) {
        AllApi.getInstance().commentCircle(UserManager.getInstance().getToken(), id, content,groupId).subscribeOn(
                Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                new ApiObserver<ApiResponseBean<CircleCommentBean>>(callBack));
    }

    @Override
    public void addCommentReply(HashMap<String, String> map,
            ApiCallBack<CircleCommentBean> callBack) {
        Subscription sub = CircleApi.getInstance().commentReply(map).subscribeOn(
                Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                new ApiObserver<ApiResponseBean<CircleCommentBean>>(callBack));
        addSub(sub);
    }

    @Override
    public void support(String token, int infoId, ApiCallBack<ThumbsupVosBean> callBack) {
        Subscription sub = CircleApi.getInstance().support(token, infoId).subscribeOn(
                Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                new ApiObserver<ApiResponseBean<ThumbsupVosBean>>(callBack));
        addSub(sub);
    }

    @Override
    public void getMoreComment(int start, int groupInfoId,
            ApiCallBack<List<CircleCommentBean>> apiCallBack) {
        Subscription sub = CircleApi.getInstance().commentList(groupInfoId, start).subscribeOn(
                Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                new ApiObserver<ApiResponseBean<List<CircleCommentBean>>>(apiCallBack));
        addSub(sub);
    }

    @Override
    public void removeComment(String token, int groupInfoId, ApiCallBack<Void> apiCallBack) {
        Subscription sub = AllApi.getInstance().deleteDT(token, groupInfoId).subscribeOn(
                Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                new ApiObserver<ApiResponseBean<Void>>(apiCallBack));
        addSub(sub);
    }

    @Override
    public void removeCommentItem(String token, int commentid, ApiCallBack<Void> apiCallBack) {
        Subscription sub = CircleApi.getInstance().deleteComment(commentid,token).subscribeOn(
                Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                new ApiObserver<ApiResponseBean<Void>>(apiCallBack));
        addSub(sub);
    }


}

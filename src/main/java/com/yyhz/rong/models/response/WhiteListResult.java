package com.yyhz.rong.models.response;

import com.yyhz.rong.models.Result;
import com.yyhz.rong.models.user.UserModel;
import com.yyhz.rong.util.GsonUtil;

/**
 * @author RongCloud
 */
public class WhiteListResult extends Result{

    private UserModel[] members;

    public WhiteListResult(Integer code, String msg, UserModel[] members) {
        super(code, msg);
        this.members = members;
    }

    public WhiteListResult(UserModel[] members) {
        this.members = members;
    }

    public UserModel[] getMembers() {
        return this.members;
    }

    public void setMembers(UserModel[] members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return GsonUtil.toJson(this, WhiteListResult.class);
    }

}

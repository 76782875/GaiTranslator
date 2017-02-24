package com.lyun.lawyer.api.service;

import com.lyun.api.response.APIResult;
import com.lyun.lawyer.api.APIConstants;
import com.lyun.lawyer.api.request.LoginBean;
import com.lyun.lawyer.api.request.ResetPasswordBean;
import com.lyun.lawyer.api.response.LoginResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author 赵尉尉
 * @date 2016/12/20
 */

public interface AuthService {

    @POST(APIConstants.LOGIN)
    Observable<APIResult<LoginResponse>> login(@Body LoginBean body);
    @POST(APIConstants.RESET_PASSWORD)
    Observable<APIResult> resetPassword(@Body ResetPasswordBean body);

}

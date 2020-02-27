package com.eju.cy.uploadcardlibrary.net


import com.eju.cy.uploadcardlibrary.dto.UploadCardDto
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part


interface AppNetInterface {

    /**
     *
     *身份证识别
     *图像数据， base64编码
     *front：身份证含照片的一面；back：身份证带国徽的一面
     * false
     *true
     * true
     */
    @Multipart
    @POST("ai/ocr/file/idcard_ocr/")
    fun uploadCardImg(
        @Part("base64_img") base64_img: RequestBody,
        @Part("id_card_side") id_card_side: RequestBody,
        @Part("detect_direction") detect_direction: RequestBody,
        @Part("detect_risk") detect_risk: RequestBody,
        @Part("detect_photo") detect_photo: RequestBody,
        @Part("detect_rectify") detect_rectify: RequestBody
    ): Observable<UploadCardDto>


//    /**
//     * 客户端户型数据保存API
//     *
//     * @param no      户型编号
//     * @param data    户型数据
//     * @param area    户型面积
//     * @param photo2d 户型平面图
//     * @return
//     */
//    @Multipart
//    @POST("/deco/huxing/save/")
//    fun saveDrawingRoom(
//
//        @Part("no") no: RequestBody,
//        @Part("data") data: RequestBody,
//        @Part("area") area: RequestBody
//
//    ): Observable<SaveRoomDto>
//
//
//    @GET("/deco/huxing/own/")
//    fun getMyRoomList(
//
//        @Query("start_index") start_index: String,
//        @Query("count") count: String
//    ): Observable<MyRoomData>
//
//
//    /**
//     * 客户端提交户属性保存api
//     *
//     * @param no             户型编号
//     * @param name           户型名称
//     * @param layout         房型
//     * @param city_id        所在城市id
//     * @param community_id   小区id
//     * @param community_name 小区名
//     * @return
//     */
//    @Multipart
//    @POST("/deco/huxing/save_property/")
//    fun saveDrawingRoomProperty(
//        @Part("no") no: RequestBody,
//        @Part("name") name: RequestBody
//    ): Observable<SaveRoomDto>
//
//
//    /**
//     * 根据户型编号获取户型详情，包括户型数据
//     *
//     * @param no case 编号
//     * @return
//     */
//
//    @GET("/deco/huxing/detail/")
//    fun getDetail(
//
//                  @Query("no") no: String): Observable<ResultDto<String>>
//
//
//    /**
//     * 获取用户登录token
//     * appid	是	string	APPID	注册app时返回的appid， 可在商家后台查询
//    openid	是	string	外部用户编号	请求方用户唯一标志，最大长度不可超过64位
//    company_id	是	int	企业id	企业id-由简单家系统生成
//    timestamp	是	string	时间戳	格式如：1529917926
//    signature	是	string	加签结果	加签方式详见-签名类
//     */
//
//    @Multipart
//    @POST("/mc/open/user/info/")
//    fun getOpenToken(
//        @Part("appid") appid: RequestBody,
//        @Part("openid") openid: RequestBody,
//        @Part("company_id") company_id: RequestBody,
//        @Part("timestamp") timestamp: RequestBody,
//        @Part("signature") signature: RequestBody
//
//    ): Observable<ResultDto<OpenYunDto>>
//
//
//    /**
//     * 删除户型数据
//     *
//     * @param no 户型编号
//     * @return
//     */
//    @Multipart
//    @POST("/deco/huxing/delete/")
//    fun delectDrawingRoom(@Part("no") no: RequestBody): Observable<DelectDrawRoomDto>
//

}
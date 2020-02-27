package com.eju.cy.uploadcardlibrary.dto;

public class UploadCardDto {


    /**
     * msg : 识别正常
     * data : {"direction":3,"name":"曹孟德","log_id":2074790113581430776,"gender":"男","nation":"汉","birthday":"19000607","image_status":"normal","words_result":{"住址":{"location":{"width":121,"top":549,"height":507,"left":352},"words":"河南省许昌"},"出生":{"location":{"width":45,"top":550,"height":398,"left":525},"words":"19000607"},"姓名":{"location":{"width":57,"top":556,"height":156,"left":710},"words":"曹孟德"},"公民身份号码":{"location":{"width":60,"top":749,"height":680,"left":166},"words":"1101112xsssss22222"},"性别":{"location":{"width":40,"top":552,"height":36,"left":618},"words":"男"},"民族":{"location":{"width":39,"top":805,"height":33,"left":622},"words":"汉"}},"address":"河南省许昌","words_result_num":6,"cardNum":"1101112xsssss22222","idcard_number_type":1}
     * code : 10000
     * encrypt : 0
     */

    private String msg;
    private DataBean data;
    private String code;
    private int encrypt;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(int encrypt) {
        this.encrypt = encrypt;
    }

    public static class DataBean {
        /**
         * direction : 3
         * name : 曹孟德
         * log_id : 2074790113581430776
         * gender : 男
         * nation : 汉
         * birthday : 19000607
         * image_status : normal
         * words_result : {"住址":{"location":{"width":121,"top":549,"height":507,"left":352},"words":"河南省许昌"},"出生":{"location":{"width":45,"top":550,"height":398,"left":525},"words":"19000607"},"姓名":{"location":{"width":57,"top":556,"height":156,"left":710},"words":"曹孟德"},"公民身份号码":{"location":{"width":60,"top":749,"height":680,"left":166},"words":"1101112xsssss22222"},"性别":{"location":{"width":40,"top":552,"height":36,"left":618},"words":"男"},"民族":{"location":{"width":39,"top":805,"height":33,"left":622},"words":"汉"}}
         * address : 河南省许昌
         * words_result_num : 6
         * cardNum : 1101112xsssss22222
         * idcard_number_type : 1
         * issueOffice：
         */

        private int direction;
        private String name; //姓名
        private long log_id;
        private String gender; //性别
        private String nation; //名族
        private String birthday; //出生年月日
        private String image_status;
        private WordsResultBean words_result;
        private String address;  //身份证户口地址
        private int words_result_num;
        private String cardNum; //身份证号码
        private int idcard_number_type;
        private String issueOffice;   //发证公安局
        private String expiryDate;   //身份证到期时间到期时间
        private String IssueDate; //身份证生效开始时间
        private String frontImgString; //身份证人像界面照片
        private String backImgString;//身份国徽像界面照片

        public String getFrontImgString() {
            return frontImgString;
        }

        public void setFrontImgString(String frontImgString) {
            this.frontImgString = frontImgString;
        }

        public String getBackImgString() {
            return backImgString;
        }

        public void setBackImgString(String backImgString) {
            this.backImgString = backImgString;
        }

        public String getExpiryDate() {
            return expiryDate;
        }

        public void setExpiryDate(String expiryDate) {
            this.expiryDate = expiryDate;
        }

        public String getIssueDate() {
            return IssueDate;
        }

        public void setIssueDate(String issueDate) {
            IssueDate = issueDate;
        }

        public String getIssueOffice() {
            return issueOffice;
        }

        public void setIssueOffice(String issueOffice) {
            this.issueOffice = issueOffice;
        }


        public int getDirection() {
            return direction;
        }

        public void setDirection(int direction) {
            this.direction = direction;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getLog_id() {
            return log_id;
        }

        public void setLog_id(long log_id) {
            this.log_id = log_id;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getNation() {
            return nation;
        }

        public void setNation(String nation) {
            this.nation = nation;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getImage_status() {
            return image_status;
        }

        public void setImage_status(String image_status) {
            this.image_status = image_status;
        }

        public WordsResultBean getWords_result() {
            return words_result;
        }

        public void setWords_result(WordsResultBean words_result) {
            this.words_result = words_result;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getWords_result_num() {
            return words_result_num;
        }

        public void setWords_result_num(int words_result_num) {
            this.words_result_num = words_result_num;
        }

        public String getCardNum() {
            return cardNum;
        }

        public void setCardNum(String cardNum) {
            this.cardNum = cardNum;
        }

        public int getIdcard_number_type() {
            return idcard_number_type;
        }

        public void setIdcard_number_type(int idcard_number_type) {
            this.idcard_number_type = idcard_number_type;
        }

        public static class WordsResultBean {
            /**
             * 住址 : {"location":{"width":121,"top":549,"height":507,"left":352},"words":"河南省许昌"}
             * 出生 : {"location":{"width":45,"top":550,"height":398,"left":525},"words":"19000607"}
             * 姓名 : {"location":{"width":57,"top":556,"height":156,"left":710},"words":"曹孟德"}
             * 公民身份号码 : {"location":{"width":60,"top":749,"height":680,"left":166},"words":"1101112xsssss22222"}
             * 性别 : {"location":{"width":40,"top":552,"height":36,"left":618},"words":"男"}
             * 民族 : {"location":{"width":39,"top":805,"height":33,"left":622},"words":"汉"}
             */

        }
    }
}

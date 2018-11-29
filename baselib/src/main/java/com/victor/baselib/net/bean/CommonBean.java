package com.victor.baselib.net.bean;

/**
 * @author lzb
 * @date 2018-4-25
 * 常见问题 bean
 *
 */
public class CommonBean {

    private String msg;
    private DataBean data;
    private int code;

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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static class DataBean {
        private String question;
        private String answer;
        private String Img1;
        private String Img2;

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public String getImg1() {
            return Img1;
        }

        public void setImg1(String img1) {
            Img1 = img1;
        }

        public String getImg2() {
            return Img2;
        }

        public void setImg2(String img2) {
            Img2 = img2;
        }
    }
}

package com.tianluoayi.bean;


import com.tianluoayi.util.Md5;

/**
 * @author neoliu6
 * @create 2018年4月13日 下午2:01:08
 */
public class TlayWxPayReq {
    /**
     * 订单ID
     */
    private int orderId;
    /**
     * 前端传入，标题，暂时无ishi用
     */
    private String title;
    /**
     * 前端传入，用户凭证
     */
    private String token;
    /**
     * 校验和
     */
    private String sum;
    private static String caos = "960f10208cb93779f86eefaa10833e70";

    /**
     *
     */
    public TlayWxPayReq() {
        super();
    }

    /**
     * @param orderId
     * @param title
     * @param token
     */
    public TlayWxPayReq(int orderId, String title, String token) {
        super();
        data(orderId, title, token);
    }

    /**
     * @return the orderId
     */
    public int getOrderId() {
        return orderId;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * @return the sum
     */
    public String getSum() {
        return sum;
    }

    /**
     * @param orderId
     * @param title
     * @param token
     */
    public void data(int orderId, String title, String token) {
        this.orderId = orderId;
        this.title = title;
        this.token = token;
        this.sum = sum();
    }

    /**
     * @return
     */
    public String sum() {
        return Md5.md5(orderId + "|" + caos + "|" + token + "|" + title);
    }

    /**
     * @return
     */
    public boolean checkSum() {
        return sum().equals(sum);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "TlayWxPayReq [orderId=" + orderId + ", title=" + title + ", token=" + token + ", sum=" + sum + "]";
    }

    // public static void main(String[] args) {
    // WxPayReqData data = new WxPayReqData();
    // data.data(1, "sss", "sss");
    // System.out.println(data.getSum());
    // }
}
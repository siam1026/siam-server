
package com.siam.package_weixin_pay.entity;


public class ResultEntity
{
    private boolean success = true;

    private String msg = "执行成功";

    public boolean isSuccess()
    {
        return success;
    }

    public void setSuccess(boolean success)
    {
        this.success = success;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }
    
    public ResultEntity(boolean success)
    {
        this.success = success;
    }
    
    public ResultEntity(boolean success, String msg)
    {
        this.success = success;
        this.msg = msg;
    }

    @Override
    public String toString()
    {
        return "[success]=" + success + ",[msg]=" + msg;
    }
}

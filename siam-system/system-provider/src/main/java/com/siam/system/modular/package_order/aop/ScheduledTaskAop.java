package com.siam.system.modular.package_order.aop;

import com.siam.package_common.annoation.ScheduledTaskAnnotation;
import com.siam.package_common.constant.Quantity;
import com.siam.package_common.constant.ScheduledTaskConst;
import com.siam.package_common.service.AliyunSms;
import com.siam.package_common.util.CommonUtils;
import com.siam.package_weixin_basic.service.WxPublicPlatformNotifyService;
import com.siam.system.modular.package_goods.entity.ScheduledTask;
import com.siam.system.modular.package_goods.entity.ScheduledTaskLog;
import com.siam.system.modular.package_goods.service.ScheduledTaskLogService;
import com.siam.system.modular.package_goods.service.ScheduledTaskService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 定时任务切面
 */
@Slf4j
@Aspect
@Component
public class ScheduledTaskAop {

    @Autowired
    private ScheduledTaskService scheduledTaskService;

    @Autowired
    private ScheduledTaskLogService scheduledTaskLogService;

    @Autowired
    private AliyunSms aliyunSms;

    @Autowired
    private WxPublicPlatformNotifyService wxPublicPlatformNotifyService;

    //@Pointcut("execution(public * com.paintingstone.controller.mod_common.executor.ScheduledTaskExecutor.agentMethod(String)) && args(code)")
    //@Pointcut("execution(public * com.paintingstone.controller.mod_common.executor.ScheduledTaskExecutor.updateByCloseOverdueOrder_task(..))")
    @Pointcut("@annotation(com.siam.package_common.annoation.ScheduledTaskAnnotation)")
    public void scheduledTaskAop(){

    }

    @Around("scheduledTaskAop()")
    public void doAroundMethod(ProceedingJoinPoint proceedingJoinPoint){
        //从@ScheduledTaskAnnotation自定义注解中获取任务代码
        ScheduledTaskAnnotation scheduledTaskAnnotation = ((MethodSignature) proceedingJoinPoint.getSignature()).getMethod().getAnnotation(ScheduledTaskAnnotation.class);
        String code = scheduledTaskAnnotation.code();

        ScheduledTask dbScheduledTask = scheduledTaskService.selectByCode(code);
        if(dbScheduledTask == null){
            log.error("定时任务代码不存在，执行失败");
            return;
        }

        log.info("即将执行以下定时任务：\n任务名称：" + dbScheduledTask.getName() + "\n任务代码：" + dbScheduledTask.getCode());
        /*String hostIpAddress = CommonUtils.getServerIP();
        if(!hostIpAddress.equals("106.12.77.159")){
            log.error("定时任务不允许在此服务器上执行");
            return;
        }*/

        //如果修改操作返回受影响行数不为1，则说明启动失败
        if(scheduledTaskService.updateByStartScheduledTask(code) != Quantity.INT_1){
            log.error("定时任务已被其它服务执行，启动失败");
            return;
        }

        int state = Quantity.INT_1;
        String error = "";

        try{
            //被代理执行的方法
            proceedingJoinPoint.proceed();

            log.info("以下定时任务执行成功：\n任务名称：" + dbScheduledTask.getName() + "\n任务代码：" + dbScheduledTask.getCode());

        } catch (Throwable throwable) {
            //throwable.printStackTrace();
            String description = "以下定时任务执行出错：\n任务名称：" + dbScheduledTask.getName() + "\n任务代码：" + dbScheduledTask.getCode();
            log.error(description, throwable);
            state = Quantity.INT_2;
            error = throwable.getMessage();

            //微信公众号消息通知管理员
            wxPublicPlatformNotifyService.sendFatalErrorMessage(WxPublicPlatformNotifyService.jpOpenId, description, CommonUtils.getFunctionLocation(), "无", new Date(), "请管理员及时处理");

            //将异常抛出去，让事务回滚
            throw new RuntimeException();

        } finally {
            //终止定时任务
            scheduledTaskService.updateByEndScheduledTask(code);

            //closeOverdueOrder定时任务是每分钟执行一次，由于执行得太频繁(1440次/天)，为防止数据库体积过大，所以不添加执行日志
            if(code.equals(ScheduledTaskConst.CLOSE_OVERDUE_ORDER)){
                return;
            }

            //添加定时任务执行日志
            ScheduledTaskLog insertScheduledTaskLog = new ScheduledTaskLog();
            insertScheduledTaskLog.setScheduledTaskCode(code);
            insertScheduledTaskLog.setState(state);
            insertScheduledTaskLog.setError(error);
            insertScheduledTaskLog.setHostName(CommonUtils.getServerHost());
            insertScheduledTaskLog.setHostIpAddress(CommonUtils.getServerIP());
            insertScheduledTaskLog.setCreateTime(new Date());
            scheduledTaskLogService.insertSelective(insertScheduledTaskLog);
        }
    }
}
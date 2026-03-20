package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.service.ReportService;
import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.format.annotation.DateTimeFormat;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/admin/report")
@Api(tags = "数据统计接口")
@Slf4j


public class ReportController {
    @Autowired
    private ReportService reportService;

    /**
     * 统计指定时间区域内的营业额数据
     * @param begin
     * @param end
     * @return
     */
    @GetMapping("/turnoverStatistics")
    public Result<TurnoverReportVO> turnoverStatistics(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin, @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end){
        log.info("查询营业额统计数据，begin：{}，end：{}", begin, end);
        return Result.success(reportService.getTurnoverStatistics(begin,end));
    }

    /**
     * 统计指定时间区域内的用户数据
     * @param begin
     * @param end
     * @return
     */
    @GetMapping("/userStatistics")
    public Result<UserReportVO> userStatistics(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin, @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        log.info("查询用户统计数据，begin：{}，end：{}", begin, end);
        return Result.success(reportService.getUserStatistics(begin, end));
    }

    /**
     * 统计指定时间区域内的订单数据
     * @param begin
     * @param end
     * @return
     */
    @GetMapping("/ordersStatistics")
    public Result<OrderReportVO> oedersStatistics(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin, @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        log.info("查询订单统计数据，begin：{}，end：{}", begin, end);
        return Result.success(reportService.getOrderStatistics(begin, end));
    }


    /**
     * 统计指定时间区域内的销售额前10数据
     * @param begin
     * @param end
     * @return
     */
    @GetMapping("/top10")
    public Result<SalesTop10ReportVO> top10(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin, @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        log.info("查询销售额前10统计数据，begin：{}，end：{}", begin, end);
        return Result.success(reportService.getSalesTop10(begin, end));
    }

    /**
     * 导出数据统计报表
     * @param response
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response) {
        log.info("导出数据统计报表");
        reportService.exportBusinessData(response);
    }

}

package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Api(tags = "和员工有关的接口")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/login")
    @ApiOperation("员工登陆")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success();
    }

    @PostMapping
    @ApiOperation("新增员工")
    public Result InsertEmployee(@RequestBody EmployeeDTO employee) {
        log.info("新增员工信息：{}",employee);
        employeeService.InsertEmployee(employee);
        return Result.success();
    }
    @GetMapping("/page")//此处的返回值是Result的
    @ApiOperation("员工分页查询")
    public  Result<PageResult> SelectPage(EmployeePageQueryDTO employeePageQueryDTO) {
        log.info("员工分页查询的参数是{}",employeePageQueryDTO);
        PageResult q=employeeService.PageSelect(employeePageQueryDTO);
        return Result.success(q);
    }
    @PostMapping("/status/{status}")
    @ApiOperation("设置员工账号的启用和禁止")
    public Result UpdateEmployeeStatus(@PathVariable Integer status,Long id) {
        log.info("设置id：{}员工的账号状态为status:{}",id,status);
        employeeService.updateStatus(status,id);
        return Result.success();
    }
    @GetMapping("/{id}")
    @ApiOperation("根据id查询员工")
    public Result<Employee> GetEmployee(@PathVariable Long id) {
        log.info("查询id：{}的员工账号信息");
        Employee a=employeeService.IDselect(id);
        return Result.success(a);
    }
    @PutMapping
    @ApiOperation("编辑员工信息")//此处有DTO类是用来专门接受前前端传递的employee对象（因为有些属性前端并不关注）
    public Result UpdateEmployee(@RequestBody EmployeeDTO employeeDTO) {
        log.info("修改员工信息");
        employeeService.update(employeeDTO);
        return Result.success();
    }
}

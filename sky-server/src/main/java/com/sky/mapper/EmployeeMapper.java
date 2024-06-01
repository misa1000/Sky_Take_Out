package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     * @param username
     * @return
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    /**
     * 新增员工
     * @param employee
     */
    @Insert("insert into employee values(#{id},#{username},#{name}," +
            "#{password},#{phone},#{sex},#{idNumber},#{status},#{createTime},#{updateTime},"+
            "#{createUser},#{updateUser})")
    void insert(Employee employee);

    /**
     * 员工的分页查询
     * @param employeePageQueryDTO
     * @return
     */
    Page<Employee> PageSelect(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 设置员工账号的启用与禁止
     * @param employee
     */
    void updateStatus(Employee employee);
}

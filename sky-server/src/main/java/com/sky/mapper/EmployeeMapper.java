package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.enumeration.OperationType;
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
    @AutoFill(value = OperationType.INSERT)
    void insert(Employee employee);

    /**
     * 员工的分页查询
     * @param employeePageQueryDTO
     * @return
     */
    Page<Employee> PageSelect(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 设置员工账号的启用与禁止
     * 编辑员工信息
     * @param employee
     */
    @AutoFill(value=OperationType.UPDATE)
    void updateStatus(Employee employee);

    /**
     * 根据id查询员工信息回显
     * @param id
     * @return
     */
    @Select("select * from employee where id=#{id}")
    Employee IDselect(Long id);

}

package com.iyyxx.dao;


import com.iyyxx.domain.Role;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @className: RoleDao
 * @author: eric 4575252@gmail.com
 * @date: 2022/10/19/0019 17:43:05
 **/
public interface RoleDao {

    @Select("select r.* from role r, user_role ur where ur.role_id = r.id and ur.user_id=#{userId}")
    List<Role> findRoleByUserId(Integer userId);
}

package com.graduation_project.testforum.mapper;

import com.graduation_project.testforum.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Insert("insert into user (accountId,name,token,gmtCreate,gmtModified,bio,avatarUrl) values (#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified},#{bio},#{avatarUrl})")
    void insert(User user);

    @Select("select * from user where token = #{token}")
    User findUserToken(@Param("token") String token);

    @Select("select * from user where id = #{creatorId}")
    User queryAvatarUrlById(Integer id);
}

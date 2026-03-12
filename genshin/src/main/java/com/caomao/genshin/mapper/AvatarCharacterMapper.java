package com.caomao.genshin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.caomao.genshin.entity.AvatarCharacter;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色Mapper接口（继承BaseMapper，获得MyBatis-Plus内置的CRUD方法）
 * @Mapper：让Spring识别为MyBatis的Mapper接口
 */
@Mapper
public interface AvatarCharacterMapper extends BaseMapper<AvatarCharacter> {
    // 无需写任何代码，BaseMapper已包含：新增、删除、修改、单查、列表查询等方法
}

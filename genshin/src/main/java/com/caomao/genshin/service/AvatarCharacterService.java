package com.caomao.genshin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.caomao.genshin.entity.AvatarCharacter;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 角色Service接口（继承IService，获得更丰富的CRUD）
 */
public interface AvatarCharacterService extends IService<AvatarCharacter> {
    // 基础CRUD无需写代码，直接继承即可
    List<AvatarCharacter> getFiveStarLiyueCharacters();

    boolean addCharacter(AvatarCharacter character);

    //批量将角色从json插入数据库
    @Transactional(rollbackFor = Exception.class) // 事务：失败则回滚，避免部分插入
    boolean importCharacterJsonToDb(String jsonDirPath);
    // 可自定义业务方法，基础CRUD无需写


    @Transactional(rollbackFor = Exception.class) // 事务：失败则回滚，避免部分插入
    boolean addCharacterBesetID(String jsonDirPath,int id);

    @Transactional(rollbackFor = Exception.class) // 事务：失败则回滚，避免部分插入
    Map<Integer, String> getAllTheCharacterID(String jsonDirPath);


}

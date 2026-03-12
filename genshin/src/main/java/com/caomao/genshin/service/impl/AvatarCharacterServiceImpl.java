package com.caomao.genshin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.caomao.genshin.entity.AvatarCharacter;
import com.caomao.genshin.mapper.AvatarCharacterMapper;
import com.caomao.genshin.service.AvatarCharacterService;
import com.caomao.genshin.utils.JsonFileReaderUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Service实现类（继承ServiceImpl，绑定Mapper和实体类）
 */
@Service
public class AvatarCharacterServiceImpl extends ServiceImpl<AvatarCharacterMapper, AvatarCharacter> implements AvatarCharacterService {
   // 基础CRUD无需写代码，直接继承即可
    @Override
    public List<AvatarCharacter> getFiveStarLiyueCharacters() {
        LambdaQueryWrapper<AvatarCharacter> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AvatarCharacter::getRarity, 5)
                .eq(AvatarCharacter::getRegion, "璃月");
        return this.list(wrapper);
    }

    @Override
    public boolean addCharacter(AvatarCharacter character) {
        LambdaQueryWrapper<AvatarCharacter> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AvatarCharacter::getName, character.getName());
        AvatarCharacter existCharacter = this.getOne(wrapper);
        if (existCharacter != null) {
            return false;
        }
        return this.save(character);
    }

    /**
     * 导入JSON文件到数据库（批量插入，开启事务保证原子性）
     * @param jsonDirPath JSON文件目录
     * @return 导入是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean importCharacterJsonToDb(String jsonDirPath) {
        // 1. 读取JSON文件，解析为实体列表
        List<AvatarCharacter> characterList = JsonFileReaderUtil.readCharacterJsonFiles(jsonDirPath);
        if (characterList.isEmpty()) {
            System.out.println("无可用的角色数据，导入终止");
            return false;
        }

        // 2. 批量插入数据库（MyBatis-Plus批量插入，性能优于单条save）
        // 先删除已存在的同名角色（避免主键/名称重复）
        for (AvatarCharacter character : characterList) {
            LambdaQueryWrapper<AvatarCharacter> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(AvatarCharacter::getId, character.getId());
            this.remove(wrapper); // 删除旧数据，再插入新数据
        }

        // 批量保存（批量插入，默认每次1000条，可自定义）
        boolean success = this.saveBatch(characterList);
        System.out.println("导入完成：共解析" + characterList.size() + "个角色，入库" + (success ? "成功" : "失败"));
        return success;
    }


    /**
     * 导入JSON文件到数据库（根据ID插入角色）
     * @param jsonDirPath JSON文件目录
     * @return 导入是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean addCharacterBesetID(String jsonDirPath,int id) {
        boolean success = false;
        // 1. 读取JSON文件，解析为实体列表
        List<AvatarCharacter> characterList = JsonFileReaderUtil.readCharacterJsonFiles(jsonDirPath);
        if (characterList.isEmpty()) {
            System.out.println("无可用的角色数据，导入终止");
            return false;
        }

        // 2. 批量插入数据库（MyBatis-Plus批量插入，性能优于单条save）
        // 先删除已存在的同名角色（避免主键/名称重复）
        for (AvatarCharacter character : characterList) {
            if (character.getId() == id) {
                //先检查旧数据,如果有删除旧数据
                LambdaQueryWrapper<AvatarCharacter> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(AvatarCharacter::getId, character.getId());
                if (this.getOne(wrapper) != null) {
                    System.out.println("已存在角色名为" + character.getName() + "的角色，并删除旧角色");
                    this.remove(wrapper); // 删除旧数据，再插入新数据
                }
                success = this.save(character);
                System.out.println("已导入角色名为" + character.getName() + "的角色");
                break;
            }

        }
        return success;
    }




    /**
     * 查询所有角色的 ID 和名称
     * @param jsonDirPath JSON文件目录
     * @return 角色 ID 和名称的映射关系 (Map<ID, Name>)
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<Integer, String> getAllTheCharacterID(String jsonDirPath) {
        Map<Integer, String> characterIdNameMap = new TreeMap<>();

        // 1. 读取 JSON文件，解析为实体列表
        List<AvatarCharacter> characterList = JsonFileReaderUtil.readCharacterJsonFiles(jsonDirPath);
        if (characterList.isEmpty()) {
            System.out.println("无可用的角色数据，查询失败");
            return characterIdNameMap;
        }

        // 2. 遍历角色列表，收集 ID 和名称
        for (AvatarCharacter character : characterList) {
            characterIdNameMap.put(character.getId(), character.getName());
        }

        System.out.println("共查询到 " + characterIdNameMap.size() + " 个角色 ID 和名称");
        return characterIdNameMap;
    }

}


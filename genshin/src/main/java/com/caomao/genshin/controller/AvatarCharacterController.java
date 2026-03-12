package com.caomao.genshin.controller;

import com.caomao.genshin.entity.AvatarCharacter;
import com.caomao.genshin.service.AvatarCharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

// @RestController：返回JSON数据（适合接口开发）
@RestController
// 接口前缀：所有该类的接口都以 /character 开头
@RequestMapping("/character")
public class AvatarCharacterController {

    // 注入Service（Spring自动创建实例，直接用）--------可用
    @Autowired
    private AvatarCharacterService avatarCharacterService;

    // 接口1：查询所有角色ID → 访问地址：GET http://localhost:8080/character/getAllTheCharacterID
    @GetMapping("/getAllTheCharacterID")
    public Map<Integer, String> getAllTheCharacterID() {
        String jsonDirPath = "classpath:jsonList/characters/";
        Map<Integer, String> IdList = avatarCharacterService.getAllTheCharacterID(jsonDirPath);
        return IdList;
    }

    // 接口2：根据ID新增角色 → 访问地址：POST http://localhost:8080/character/addCharacterBesetID--------可用
    @PostMapping("/add/addCharacterBesetID")
    public String addCharacterBesetID (@RequestBody Integer id) {

        String jsonDirPath = "classpath:jsonList/characters/";
        boolean success = avatarCharacterService.addCharacterBesetID(jsonDirPath, id);
        return success ? "ID为" + id + "的角色成功增加" : "不存在ID为" + id + "的角色";
    }

    // 接口3：根据ID查角色 → 访问地址：GET http://localhost:8080/character/1
    @GetMapping("/{id}")
    public AvatarCharacter getById(@PathVariable Integer id) {
        return null;
    }


    // 新增：批量写入数据库角色信息--------可用
    @GetMapping("/import/json")
    public String importJsonToDb() {
        // JSON文件目录（根据你的实际路径调整！）
        // 注意：jsonList在java目录下，需调整为resources目录，或修改路径读取方式
        // 推荐：将jsonList文件夹移动到 src/main/resources/ 下，路径改为 "classpath:jsonList/characters/"
        String jsonDirPath = "classpath:jsonList/characters/";
        boolean success = avatarCharacterService.importCharacterJsonToDb(jsonDirPath);
        return success ? "JSON导入数据库成功" : "JSON导入数据库失败";
    }

}

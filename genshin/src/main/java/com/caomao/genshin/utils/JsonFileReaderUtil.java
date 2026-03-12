package com.caomao.genshin.utils;

import com.caomao.genshin.entity.AvatarCharacter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * JSON文件读取工具类：读取characters目录下的JSON文件，解析为AvatarCharacter实体
 * 优化：兼容JSON字段缺失的场景，避免空指针异常
 */
public class JsonFileReaderUtil {

    // Jackson JSON解析器（Spring Boot已内置，无需额外依赖）
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * 读取指定目录下所有角色JSON文件，解析为实体列表
     * @param jsonDirPath JSON文件目录（如：classpath:jsonList/characters/）
     * @return 角色实体列表
     */
    public static List<AvatarCharacter> readCharacterJsonFiles(String jsonDirPath) {
        List<AvatarCharacter> characterList = new ArrayList<>();

        try {
            // 1. 获取JSON文件目录的绝对路径（适配项目内路径）
            File dir = ResourceUtils.getFile(jsonDirPath);
            if (!dir.exists() || !dir.isDirectory()) {
                throw new FileNotFoundException("JSON目录不存在：" + jsonDirPath);
            }

            // 2. 遍历目录下所有.json文件（过滤非JSON文件）
            File[] jsonFiles = dir.listFiles(file -> file.isFile() && file.getName().endsWith(".json"));
            if (jsonFiles == null || jsonFiles.length == 0) {
                System.out.println("JSON目录下无有效文件：" + jsonDirPath);
                return characterList;
            }

            // 3. 逐个解析JSON文件为实体
            for (File jsonFile : jsonFiles) {
                // 读取文件内容（UTF-8编码，避免中文乱码）
                String jsonContent = Files.readString(jsonFile.toPath(), StandardCharsets.UTF_8);
                // 解析JSON节点（灵活获取字段，适配JSON结构）
                JsonNode rootNode = OBJECT_MAPPER.readTree(jsonContent);

                // 构建AvatarCharacter实体（映射JSON字段到实体，兼容字段缺失）
                AvatarCharacter character = new AvatarCharacter();

                // 基础字段：先判断节点是否存在，再取值（空值兜底）
                character.setId(getIntValue(rootNode, "id", 0));
                character.setName(getTextValue(rootNode, "name", ""));
                character.setTitle(getTextValue(rootNode, "title", ""));
                character.setDescription(getTextValue(rootNode, "description", ""));
                character.setWeaponType(getTextValue(rootNode, "weaponType", ""));
                character.setWeaponText(getTextValue(rootNode, "weaponText", ""));
                character.setBodyType(getTextValue(rootNode, "bodyType", ""));
                character.setGender(getTextValue(rootNode, "gender", ""));
                character.setQualityType(getTextValue(rootNode, "qualityType", ""));
                character.setRarity(getIntValue(rootNode, "rarity", 0));
                character.setBirthdayMmdd(getTextValue(rootNode, "birthdaymmdd", ""));
                character.setBirthday(getTextValue(rootNode, "birthday", ""));
                character.setElementType(getTextValue(rootNode, "elementType", ""));
                character.setElementText(getTextValue(rootNode, "elementText", ""));
                character.setAffiliation(getTextValue(rootNode, "affiliation", ""));
                character.setAssociationType(getTextValue(rootNode, "associationType", ""));
                character.setRegion(getTextValue(rootNode, "region", ""));
                character.setSubstatType(getTextValue(rootNode, "substatType", ""));
                character.setSubstatText(getTextValue(rootNode, "substatText", ""));
                character.setConstellation(getTextValue(rootNode, "constellation", ""));

                // 解析CV子节点（嵌套JSON，先判断cv节点是否存在）
                JsonNode cvNode = rootNode.get("cv");
                if (cvNode != null) {
                    character.setCvEnglish(getTextValue(cvNode, "english", ""));
                    character.setCvChinese(getTextValue(cvNode, "chinese", ""));
                    character.setCvJapanese(getTextValue(cvNode, "japanese", ""));
                    character.setCvKorean(getTextValue(cvNode, "korean", ""));
                } else {
                    // 若cv节点不存在，手动设置CV字段为空字符串（避免实体字段为null）
                    character.setCvEnglish("");
                    character.setCvChinese("");
                    character.setCvJapanese("");
                    character.setCvKorean("");
                }

                // 添加到列表
                characterList.add(character);
                System.out.println("解析完成：" + jsonFile.getName() + " → 角色：" + character.getName());
            }

        } catch (Exception e) {
            System.err.println("读取/解析JSON文件失败：" + e.getMessage());
            e.printStackTrace();
        }

        return characterList;
    }

    /**
     * 工具方法：安全获取JSON节点的文本值（字段缺失时返回默认值）
     * @param node JSON节点
     * @param fieldName 字段名
     * @param defaultValue 默认值
     * @return 字段值（或默认值）
     */
    private static String getTextValue(JsonNode node, String fieldName, String defaultValue) {
        JsonNode fieldNode = node.get(fieldName);
        return (fieldNode != null && !fieldNode.isNull()) ? fieldNode.asText() : defaultValue;
    }

    /**
     * 工具方法：安全获取JSON节点的整数值（字段缺失时返回默认值）
     * @param node JSON节点
     * @param fieldName 字段名
     * @param defaultValue 默认值
     * @return 字段值（或默认值）
     */
    private static int getIntValue(JsonNode node, String fieldName, int defaultValue) {
        JsonNode fieldNode = node.get(fieldName);
        return (fieldNode != null && !fieldNode.isNull() && fieldNode.isInt()) ? fieldNode.asInt() : defaultValue;
    }
}

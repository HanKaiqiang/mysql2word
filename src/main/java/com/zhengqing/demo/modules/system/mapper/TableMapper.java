package com.zhengqing.demo.modules.system.mapper;


import com.zhengqing.demo.modules.system.Fields;
import com.zhengqing.demo.modules.system.Models;
import com.zhengqing.demo.modules.system.entity.TableFileds;
import com.zhengqing.demo.modules.system.entity.Tables;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p> 查询表数据信息 </p>
 *
 * @author : zhengqing
 * @description :
 * @date : 2019/11/8 15:46
 */
@Mapper
public interface TableMapper {

    /**
     * 获取指定数据库下所有表名和注释
     *
     * @param dbName:数据库名
     * @return: java.util.List<com.zhengqing.demo.modules.system.entity.Tables>
     */
    @Select("select table_name as name,table_comment as comment from information_schema.tables where table_schema =#{dbName} order by table_name")
    List<Tables> getAllTables(@Param("dbName") String dbName);

    /**
     * 获取指定表信息
     *
     * @param tableName:表
     * @return: java.util.List<com.zhengqing.demo.modules.system.entity.TableFileds>
     */
    @Select("SHOW FULL FIELDS FROM ${tableName}")
    List<TableFileds> getTable(@Param("tableName") String tableName);

    @Select("SELECT table_name FROM information_schema.tables WHERE `table_name` LIKE 'ci_124_%' AND table_schema = 'tzxt'")
    List<String> getAllTableNames();


    @Select("SELECT `key`, `name` FROM `custom_models` WHERE tenant_id = 124 ")
    List<Models> getAllModels();

    @Select("SELECT `key`, `name` FROM `extension_fields` WHERE tenant_id = 124")
    List<Fields> getAllFields();

}

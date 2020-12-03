package com.zhengqing.demo.modules.system.service.impl;

import com.zhengqing.demo.config.Constants;
import com.zhengqing.demo.modules.system.Fields;
import com.zhengqing.demo.modules.system.Models;
import com.zhengqing.demo.modules.system.entity.Tables;
import com.zhengqing.demo.modules.system.mapper.TableMapper;
import com.zhengqing.demo.modules.system.service.ITableService;
import com.zhengqing.demo.utils.DateTimeUtils;
import com.zhengqing.demo.utils.TableToWordUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p> 实现类 </p>
 *
 * @author : zhengqing
 * @description :
 * @date : 2019/11/8 16:22
 */
@Slf4j
@Service
public class TableService implements ITableService {

    @Autowired
    private TableMapper tableMapper;
    @Autowired
    private TableToWordUtil tableToWordUtil;

    @Override
    public String getTableInfo() {
        // 1、获取数据库所有表信息
        List<String> tableNames = tableMapper.getAllTableNames();

        List<String> history = tableNames.stream().filter(name -> name.contains("_history")).collect(Collectors.toList());

        List<Fields> allFields = tableMapper.getAllFields();

        List<Models> allModels = tableMapper.getAllModels();

        List<Tables> tables  = new ArrayList<>();

        for (String tableName : tableNames) {
            Tables table = new Tables();
            table.setName(tableName);
            for (Models model : allModels) {
                String key = model.getKey();
                if (tableName.endsWith(key)) {
                    table.setComment(model.getName());
                    tables.add(table);
                    break;
                }
            }
        }

        // List<Tables> tables = tableMapper.getAllTables(Constants.DATABASE);

        // 2、生成文件名信息 - 年月日时分秒
        String date = null;
        try {
            date = DateTimeUtils.dateFormat(new Date(), DateTimeUtils.PARSE_PATTERNS[12]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String docFileName = Constants.FILE_PATH + "\\" + Constants.FILE_NAME + "-" + date + ".doc";

        // 3、调用工具类生成文件
        tableToWordUtil.toWord(tables, docFileName, Constants.FILE_NAME, allFields);

        // 4、返回文件地址
        String filePath = docFileName.replaceAll("\\\\", "/");
        return filePath;
    }
}

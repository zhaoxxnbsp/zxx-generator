package com.github.zhaoxxnbsp;

import com.github.zhaoxxnbsp.util.GenUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

public class GeneratorService {

    private GeneratorProperties generatorProperties;

    private JdbcTemplate jdbcTemplate;

    public GeneratorService(GeneratorProperties generatorProperties, JdbcTemplate jdbcTemplate) {
        this.generatorProperties = generatorProperties;
        this.jdbcTemplate = jdbcTemplate;
    }

    public byte[] code() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        for (String tableName : generatorProperties.getTableNames()) {
            //查询表信息
            Map<String, Object> table = this.queryTable(tableName);
            //查询列信息
            List<Map<String, Object>> columns = this.queryColumns(tableName);
            //生成代码
            GenUtils.generatorCode(table, columns, zip);
        }

        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }

    private Map<String, Object> queryTable(String tableName) {

        String sql = String.format("select table_name tableName, engine, table_comment tableComment, create_time createTime " +
                " from information_schema.tables " +
                " where table_schema = (select database()) and table_name = '%s'", tableName);
        System.out.println("queryTable sql = " + sql);
        Map<String, Object> result = jdbcTemplate.queryForMap(sql);
        System.out.println("queryTable result = " + result);
        return result;
    }

    private List<Map<String, Object>> queryColumns(String tableName) {
        String sql = String.format("select column_name columnName, data_type dataType, column_comment columnComment, column_key columnKey, extra " +
                " from information_schema.columns " +
                " where table_name = '%s' and table_schema = (select database()) order by ordinal_position", tableName);
        System.out.println("queryColumns sql = " + sql);
        List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
        System.out.println("queryColumns result = " + result);
        return result;
    }

}

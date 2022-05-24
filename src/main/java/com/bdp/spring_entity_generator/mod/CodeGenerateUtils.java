package com.bdp.spring_entity_generator.mod;

import freemarker.template.Template;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class CodeGenerateUtils {
    //类上的注释（作者 日期）
    private final String AUTHOR = "WB";
    private static String CURRENT_DATE="";
    private final String tableName = "PURCHASE_PLAN";
    //包名
    private final String packageName = "org.spring.bdp.sdp.model.warehouse.mapper";
    //数据库信息
    private final String URL = "jdbc:oracle:thin:@bjjgq.ticp.net:1521:orcl";
    private final String USER = "ztwzjt";
    private final String PASSWORD = "ztwzjt";
    private final String DRIVER = "oracle.jdbc.OracleDriver";
    //生成的位置
    private final static String diskPath = "E:\\gitwork\\srping-bdp\\whxxykj-bdp\\spring-bdp\\src\\main\\java\\org\\spring\\bdp\\sdp\\model\\warehouse";
    //文件生成目录
    private final static String dirs="mapper";
    public static void main(String[] args) throws Exception{
        CodeGenerateUtils codeGenerateUtils = new CodeGenerateUtils();
        setDF();
        codeGenerateUtils.generate();
    }

    /**
     * 设置日期
     * @throws Exception
     */
    public static void setDF() throws Exception{
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        CURRENT_DATE=sdf.format(date);
    }
    /**
     * 获取数据库连接
     * @return
     * @throws Exception
     */
    public Connection getConnection() throws Exception{
        Class.forName(DRIVER);
        Connection connection= DriverManager.getConnection(URL, USER, PASSWORD);
        return connection;
    }

    /**
     * 生成代码
     * @throws Exception
     */
    public void generate() throws Exception{
        try {
            Map<String,String> tables = new HashMap<String,String>();//存储表名
            //获取数据库元信息
            Connection connection = getConnection();
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            //存储表名
            String[] types = {"TABLE","VIEW"};
            
            ResultSet tabs    = databaseMetaData.getTables(null, null, tableName, types);
            while(tabs.next()){
                //只要表名这一列
            	tables.put(tabs.getString("TABLE_NAME"),tabs.getString("TABLE_NAME"));
            }
            for (String key : tables.keySet()) {
            	String table = key;
                ResultSet resultSet = databaseMetaData.getColumns(null,"%",table,"%");
                //生成Model文件
                generateModelFile(resultSet,table);
                //生成Mapper文件
//                generateMapperFile(resultSet,table);
			}
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally{

        }
    }

    /**
     * 生成实体类
     * @param resultSet
     * @throws Exception
     */
     private void generateModelFile(ResultSet resultSet,String tableName) throws Exception{
        final String suffix = ".java";//后缀名
        String name = replaceUnderLineAndUpperCase(tableName,"TABLE");
        final String path = diskPath  +"\\"+dirs+"\\"+ name+ suffix;
        final String templateName = "Model.ftl";
        File mapperFile = new File(path);
        List<Column> columnClassList = new ArrayList<Column>();
        Column columnClass = null;
        while(resultSet.next()){
            //id字段略过
            //if(resultSet.getString("COLUMN_NAME").equals("id")) continue;
            columnClass = new Column();
            //获取字段名称
            columnClass.setColumnName(resultSet.getString("COLUMN_NAME"));
            //获取字段类型
            columnClass.setColumnType(resultSet.getString("TYPE_NAME"));
            //获取字段长度
            columnClass.setColumnSize(resultSet.getInt("COLUMN_SIZE"));
            //转换字段名称，如 sys_name 变成 SysName
            columnClass.setChangeColumnName(replaceUnderLineAndUpperCase(resultSet.getString("COLUMN_NAME"), "COLUMN"));
            //字段在数据库的注释
            columnClass.setColumnComment(resultSet.getString("REMARKS"));
            //字段在数据库名称首字母大写
            columnClass.setColumnCommentP(String.valueOf(columnClass.getChangeColumnName().charAt(0)).toUpperCase()+columnClass.getChangeColumnName().substring(1,columnClass.getChangeColumnName().length()));
            String a = columnClass.getColumnName();
            List<Column> filter = columnClassList.stream().filter(s -> a.equals(s.getColumnName())).collect(Collectors.toList());
            if(null==filter||filter.isEmpty()) {
            	columnClassList.add(columnClass);
            }
        }
        Map<String,Object> dataMap = new HashMap<String,Object>();
        dataMap.put("model_column",columnClassList);
        dataMap.put("table_name",name);
        dataMap.put("table_old_name",tableName);
        dataMap.put("UID",String.valueOf(new Date().getTime()));
        generateFileByTemplate(templateName,mapperFile,dataMap);
        System.out.println(name);
    }

    /**
     * 生成mapper文件
     * @param resultSet
     * @param tableName
     * @throws Exception
     */
    private void generateMapperFile(ResultSet resultSet,String tableName) throws Exception{
        final String suffix = "Mapper.xml";
        final String package_name = "mappers";//包名
        String name = replaceUnderLineAndUpperCase(tableName, "TABLE");
        final String path = diskPath  +"\\"+package_name+"\\"+ name+ suffix;
        final String templateName = "Mapper.ftl";
        File mapperFile = new File(path);

        List<Column> columnClassList = new ArrayList<Column>();
        Column columnClass = null;
        while(resultSet.next()){
            //id字段略过
            //if(resultSet.getString("COLUMN_NAME").equals("id")) continue;
            columnClass = new Column();
            //获取字段名称
            columnClass.setColumnName(resultSet.getString("COLUMN_NAME"));
            //获取字段类型
            columnClass.setColumnType(resultSet.getString("TYPE_NAME"));
            columnClassList.add(columnClass);
        }
        Map<String,Object> dataMap = new HashMap<String,Object>();
        System.out.println(name);
        dataMap.put("model_column",columnClassList);
        dataMap.put("table_name",name);
        dataMap.put("jing","#");
        dataMap.put("left","{");
        dataMap.put("right","}");
        generateFileByTemplate(templateName,mapperFile,dataMap);
    }

    private void generateFileByTemplate(final String templateName,File file,Map<String,Object> dataMap) throws Exception{
        Template template = FreeMarkerTemplateUtils.getTemplate(templateName);
        FileOutputStream fos = new FileOutputStream(file);
        dataMap.put("table_name_small",tableName);
        dataMap.put("author",AUTHOR);
        dataMap.put("date",CURRENT_DATE);
        dataMap.put("package_name",packageName);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"),10240);
        template.process(dataMap,out);
    }

    /**
     * 带下划线的名字转换
     * @param str
     * @return
     */
    public static String replaceUnderLineAndUpperCase(String str, String type){
    	String tableName = str;
        if(str.contains("_")){
        	tableName = tableName.toLowerCase();
        	int num = tableName.indexOf("_");
        	String p = String.valueOf(tableName.charAt(num+1));
        	tableName = tableName.replace("_"+p, p.toUpperCase());
        	if(type.equals("TABLE")) {
        		tableName = "Abstract"+String.valueOf(tableName.charAt(0)).toUpperCase()+tableName.substring(1, tableName.length());
        	}
            return replaceUnderLineAndUpperCase(tableName, type);
        }else {
            return tableName;
        }
    }
}

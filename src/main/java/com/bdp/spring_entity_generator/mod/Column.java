package com.bdp.spring_entity_generator.mod;
public class Column {

    /** 数据库字段名称 **/
    private String columnName;
    /** 数据库字段类型 **/
    private String columnType;
    /** 数据库字段首字母小写且去掉下划线字符串 **/
    private String changeColumnName;
    /** 数据库字段注释 **/
    private String columnComment;
    /** 数据库字段长度*/
    private int columnSize;
    /** 数据库字段名称首字母大写 **/
    private String columnCommentP;

    public String getColumnComment() {
        return columnComment;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getChangeColumnName() {
        return changeColumnName;
    }

    public void setChangeColumnName(String changeColumnName) {
        this.changeColumnName = changeColumnName;
    }

	public int getColumnSize() {
		return columnSize;
	}

	public void setColumnSize(int columnSize) {
		this.columnSize = columnSize;
	}

	public String getColumnCommentP() {
		return columnCommentP;
	}

	public void setColumnCommentP(String columnCommentP) {
		this.columnCommentP = columnCommentP;
	}
}


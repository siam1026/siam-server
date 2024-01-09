package com.louis.kitty.generator.utils;

/**
 * 数据类型工具类
 * @author Louis
 * @date Nov 10, 2018
 */
public class DataTypeUtils {

	public static final String String = "String";
	public static final String Byte = "Byte";
	public static final String ByteArray = "byte[]";
	public static final String Short = "Short";
	public static final String Integer = "Integer";
	public static final String Long = "Long";
	public static final String Float = "Float";
	public static final String Double = "Double";
	public static final String Character = "Character";
	public static final String Boolean = "Boolean";
	public static final String Date = "java.util.Date";
	public static final String Time = "java.sql.Time";
	public static final String Timestamp = "java.sql.Timestamp";
	public static final String Object = "java.lang.Object";

	public static final String CHAR = "CHAR";
	public static final String TEXT = "TEXT";
	public static final String BLOB = "BLOB";
	public static final String CLOB = "CLOB";
	public static final String BIT = "BIT";
	public static final String TINYINT = "TINYINT";
	public static final String SMALLINT = "SMALLINT";
	public static final String INT = "INT";
	public static final String INT8 = "INT8";
	public static final String BIGINT = "BIGINT";
	public static final String LONG = "LONG";
	public static final String FLOAT = "FLOAT";
	public static final String NUMBER = "NUMBER";
	public static final String NUMERIC = "NUMERIC";
	public static final String DECIMAL = "DECIMAL";
	public static final String DOUBLE = "DOUBLE";
	public static final String BOOL = "BOOL";
	public static final String BOOLEAN = "BOOLEAN";
	public static final String DATE = "DATE";
	public static final String TIME = "TIME";
	public static final String BINARY = "BINARY";
	public static final String VARBINARY = "VARBINARY";
	public static final String CHAR_FOR_BIT_DATA = "CHAR FOR BIT DATA";
	public static final String RAW = "RAW";
	public static final String IMAGE = "IMAGE";
	public static final String BYTE = "BYTE";
	public static final String DK_CM_BLOB = "DK_CM_BLOB";
	public static final String DK_CM_SMALLINT = "DK_CM_SMALLINT";
	public static final String OBJECT = "OBJECT";
	public static final String LONGVARCHAR = "LONGVARCHAR";
	public static final String INTEGER = "INTEGER";
	public static final String TIMESTAMP = "TIMESTAMP";
	public static final String VARCHAR = "VARCHAR";
	  
	/**
	 * 根据数据库类型获取对应的JAVA类型
	 * @param dataType
	 * @return
	 */
	public static String getJavaType(String dataType) {
		String javaType = String;
		if (dataType != null) {
			dataType = dataType.toUpperCase();
			if (dataType.contains(BINARY) || dataType.contains(CHAR_FOR_BIT_DATA) || dataType.contains(RAW)
					|| dataType.contains(IMAGE) || dataType.contains(BYTE) || dataType.contains(DK_CM_BLOB)
					|| dataType.contains(BLOB)) {
				javaType = ByteArray;
			} else if (dataType.contains(BOOLEAN) || dataType.contains(BIT) || dataType.contains(DK_CM_SMALLINT)) {
				javaType = Boolean;
			} else if (dataType.contains(CHAR) || dataType.contains(TEXT) || dataType.contains(CLOB)) {
				javaType = String;
			} else if (dataType.contains(LONG) || dataType.contains(BIGINT) || dataType.contains(INT8)) {
				javaType = Long;
			} else if (dataType.contains(INT)) {
				javaType = Integer;
			}  else if (dataType.contains(FLOAT)) {
				javaType = Float;
			} else if (dataType.contains(NUMBER) || dataType.contains(DECIMAL) || dataType.contains(DOUBLE)) {
				javaType = Double;
			} else if (dataType.contains(DATE) || dataType.contains(TIME)) {
				javaType = Date;
			} else if (dataType.contains(OBJECT)) {
				javaType = Object;
			}
		}
		return javaType;
	}
	
	/**
	 * 根据数据库类型获取对应JdbcType
	 * @param dataType
	 * @return
	 */
	public static String getJdbcType(String dataType) {
		String jdbcType = dataType.toUpperCase();
		if (dataType.contains(BINARY) || dataType.contains(CHAR_FOR_BIT_DATA) || dataType.contains(RAW)
				|| dataType.contains(IMAGE) || dataType.contains(BYTE) || dataType.contains(DK_CM_BLOB)) {
			jdbcType = VARBINARY;
		} else if (dataType.contains(BLOB)) {
			jdbcType = BLOB;
		} else if (dataType.contains(CLOB)) {
			jdbcType = CLOB;
		} else if (dataType.contains(BOOLEAN)) {
			jdbcType = BOOLEAN;
		} else if (dataType.contains(BIT) || dataType.contains(DK_CM_SMALLINT)) {
			jdbcType = BIT;
		} else if (jdbcType.equalsIgnoreCase(TINYINT)) {
			jdbcType = TINYINT;
		} else if (jdbcType.equalsIgnoreCase(SMALLINT)) {
			jdbcType = SMALLINT;
		} else if (jdbcType.equalsIgnoreCase(INT) || jdbcType.contains(INTEGER)) {
			jdbcType = INTEGER;
		} else if (jdbcType.equalsIgnoreCase(DATE)) {
			jdbcType = DATE;
		} else if (jdbcType.equalsIgnoreCase(TIME)) {
			jdbcType = TIME;
		} else if(jdbcType.contains(DATE) || jdbcType.contains(TIME)) {
			jdbcType = TIMESTAMP;
		} else if(jdbcType.contains(FLOAT)) {
			jdbcType = FLOAT;
		} else if(jdbcType.contains(DOUBLE)) {
			jdbcType = DOUBLE;
		} else if(jdbcType.contains(DECIMAL)) {
			jdbcType = DECIMAL;
		} else if(jdbcType.contains(NUMBER) || jdbcType.contains(NUMERIC)) {
			jdbcType = NUMERIC;
		} else if(jdbcType.contains(TEXT) || jdbcType.contains(LONGVARCHAR)) {
			jdbcType = LONGVARCHAR;
		} else if(jdbcType.contains(VARCHAR)) {
			jdbcType = VARCHAR;
		}
		return jdbcType;
	}

}

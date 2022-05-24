package ${package_name};

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

/**
* @author ${author}
* @date ${date}
*/
@MappedSuperclass
public class ${table_name} implements Serializable {

	/** */
	private static final long serialVersionUID = ${UID}L;

<#if model_column ? exists>
    <#list model_column as model>
    <#if model.columnType = 'VARCHAR2' >
    @Column(name = "${model.columnName}")
    private String ${model.changeColumnName? uncap_first};
    
    </#if>
    <#if model.columnType = 'NUMBER' >
	<#if model.columnSize = 11 >
    @Column(name = "${model.columnName}")
    private Long ${model.changeColumnName?uncap_first};
    
    </#if>
    <#if model.columnSize = 18 >
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "${table_old_name}_SEQ")
    @SequenceGenerator(sequenceName = "${table_old_name}_SEQ", allocationSize = 1, name = "${table_old_name}_SEQ")
    private Long ${model.changeColumnName?uncap_first};
    
    </#if>
    <#if model.columnSize = 25 >
    @Column(name = "${model.columnName}")
    private Double ${model.changeColumnName?uncap_first};
    
    </#if>
    </#if>
    <#if model.columnType = 'INT' >
    @Column(name = "${model.columnName}")
    private Integer ${model.changeColumnName?uncap_first};
    
    </#if>
    <#if model.columnType = 'DATE' >
    @Column(name = "${model.columnName}")
    private Date ${model.changeColumnName?uncap_first};
    
    </#if>
    </#list>
</#if>

	/** default constructor */
	public ${table_name}() {
	}

<#if model_column?exists>
    <#list model_column as model>
    <#if model.columnType = 'VARCHAR2' >
    public String get${model.columnCommentP}() {
        return this.${model.changeColumnName?uncap_first};
    }
    
    public void set${model.columnCommentP}(String ${model.changeColumnName? uncap_first}) {
        this.${model.changeColumnName?uncap_first} = ${model.changeColumnName?uncap_first};
    }
    
    </#if>
    <#if model.columnType = 'NUMBER' >
	<#if model.columnSize = 11 >
    public Long get${model.columnCommentP}() {
        return this.${model.changeColumnName?uncap_first};
    }
    
    public void set${model.columnCommentP}(Long ${model.changeColumnName? uncap_first}) {
        this.${model.changeColumnName?uncap_first} = ${model.changeColumnName?uncap_first};
    }
    
    </#if>
    <#if model.columnSize = 18 >
    public Long get${model.columnCommentP}() {
        return this.${model.changeColumnName?uncap_first};
    }
    
    public void set${model.columnCommentP}(Long ${model.changeColumnName? uncap_first}) {
        this.${model.changeColumnName?uncap_first} = ${model.changeColumnName?uncap_first};
    }
    
    </#if>
    <#if model.columnSize = 25 >
    public Double get${model.columnCommentP}() {
        return this.${model.changeColumnName?uncap_first};
    }
    
    public void set${model.columnCommentP}(Double ${model.changeColumnName? uncap_first}) {
        this.${model.changeColumnName?uncap_first} = ${model.changeColumnName?uncap_first};
    }
    
    </#if>
    </#if>
    <#if model.columnType = 'INT' >
    public int get${model.columnCommentP}() {
        return this.${model.changeColumnName?uncap_first};
    }
    
    public void set${model.columnCommentP}(int ${model.changeColumnName? uncap_first}) {
        this.${model.changeColumnName?uncap_first} = ${model.changeColumnName?uncap_first};
    }
    
    </#if>
    <#if model.columnType = 'DATE' >
    public Date get${model.columnCommentP}() {
        return this.${model.changeColumnName?uncap_first};
    }
    
    public void set${model.columnCommentP}(Date ${model.changeColumnName?uncap_first}) {
        this.${model.changeColumnName?uncap_first} = ${model.changeColumnName?uncap_first};
    }
    
    </#if>
    </#list>
</#if>

}

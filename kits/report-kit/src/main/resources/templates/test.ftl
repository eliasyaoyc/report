<#list content?keys as classKey >
    班级: ${classKey}

    成员如下:
    <#assign users = content[classKey]>
    <#list users as user>
        name: ${user.index}
    </#list>
</#list>
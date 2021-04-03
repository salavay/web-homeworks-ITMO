<#-- @ftlvariable name="posts" type="ru.itmo.wp.domain.Post[]" -->
<#import "commons.ftlh" as c>

<@c.page>
    <#list posts as post>
        <@c.post pagePost=post/>
    </#list>
</@c.page>

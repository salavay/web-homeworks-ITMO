<#import "commons.ftlh" as c>

<@c.page>
    <#if pagePost??>
        <@c.post pagePost true/>
    <#else>
        No such post
    </#if>
</@c.page>
<#import "./common.ftlh" as c>

<@c.page>
    <#list posts?reverse as p>
        <@c.postMacro p/>
    </#list>

</@c.page>

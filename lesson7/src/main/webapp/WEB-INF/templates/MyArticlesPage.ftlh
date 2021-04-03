<#-- @ftlvariable name="articles" type="ru.itmo.wp.model.domain.article.Article[]" -->
<#-- @ftlvariable name="loginOrEmail" type="java.lang.String" -->
<#-- @ftlvariable name="password" type="java.lang.String" -->
<#-- @ftlvariable name="error" type="java.lang.String" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="myArticles">
        <div class="caption">Article</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>UserId</th>
                <th>Visibility</th>
            </tr>
            </thead>
            <tbody>
            <#if articles?size == 0>
                <tr class="noData">
                    <td colspan="3">
                        No data
                    </td>
                </tr>
            </#if>
            <#list articles as article>
                <tr>
                    <td class="article_id">${article.id}</td>
                    <td class="article_userId">${article.userId}</td>
                    <td class="article_title">${article.title}</td>
                    <td class="article_isHidden" id="${article.id}">
                        <a id="${article.hidden?string("false", "true")}">
                            ${article.hidden?string("false", "true")}
                        </a>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>

    <script>
        $(".article_isHidden").click(function () {
            $(this).find(" a").text("Changing...");
            ajax({
                action: "changeHiddenById",
                id: $(this).attr("id"),
                hidden: $(this).find(" a").attr("id")
            }, function (response) {
                let newHidden = response["changedArticle"]["hidden"] ? "false" : "true";
                let articleId = response["changedArticle"]["id"];
                $("#" + articleId + " a").text(newHidden).attr("id", newHidden);
            }, "/MyArticles")
        });
    </script>
</@c.page>
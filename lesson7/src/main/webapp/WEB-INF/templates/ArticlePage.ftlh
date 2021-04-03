<#-- @ftlvariable name="loginOrEmail" type="java.lang.String" -->
<#-- @ftlvariable name="password" type="java.lang.String" -->
<#-- @ftlvariable name="error" type="java.lang.String" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="saveNewArticle form-box">
        <div class="header">Article</div>
        <div class="body">
            <form method="post" action="">
                <input type="hidden" name="action" value="saveNewArticle"/>
                <div class="field">
                    <div class="name">
                        <label for="titleArticle">Title of article</label>
                    </div>
                    <div class="value">
                        <input id="titleArticle" name="titleArticle" value="${titleArticle!}"/>
                    </div>
                </div>
                <div class="field">
                    <div class="name">
                        <label for="textArticle">Text</label>
                    </div>
                    <div class="value">
                        <input id="textArticle" name="textArticle" value="${textArticle!}"/>
                    </div>
                </div>
                <div class="error">
                    ${error!}
                </div>
                <div class="button-field">
                    <input type="submit" value="Post">
                </div>
            </form>
        </div>
    </div>

    <script>
        $(function () {
            $(".saveNewArticle form").submit(function () {
                const titleArticle = $(this).find("input[name='titleArticle']").val();
                const textArticle = $(this).find("input[name='textArticle']").val();
                ajax({
                    action: "saveNewArticle",
                    titleArticle,
                    textArticle
                }, function (response) {
                    if (response["error"]) {
                        $(".error").text(response["error"]);
                    }
                });
                return false;
            });
        })
    </script>
</@c.page>

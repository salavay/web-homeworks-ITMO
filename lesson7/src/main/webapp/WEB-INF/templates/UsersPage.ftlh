<#-- @ftlvariable name="user" type="ru.itmo.wp.model.domain.User" -->
<#-- @ftlvariable name="users" type="ru.itmo.wp.model.domain.User[]" -->
<#import "commons.ftlh" as c>

<@c.page>
    <#assign isAdmin = false/>
    <#if user?? && user.admin>
        <#assign isAdmin = true/>
    </#if>
    <div class="users datatable">
        <div class="caption">User</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Login</th>
                <#if isAdmin>
                    <th>Admin</th>
                </#if>
            </tr>
            </thead>
            <tbody>
            <template>
                <tr>
                    <td class="user_id"></td>
                    <td class="user_login"></td>
                    <td class="user_admin" ${isAdmin?string("", "style=display:none")}>
                        <div class="isAdminValue">
                        </div>
                        <button class="isAdminButton">
                        </button>
                    </td>
                </tr>
            </template>
            <tr class="noData">
                <td colspan="3">
                    No data
                </td>
            </tr>
            </tbody>
        </table>
    </div>

    <script>
        $(function () {
            const $noData = $(".users .noData");
            $noData.find("td").text("Loading...");

            ajax({
                    action: "findAll"
                },
                function (response) {
                    response["users"].forEach(function (user) {
                        $noData.hide();
                        const $tr = $($(".users template").prop("content")).clone();
                        $tr.find(".user_id").text(user["id"]);
                        $tr.find(".user_login").text(user["login"]);
                        $tr.find(".user_admin .isAdminButton").attr("id", user["id"]);
                        $tr.find(".user_admin .isAdminButton").attr("isAdmin", user["admin"]);
                        $tr.find(".user_admin .isAdminValue").text(user["admin"] ? "Admin" : "Homyachok");
                        $tr.find(".user_admin .isAdminButton").text(user["admin"] ? "Disable" : "Enable");
                        $tr.find("tr").attr("data-userId", user["id"]);
                        $(".users tbody").append($tr);
                    });
                    $(".user_admin .isAdminButton").click(function () {
                        $(this).text("Changing...");
                        $(this).parent().find(".isAdminValue").text("Changing...");
                        ajax({
                            action: "changeAdminById",
                            userId: $(this).attr("id"),
                            isAdmin: $(this).attr("isAdmin")
                        }, function (changedUserResponse) {
                            let userFromResponse = changedUserResponse["changedUser"];
                            let isAdmin = userFromResponse["admin"];
                            let id = userFromResponse["id"];
                            let newAdminButton = isAdmin ? "Disable" : "Enable";
                            let newAdminValue = isAdmin ? "Admin" : "Homyachok";
                            let changedUser = $("tr[data-userId=" + id + "]");
                            changedUser.find(".user_admin .isAdminValue").text(newAdminValue);
                            changedUser.find(".user_admin .isAdminButton").text(newAdminButton);
                            changedUser.find(".user_admin .isAdminButton").attr("isAdmin", isAdmin);
                        }, "/users")
                    })
                }, "/users")
        });
    </script>
</@c.page>

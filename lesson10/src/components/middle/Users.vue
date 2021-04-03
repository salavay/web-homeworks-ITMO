<template>
  <div class="users datatable">
    <div class="caption">User</div>
    <table>
      <thead>
      <tr>
        <th>Id</th>
        <th>Login</th>
        <th>Name</th>
      </tr>
      </thead>
      <tbody>
      <template v-if="viewUsers.length === 0">
      <tr class="noData">
        <td colspan="3">
          No data
        </td>
      </tr>
      </template>
      <template v-else>
        <tr v-for="user in viewUsers" :key="user.id">
          <td class="id">{{ user.id }}</td>
          <td class="login">{{ user.login }}</td>
          <td class="name">{{ user.name }}</td>
        </tr>
      </template>
      </tbody>
    </table>
  </div>
</template>

<script>

export default {
  name: "Users",
  props: ["viewUsers"]
}
</script>

<style scoped>

</style>